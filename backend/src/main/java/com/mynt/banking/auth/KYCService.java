package com.mynt.banking.auth;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private String applicantId;
    private String workflow_ID;
    private String workflowRunId;
    private String referrrer;
    private String redirectURL;
    private String url;
    private String sdkToken;


    //TODO: take values and insert into DB for both endpoints && on DTO and in JS add critiria to check for correct inputs
    public SDKResponceDTO getOnfidoSDK(SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {

        SDKResponceDTO sdkResponceDTO = new SDKResponceDTO();

        String apiToken = "Token token="+onfido;
//        workflow_ID  = "c3c68677-2a4d-44cb-8bbc-f2d4693ec7be"; //KYC
        workflow_ID  = "792f7968-d06b-4b6b-80cc-4e9a9a089ad2" ; //Basic Test - Versions
        referrrer = "http://localhost:9001/signup/*";
        redirectURL = "http://localhost:9001/kyc";

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            ObjectNode applicantBody = objectMapper.createObjectNode();
            applicantBody.put("first_name",request.getFirstname());
            applicantBody.put("last_name",request.getLastname());
            String createApplicantBody = objectMapper.writeValueAsString(applicantBody);

            String endPoint =  "https://api.eu.onfido.com/v3.6/applicants/";
            String createApplicantResponce = postRequest(createApplicantBody, endPoint, apiToken);

            JsonNode createApplicantResponceBody = objectMapper.readTree(createApplicantResponce);
            applicantId = createApplicantResponceBody.get("id").asText();

            // ===================

            ObjectNode linkRequstBody = objectMapper.createObjectNode();
            linkRequstBody.put("completed_redirect_url",redirectURL);
            linkRequstBody.put("expired_redirect_url",redirectURL);
            linkRequstBody.put("language","en_US");

            ObjectNode createWorkFlowRunRequestBody = objectMapper.createObjectNode();
            createWorkFlowRunRequestBody.put("workflow_id",workflow_ID);
            createWorkFlowRunRequestBody.put("applicant_id",applicantId);
            createWorkFlowRunRequestBody.set("link", linkRequstBody);
            String createWorkFlowRunRequestJsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createWorkFlowRunRequestBody);

            endPoint =  "https://api.eu.onfido.com/v3.6/workflow_runs";
            String createWorkFlowResponce = postRequest(createWorkFlowRunRequestJsonBody, endPoint, apiToken);

            JsonNode createWorkFlowResponceBody = objectMapper.readTree(createWorkFlowResponce);
            workflowRunId = createWorkFlowResponceBody.get("id").asText();
            url = createWorkFlowResponceBody.get("link").get("url").asText();

            // ====================

            ObjectNode sdkRequest = objectMapper.createObjectNode();
            sdkRequest.put("applicant_id",applicantId);
            sdkRequest.put("referrer",referrrer);
            String createSDKRequestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sdkRequest);

            endPoint =  "https://api.eu.onfido.com/v3.6/sdk_token";
            String createSDKResponce = postRequest(createSDKRequestBody, endPoint, apiToken);

            JsonNode createSDKResponceBody = objectMapper.readTree(createSDKResponce);
            sdkToken = createSDKResponceBody.get("token").asText();

            sdkResponceDTO.setStage("createSDKResponce");
            sdkResponceDTO.setData(createSDKResponce);

            //=====================

            ObjectNode dtoData = objectMapper.createObjectNode();
            dtoData.put("sdkToken",sdkToken);
            dtoData.put("YOUR_WORKFLOW_RUN_ID",workflowRunId);
            dtoData.put("url",url);
            String dtoDataJsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dtoData);
            sdkResponceDTO.setStage("SDKResponceDTO");
            sdkResponceDTO.setData(dtoDataJsonBody);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return sdkResponceDTO;
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

        HttpRequest requestResults  = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs/"+request.getWORKFLOW_RUN_ID())) //
                .header("Authorization",apiToken)
                .GET()
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> resultsResponse = httpClient.send(requestResults, HttpResponse.BodyHandlers.ofString());

        ObjectMapper resultsMapper = new ObjectMapper();
        Map<String, Object> resultsResponceMapper = resultsMapper.readValue(resultsResponse.body(), new TypeReference<Map<String, Object>>() {});

        sdkResponceDTO.setData(resultsResponceMapper.toString());
        return sdkResponceDTO;
    }



}
