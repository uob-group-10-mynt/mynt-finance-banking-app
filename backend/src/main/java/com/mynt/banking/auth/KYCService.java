package com.mynt.banking.auth;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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

//    private static final Object apiToken = ;

//    @Value(("${api.kycAPI}")
//    private String apiToken;

    public SDKResponceDTO getSDK(SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {

        SDKResponceDTO sdkResponceDTO = new SDKResponceDTO();
        String apiToken = "Token token="+"api_sandbox.IYA-2r1JzLQ._kIRoOo62rJjh0JJQdYdI9vMaL63smxf";
        String workflow_ID  = "c3c68677-2a4d-44cb-8bbc-f2d4693ec7be";
        String referrrer = "https://localhost:9001/kyc";

        try{
            HashMap<String,Object> createApplicant = createApplicant(request, apiToken);
            sdkResponceDTO.setStage("createApplication");
            sdkResponceDTO.setData(createApplicant);

            String id = createApplicant.get("id").toString();

            HashMap<String,Object> createWorkflowRun = createWorkflowRun(workflow_ID, id , apiToken);
            sdkResponceDTO.setStage("createWorkflowRun");
            sdkResponceDTO.setData(createWorkflowRun);

            HashMap<String,Object> sdkDetails = getSDKDetails(id, referrrer, apiToken);
            sdkResponceDTO.setStage("getSDKDetails");
            sdkResponceDTO.setData(sdkDetails);

            HashMap<String, Object> response = new HashMap<>();
            response.put("sdkToken", sdkDetails.get("token"));
            response.put("YOUR_WORKFLOW_RUN_ID",workflow_ID);
            response.put("url",createWorkflowRun.get("link") );
            sdkResponceDTO.setData(response);

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

        // keep here until I need it later
//        "dob": "1990-01-31",
//                "address": {
//            "building_number": "100",
//                    "street": "Main Street",
//                    "town": "London",
//                    "postcode": "SW4 6EH",
//                    "country": "GBR"
//        }

        return (HashMap<String, Object>) map;
    }

    private HashMap<String,Object> createWorkflowRun(String workflow_id, String applicant_id, String apiToken) throws URISyntaxException, IOException, InterruptedException {

        Map<String, String> requestMap = new HashMap();
        requestMap.put("workflow_id",workflow_id);
        requestMap.put("applicant_id", applicant_id);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(requestMap);

        HttpRequest createWorkflowRunRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs"))
                .header("Authorization",apiToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> createWorkflowRun = httpClient.send(createWorkflowRunRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper workflowMapper = new ObjectMapper();
        Map<String, Object> responceMap = workflowMapper.readValue(createWorkflowRun.body(), new TypeReference<Map<String, Object>>() {});

        return (HashMap<String, Object>) responceMap;
    }

    private HashMap<String,Object> getSDKDetails(String applicant_id, String referrrer, String apiToken) throws IOException, URISyntaxException, InterruptedException {

        Map<String, String> requestSDK = new HashMap<>();
        requestSDK.put("applicant_id",applicant_id);
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





//    public AuthenticationResponse getReults(RegisterRequest request){
//        // TODO: Create new end point for this
//        // get results ==================================================================
//        HttpRequest requestResults  = (HttpRequest) HttpRequest.newBuilder()
//                .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs/"+(String) responceMapper.get("id")))
//                .header("Authorization","Token token=api_sandbox.IYA-2r1JzLQ._kIRoOo62rJjh0JJQdYdI9vMaL63smxf")
//                .GET()
//                .build();
//
//        httpClient = HttpClient.newHttpClient();
//        HttpResponse<String> resultsResponse = httpClient.send(createWorkflowRunRequest, HttpResponse.BodyHandlers.ofString());
//        ObjectMapper resultsMapper = new ObjectMapper();
//        Map<String, Object> resultsResponceMapper = resultsMapper.readValue(resultsResponse.body(), new TypeReference<Map<String, Object>>() {});
//        for( String key :resultsResponceMapper.keySet()){
//            System.out.println("Result - "+key+": "+resultsResponceMapper.get(key));
//        }
//        return null;
//    }



}
