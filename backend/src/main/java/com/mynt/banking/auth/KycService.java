package com.mynt.banking.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.requests.ValidateKycRequest;
import com.mynt.banking.auth.responses.SDKResponse;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.repo.CurrencyCloudRepository;
import com.mynt.banking.currency_cloud.manage.accounts.AccountService;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.CreateContact;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.FindContact;
import com.mynt.banking.user.Role;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.Optional;

@Service
@Configuration
@RequiredArgsConstructor
public class KycService {

    @Value("${api.onfido}")
    private String onfido;

    @Value("${api.onfido.workflow_id}")
    private String workflow_ID;

    private final PasswordEncoder passwordEncoder;
    private String applicantId;
    private String workflowRunId;
    private String referrer;
    private String redirectURL;
    private String url;
    private String sdkToken;

    private final KycRepository kycRepository;

    private final UserRepository userRepository;

    private final AccountService accountService;

    private final ContactsService contactsService;

    private final CurrencyCloudRepository currencyCloudRepository;

    public ResponseEntity<SDKResponse> getOnfidoSDK(SignUpRequest request) {

        SDKResponse sdkResponceDTO = new SDKResponse();

        String apiToken = "Token token="+onfido;
        referrer = "http://localhost:9001/signup/*";
        redirectURL = "http://localhost:9001/kyc";

        request.setEmail(request.getEmail().toLowerCase());

        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            sdkResponceDTO.setStage("duplicate ID and or Email");
            return new ResponseEntity<>(sdkResponceDTO, HttpStatus.BAD_REQUEST);
        }

        try{
            applicant(request, apiToken);
            createWorkFlow(apiToken);
            createSDK(apiToken);
            sdkResponceDTO = apiResponseDto();
            addDataToDB(request);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(sdkResponceDTO, HttpStatus.OK);
    }

    private SDKResponse apiResponseDto() throws JsonProcessingException {

        SDKResponse sdkResponseDTO = new SDKResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode dtoData = objectMapper.createObjectNode();
        dtoData.put("sdkToken",sdkToken);
        dtoData.put("YOUR_WORKFLOW_RUN_ID",workflowRunId);
        dtoData.put("url",url);
        String dtoDataJsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dtoData);
        sdkResponseDTO.setStage("SDKResponseDTO");
        sdkResponseDTO.setData(dtoDataJsonBody);

