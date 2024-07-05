package com.mynt.banking.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.user.Role;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KYCService {

    @Value("${api.onfido}")
    private String onfido;

    private String applicantId;
    private String workflow_ID;
    private String workflowRunId;
    private String referrrer;
    private String redirectURL;
    private String url;
    private String sdkToken;

    @Autowired
    private KycRepository kycRepository;

    @Autowired
    private UserRepository userRepository;

    public SDKResponceDTO getOnfidoSDK(SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {

        SDKResponceDTO sdkResponceDTO = new SDKResponceDTO();

        String apiToken = "Token token="+onfido;
//        workflow_ID  = "c3c68677-2a4d-44cb-8bbc-f2d4693ec7be"; //KYC
        workflow_ID  = "792f7968-d06b-4b6b-80cc-4e9a9a089ad2" ; //Basic Test - Versions
        referrrer = "http://localhost:9001/signup/*";
        redirectURL = "http://localhost:9001/kyc";

        try{

            applicant(request, apiToken);

            createWorkFlow(request, apiToken);

            createSDK(request, apiToken);

            sdkResponceDTO = apiResponseDto();

            addDataToDB(request);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return sdkResponceDTO;
    }

    private SDKResponceDTO apiResponseDto() throws JsonProcessingException {

        SDKResponceDTO sdkResponceDTO = new SDKResponceDTO();

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode dtoData = objectMapper.createObjectNode();
        dtoData.put("sdkToken",sdkToken);
        dtoData.put("YOUR_WORKFLOW_RUN_ID",workflowRunId);
        dtoData.put("url",url);
        String dtoDataJsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dtoData);
        sdkResponceDTO.setStage("SDKResponceDTO");
        sdkResponceDTO.setData(dtoDataJsonBody);

        return sdkResponceDTO;
    }


    private void createSDK(SignUpRequest request, String apiToken) throws URISyntaxException, IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode sdkRequest = objectMapper.createObjectNode();
        sdkRequest.put("applicant_id",applicantId);
        sdkRequest.put("referrer",referrrer);
        String createSDKRequestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sdkRequest);

        String endPoint =  "https://api.eu.onfido.com/v3.6/sdk_token";
        String createSDKResponce = postRequest(createSDKRequestBody, endPoint, apiToken);

        JsonNode createSDKResponceBody = objectMapper.readTree(createSDKResponce);
        sdkToken = createSDKResponceBody.get("token").asText();

    }

    private void applicant(SignUpRequest request, String apiToken) throws URISyntaxException, IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode applicantBody = objectMapper.createObjectNode();
        applicantBody.put("first_name",request.getFirstname());
        applicantBody.put("last_name",request.getLastname());
        String createApplicantBody = objectMapper.writeValueAsString(applicantBody);

        String endPoint =  "https://api.eu.onfido.com/v3.6/applicants/";
        String createApplicantResponce = postRequest(createApplicantBody, endPoint, apiToken);

        JsonNode createApplicantResponceBody = objectMapper.readTree(createApplicantResponce);
        applicantId = createApplicantResponceBody.get("id").asText();

    }

    private void createWorkFlow(SignUpRequest request, String apiToken) throws URISyntaxException, IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode linkRequstBody = objectMapper.createObjectNode();
        linkRequstBody.put("completed_redirect_url",redirectURL);
        linkRequstBody.put("expired_redirect_url",redirectURL);
        linkRequstBody.put("language","en_US");

        ObjectNode createWorkFlowRunRequestBody = objectMapper.createObjectNode();
        createWorkFlowRunRequestBody.put("workflow_id",workflow_ID);
        createWorkFlowRunRequestBody.put("applicant_id",applicantId);
        createWorkFlowRunRequestBody.set("link", linkRequstBody);
        String createWorkFlowRunRequestJsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createWorkFlowRunRequestBody);

        String endPoint =  "https://api.eu.onfido.com/v3.6/workflow_runs";
        String createWorkFlowResponce = postRequest(createWorkFlowRunRequestJsonBody, endPoint, apiToken);

        JsonNode createWorkFlowResponceBody = objectMapper.readTree(createWorkFlowResponce);
        workflowRunId = createWorkFlowResponceBody.get("id").asText();
        url = createWorkFlowResponceBody.get("link").get("url").asText();


    }



    private void addDataToDB (SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {
        User user = new User();
        user.setId(null);
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setDob(request.getDob());
        user.setPhone_number(request.getPhoneNumber());
        user.setRole(Role.USER);
        userRepository.save(user);

        KycEntity kycEntity = new KycEntity();
        long num = kycRepository.count();
        kycEntity.setId(num++);
        kycEntity.setApplicationId(this.applicantId);
        kycEntity.setWorkFlowRunId(this.workflowRunId);
        kycEntity.setStatus("TBD");
        kycEntity.setUser( userRepository.findByEmail(request.getEmail()).get());
        kycRepository.save(kycEntity);
    }



    private String postRequest(String jsonObj, String urlAndEndPoint ,String apiToken) throws URISyntaxException, IOException, InterruptedException {

        HttpRequest createApplicantRequest = HttpRequest.newBuilder()
                .uri(new URI(urlAndEndPoint))
                .header("Authorization", apiToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObj))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> createApplicant = httpClient.send(createApplicantRequest, HttpResponse.BodyHandlers.ofString());

        return createApplicant.body();
    }

    public SDKResponceDTO retrieveResults(ValidateKycRequest request ) throws IOException, InterruptedException, URISyntaxException {

        SDKResponceDTO sdkResponceDTO = new SDKResponceDTO();

        String apiToken = "Token token="+onfido;

        User user = userRepository.findByEmail(request.getEmail()).get();
        KycEntity kyc = kycRepository.findByUser(user);

        HttpRequest requestResults  = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs/"+kyc.getWorkFlowRunId()))
                .header("Authorization",apiToken)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> resultsResponse = httpClient.send(requestResults, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode resultsResponce = objectMapper.readTree(resultsResponse.body());

        System.out.println("\n\n\n\n filtered version: "+resultsResponce.toPrettyString());
        System.out.println("status: "+resultsResponce.get("status").asText());
        System.out.println("id: "+kyc.getId());


//        kycRepository.updateStatus(resultsResponce.get("status").asText(), kyc.getId() );

        kycRepository.updateStatus(resultsResponce.get("status").asText(),kyc.getId());


        String responceStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultsResponce);
        sdkResponceDTO.setData(responceStr);
        return sdkResponceDTO;
    }



}
