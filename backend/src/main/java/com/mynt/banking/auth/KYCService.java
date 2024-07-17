package com.mynt.banking.auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.requests.ValidateKycRequest;
import com.mynt.banking.auth.responses.SDKResponse;
import com.mynt.banking.currency_cloud.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.CurrencyCloudRepository;
import com.mynt.banking.currency_cloud.manage.accounts.AccountService;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.CreateContact;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.FindContact;
import com.mynt.banking.user.Role;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class KYCService {

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

    @Autowired
    private KycRepository kycRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private CurrencyCloudRepository currencyCloudRepository;

    public SDKResponse getOnfidoSDK(SignUpRequest request) {

        SDKResponse sdkResponceDTO = new SDKResponse();

        String apiToken = "Token token="+onfido;
        referrer = "http://localhost:9001/signup/*";
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

    private SDKResponse apiResponseDto() throws JsonProcessingException {

        SDKResponse sdkResponceDTO = new SDKResponse();

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
        sdkRequest.put("referrer",referrer);
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
        createWorkFlowRunRequestBody.put("workflow_id",this.workflow_ID);
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
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail().toLowerCase());
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

    public SDKResponse validateKyc(ValidateKycRequest request) {

        SDKResponse sdkResponceDTO = new SDKResponse();

        String apiToken = "Token token="+onfido;

        if(userRepository.findByEmail(request.getEmail()).isEmpty() || //email is not in DB
                Objects.equals(request.getEmail(), "") ||            // email is empty string
                request.getEmail() == null                              // email is null
        ){
            sdkResponceDTO.setStage("error with email");
            sdkResponceDTO.setData("error invalid email please check and try again");
            return sdkResponceDTO;
        }

        User user = userRepository.findByEmail(request.getEmail()).get();
        KycEntity kyc = kycRepository.findByUser(user);

        try {

            HttpRequest requestResults  = (HttpRequest) HttpRequest.newBuilder()
                    .uri(new URI("https://api.eu.onfido.com/v3.6/workflow_runs/"+kyc.getWorkFlowRunId()))
                    .header("Authorization",apiToken)
                    .GET()
                    .build();

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> resultsResponse = httpClient.send(requestResults, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();

            if(resultsResponse.statusCode() == 200){

                JsonNode resultsResponce = objectMapper.readTree(resultsResponse.body());

                kycRepository.updateStatus(resultsResponce.get("status").asText(),kyc.getId());

                String responceStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultsResponce);
                sdkResponceDTO.setData(responceStr);

                boolean hasAccount = createCurrencyCloudUser(resultsResponce, request.getEmail());
                if(!hasAccount){
                    sdkResponceDTO.setStage("approved");
                    sdkResponceDTO.setData("user already has an account / contact");
                    return sdkResponceDTO;
                }
            }

        } catch (URISyntaxException | IOException | InterruptedException ignored){
            System.err.println("Error: "+ignored.getMessage());
            System.err.println("Error: "+"check to see if email is already within use");
        }
        sdkResponceDTO.setStage("approved");
        return sdkResponceDTO;
    }

    public boolean createCurrencyCloudUser(JsonNode resultsResponce, String email){
        if(Objects.equals(resultsResponce.get("status").asText(), "approved")){

            if(!preChecks(email)){return false;}

            User user = userRepository.findByEmail(email).get();

            CreateAccountRequest createAccountRequest = CreateAccountRequest.builder()
                    .accountName(user.getFirstname()+" "+user.getLastname())
                    .legalEntityType("individual")
                    .street(user.getAddress())
                    .city(user.getAddress())
                    .country("gb") // TODO update database to modle addresses better
                    .build();
            ResponseEntity<JsonNode> account  = accountService.createAccount(createAccountRequest).block();

            if(account.getStatusCode().is2xxSuccessful()){
                CreateContact contact = CreateContact.builder()
                        .accountId(account.getBody().get("id").asText())
                        .firstName(user.getFirstname())
                        .lastName(user.getLastname())
                        .emailAddress(user.getEmail())
                        .phoneNumber(user.getPhone_number())
                        .status("enabled")
                        .dateOfBirth(user.getDob())
                        .build();
                ResponseEntity<JsonNode> contactResponce = contactsService.createContact(contact).block();

                String contactUuid = contactResponce.getBody().get("id").asText();

                Long userID = (long) userRepository.findByEmail(email).get().getId();

                CurrencyCloudEntity currencyCloudEntity = new CurrencyCloudEntity();
                currencyCloudEntity.setUuid(contactUuid);
                currencyCloudEntity.setUsersId(userID);
                currencyCloudRepository.save(currencyCloudEntity);


            }

        }

        return true;
    }

    private boolean preChecks(String email){

        User user = userRepository.findByEmail(email).get();

        if(!currencyCloudRepository.findByUsersId((long)user.getId()).isEmpty()){return false;}

        FindContact findContact = FindContact.builder()
                .emailAddress(email)
                .build();

        int statusCode = contactsService.findContact(findContact).block().getStatusCode().value();
        if (statusCode == 200) {
            String numEntries = contactsService.findContact(findContact).block().getBody().get("pagination").get("total_entries").asText();
            if (numEntries.equals("1")) {
                return false;
            }
        }
        return true;
    }



}
