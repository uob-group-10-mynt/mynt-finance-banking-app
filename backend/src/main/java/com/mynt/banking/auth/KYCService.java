package com.mynt.banking.auth;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KYCService {

    @Value("${api.onfido}")
    private String onfido;

    private String ApplicantId;
    private String workflow_ID;
    private String workflowRunId;
    private String referrrer;
    private String redirectURL;
    private String url;
    private String sdkToken;

    public SDKResponceDTO getOnfidoSDK(SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {

        SDKResponceDTO sdkResponceDTO = new SDKResponceDTO();

        String apiToken = "Token token="+onfido;
//        workflow_ID  = "c3c68677-2a4d-44cb-8bbc-f2d4693ec7be"; //KYC
        workflow_ID  = "792f7968-d06b-4b6b-80cc-4e9a9a089ad2" ; //Basic Test - Versions
        referrrer = "http://localhost:9001/signup/*";
        redirectURL = "http://localhost:9001/kyc";

        try{
            HashMap<String,Object> createApplicant = createApplicant(request, apiToken);
            ApplicantId = createApplicant.get("id").toString();

            HashMap<String,Object> createWorkflowRun = createWorkflowRun(workflow_ID, ApplicantId , apiToken, redirectURL);
            workflowRunId = (String) createWorkflowRun.get("id");
            url = createWorkflowRun.get("link").toString();

            HashMap<String,Object> sdkDetails = getSDKDetails(ApplicantId, referrrer, apiToken);
            sdkToken = (String) sdkDetails.get("token");
//            sdkResponceDTO.setStage("createWorkflowRun");
//            sdkResponceDTO.setData(createWorkflowRun);

            HashMap<String, Object> response = new HashMap<>();
            response.put("sdkToken", sdkToken);
            response.put("YOUR_WORKFLOW_RUN_ID",workflowRunId);
            response.put("url", url);
            sdkResponceDTO.setData(String.valueOf(response));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return sdkResponceDTO;
    }

    private HashMap<String,Object> createApplicant(SignUpRequest request, String apiToken) throws URISyntaxException, IOException, InterruptedException {

        HashMap<String,String> body = new HashMap<>();
        body.put("first_name",request.getFirstname());
        body.put("last_name",request.getLastname());

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonObj = objectMapper.writeValueAsString(body);

        HttpRequest createApplicantRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://api.eu.onfido.com/v3.6/applicants/"))
                .header("Authorization", apiToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonObj))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> createApplicant = httpClient.send(createApplicantRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(createApplicant.body(), new TypeReference<Map<String, Object>>() {
        });

        return (HashMap<String, Object>) map;
    }

    private HashMap createWorkflowRun(String workflow_id, String applicant_id,
                                      String apiToken, String redirectURL
                                                     ) throws URISyntaxException,
                                                              IOException,
                                                              InterruptedException {

        String json = "{ \"workflow_id\": \""+workflow_id+"\","+
                "\"applicant_id\":\""+applicant_id+"\","+
                "\"link\":{"+
                    "\"completed_redirect_url\":\""+redirectURL+"\","+
                    "\"expired_redirect_url\":\""+redirectURL+"\","+
                    "\"language\":\"en_US\""+
            "}"+
        "}";

        HttpRequest createWorkflowRunRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs"))
                .header("Authorization",apiToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> createWorkflowRun = httpClient.send(createWorkflowRunRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        HashMap result =  mapper.readValue(createWorkflowRun.body(),HashMap.class);

        return result;
    }

    private HashMap<String,Object> getSDKDetails(String applicant_id, String referrrer, String apiToken) throws IOException, URISyntaxException, InterruptedException {

        Map<String, String> requestSDK = new HashMap<>();
        requestSDK.put("applicant_id", applicant_id);
        requestSDK.put("referrer",referrrer);

        ObjectMapper sdkMapper = new ObjectMapper();
        String jsonSDK = sdkMapper.writeValueAsString(requestSDK);

        HttpRequest createSDK = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://api.eu.onfido.com/v3.6/sdk_token"))
                .header("Authorization",apiToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonSDK))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> sdkResponse = httpClient.send(createSDK, HttpResponse.BodyHandlers.ofString());

        ObjectMapper sdkResponseMapper = new ObjectMapper();
        Map<String, Object> responceMapper = sdkResponseMapper.readValue(sdkResponse.body(), new TypeReference<Map<String, Object>>() {});

        return (HashMap<String, Object>) responceMapper;
    }

    public SDKResponceDTO retrieveResults(ValidateKycRequest request ) throws IOException, InterruptedException, URISyntaxException {

        SDKResponceDTO sdkResponceDTO = new SDKResponceDTO();

        String apiToken = "Token token="+onfido;

        HttpRequest requestResults  = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs/"+request.getWORKFLOW_RUN_ID())) //
                .header("Authorization",apiToken)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> resultsResponse = httpClient.send(requestResults, HttpResponse.BodyHandlers.ofString());

        ObjectMapper resultsMapper = new ObjectMapper();
        Map<String, Object> resultsResponceMapper = resultsMapper.readValue(resultsResponse.body(), new TypeReference<Map<String, Object>>() {});

        sdkResponceDTO.setData((HashMap<String,Object>) resultsResponceMapper);
        return sdkResponceDTO;
    }



}