        return sdkResponseDTO;
    }


    private void createSDK(String apiToken) throws URISyntaxException, IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode sdkRequest = objectMapper.createObjectNode();
        sdkRequest.put("applicant_id",applicantId);
        sdkRequest.put("referrer",referrer);
        String createSDKRequestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(sdkRequest);

        String endPoint =  "https://api.eu.onfido.com/v3.6/sdk_token";
        String createSDKResponse = postRequest(createSDKRequestBody, endPoint, apiToken);

        JsonNode createSDKResponseBody = objectMapper.readTree(createSDKResponse);
        sdkToken = createSDKResponseBody.get("token").asText();
    }

    private void applicant(SignUpRequest request, String apiToken) throws URISyntaxException, IOException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode applicantBody = objectMapper.createObjectNode();
        applicantBody.put("first_name",request.getFirstname());
        applicantBody.put("last_name",request.getLastname());
        String createApplicantBody = objectMapper.writeValueAsString(applicantBody);

        String endPoint =  "https://api.eu.onfido.com/v3.6/applicants/";
        String createApplicantResponse = postRequest(createApplicantBody, endPoint, apiToken);

        JsonNode createApplicantResponseBody = objectMapper.readTree(createApplicantResponse);
        applicantId = createApplicantResponseBody.get("id").asText();

    }

    private void createWorkFlow(String apiToken) throws URISyntaxException, IOException, InterruptedException {

        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode linkRequestBody = objectMapper.createObjectNode();
        linkRequestBody.put("completed_redirect_url",redirectURL);
        linkRequestBody.put("expired_redirect_url",redirectURL);
        linkRequestBody.put("language","en_US");

        ObjectNode createWorkFlowRunRequestBody = objectMapper.createObjectNode();
        createWorkFlowRunRequestBody.put("workflow_id",this.workflow_ID);
        createWorkFlowRunRequestBody.put("applicant_id",applicantId);
        createWorkFlowRunRequestBody.set("link", linkRequestBody);
        String createWorkFlowRunRequestJsonBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(createWorkFlowRunRequestBody);

        String endPoint =  "https://api.eu.onfido.com/v3.6/workflow_runs";
        String createWorkFlowResponseString = postRequest(createWorkFlowRunRequestJsonBody, endPoint, apiToken);

        JsonNode createWorkFlowResponseBody = objectMapper.readTree(createWorkFlowResponseString);
        workflowRunId = createWorkFlowResponseBody.get("id").asText();
        url = createWorkFlowResponseBody.get("link").get("url").asText();


    }

    private void addDataToDB (SignUpRequest request) throws URISyntaxException, IOException, InterruptedException {
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail().toLowerCase())
                .address(request.getAddress())
                .dob(request.getDob().toString())
                .phone_number(request.getPhoneNumber())
                .role(Role.USER)
                .build();
        userRepository.save(user);

        KycEntity kycEntity = KycEntity.builder()
                .applicationId(this.applicantId)
                .workFlowRunId(this.workflowRunId)
                .status("TBD")
                .user(user)
                .build();
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

    private boolean preCheck(ValidateKycRequest request){

        if(request.getEmail() == null){ return false;}

        if(Objects.equals(request.getEmail(), "")){ return false;}

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("No User"));

        Optional<CurrencyCloudEntity> cloudCurrencyUser = currencyCloudRepository.findByUser(user);

        if(cloudCurrencyUser.isPresent()){return false;}

        return true;
    }



    private HttpResponse<String> getSDKValidation(KycEntity kyc) {

        String apiToken = "Token token="+onfido;

        try {
            HttpRequest requestResults = HttpRequest.newBuilder()
                    .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs/" + kyc.getWorkFlowRunId()))
                    .header("Authorization", apiToken)
                    .GET()
                    .build();
            HttpClient httpClient = HttpClient.newHttpClient();
            return httpClient.send(requestResults, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e){
            System.err.println("Error: "+e.getMessage());
            System.err.println("Error: "+"check to see if email is already within use");
        }
        return null;
    }

    public SDKResponse validateKyc(ValidateKycRequest request) throws JsonProcessingException {

        SDKResponse sdkResponseDTO = new SDKResponse();
        request.setEmail(request.getEmail().toLowerCase().trim());
        boolean isValidRequest = preCheck(request);
        if(!isValidRequest){
            sdkResponseDTO.setStage("error with email");
            sdkResponseDTO.setData("error invalid email please check and try again");
            return sdkResponseDTO;
        }

        FindContact findContact = FindContact.builder()
                .emailAddress(request.getEmail())
                .build();

        ResponseEntity<JsonNode> contact = contactsService.findContact(findContact).block();

        assert contact != null;
        int statusCode = contact.getStatusCode().value();
        if (!(statusCode == 200)) {
            sdkResponseDTO.setStage("error with checking contact on currencycloud");
            sdkResponseDTO.setData("error please contact backend");
            return sdkResponseDTO;
        }
        String numEntries = Objects.requireNonNull(contact.getBody()).get("pagination").get("total_entries").asText();

        if (numEntries.equals("1")) {
            saveToCurrencyCloudRepository(request.getEmail(), contact.getBody().get("contacts").get(0).get("id").asText());
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("No User"));
            KycEntity kyc = kycRepository.findByUser(user);
            kycRepository.updateStatus("approved",kyc.getId());
            sdkResponseDTO.setStage("approved");
            sdkResponseDTO.setData("user already has an account / contact");
            return sdkResponseDTO;
        }

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("No User"));
        KycEntity kyc = kycRepository.findByUser(user);
        HttpResponse<String> resultsResponse = getSDKValidation(kyc);
        assert resultsResponse != null;
        if(resultsResponse.statusCode() != 200){
            sdkResponseDTO.setStage("get status");
            sdkResponseDTO.setData("error cant retrivie status");
            return sdkResponseDTO;
        }

        checkResult(resultsResponse, sdkResponseDTO, kyc);

        boolean hasAccount = createCurrencyCloudUser(resultsResponse, request.getEmail());
        if(!hasAccount){ sdkResponseDTO.setData("user already has an account / contact"); }
        sdkResponseDTO.setStage("approved");

        return sdkResponseDTO;
    }

    public void checkResult(HttpResponse<String> resultsResponse,
                            SDKResponse sdkResponseDTO,
                            KycEntity kyc) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode resultResponse = objectMapper.readTree(resultsResponse.body());

        kycRepository.updateStatus(resultResponse.get("status").asText(),kyc.getId());

        String responseStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultResponse);
        sdkResponseDTO.setData(responseStr);

    }

    public boolean createCurrencyCloudUser(HttpResponse<String> resultsResponse, String email) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(resultsResponse.body());

        if(!Objects.equals(response.get("status").asText(), "approved")){ return false; }

        ResponseEntity<JsonNode> account  = createAccount(email);;

        if(!account.getStatusCode().is2xxSuccessful()){ return false;}

        ResponseEntity<JsonNode> contactResponse = createContact(email, account);

        if(!contactResponse.getStatusCode().is2xxSuccessful()){return false;}

        String contactUuid = Objects.requireNonNull(contactResponse.getBody()).get("id").asText();
        saveToCurrencyCloudRepository(email, contactUuid);

        return true;
    }

    private void saveToCurrencyCloudRepository(String email, String contactUuid){

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User Not found"));
        CurrencyCloudEntity currencyCloudEntity = new CurrencyCloudEntity();
        currencyCloudEntity.setUuid(contactUuid);
        currencyCloudEntity.setUser(user);
        currencyCloudRepository.save(currencyCloudEntity);
    }

    private ResponseEntity<JsonNode> createAccount(String email){

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No User"));

        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                .accountName(user.getFirstname()+" "+user.getLastname())
                .legalEntityType("individual")
                .street(user.getAddress())
                .city(user.getAddress())
                .country("gb")
                .build();
        return accountService.create(createAccountRequest).block();
    }

    private ResponseEntity<JsonNode> createContact(String email, ResponseEntity<JsonNode> account){

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("No User"));

        CreateContact contact = CreateContact.builder()
                .accountId(Objects.requireNonNull(account.getBody().get("id").asText()))
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .emailAddress(user.getEmail())
                .phoneNumber(user.getPhone_number())
                .status("enabled")
                .dateOfBirth(user.getDob())
                .build();
        return contactsService.createContact(contact).block();
    }
}
