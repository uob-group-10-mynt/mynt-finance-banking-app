package com.mynt.banking.client.pay.beneficiaries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mynt.banking.currency_cloud.pay.beneficiaries.BeneficiaryService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.CreateBeneficiaryRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.FindBeneficiaryRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.ValidateBeneficiaryRequest;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyntBeneficiaryService {

    private final BeneficiaryService beneficiaryService;
    private final UserContextService userContextService;

    public BeneficiariesDetailResponse find(Integer perPage, Integer page) {
        // Form Beneficiary Request:
        FindBeneficiaryRequest findBeneficiaryRequest = FindBeneficiaryRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .perPage(perPage)
                .page(page)
                .build();

        // Fetch beneficiaries:
        ResponseEntity<JsonNode> currencyCloudBeneficiariesResponse = beneficiaryService.find(findBeneficiaryRequest);

        // Map and return the response
        try {
            // Initialize ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(Objects.requireNonNull(currencyCloudBeneficiariesResponse.getBody()).toString(),
                    BeneficiariesDetailResponse.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public BeneficiaryDetail getBeneficiary(String id) {
        ResponseEntity<JsonNode> beneficiaryDetailsResponse = beneficiaryService.get(
                id, userContextService.getCurrentUserUuid());
        JsonNode beneficiaryDetails = beneficiaryDetailsResponse.getBody();
        if (beneficiaryDetails == null || beneficiaryDetails.isEmpty()) {
            throw new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Map and return the response
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(beneficiaryDetails.toString(), BeneficiaryDetail.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse.Beneficiary",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
    public BeneficiaryDetail create(BeneficiaryDetail request) {
        // Validate beneficiary:
        ObjectMapper mapper = new ObjectMapper();
        try {
            ValidateBeneficiaryRequest validateBeneficiaryRequest =  mapper.readValue(request.toString(),
                    ValidateBeneficiaryRequest.class);
            validateBeneficiaryRequest.setBeneficiaryEntityType("individual");
            validateBeneficiaryRequest.setOnBehalfOf(userContextService.getCurrentUserUuid());
            beneficiaryService.validate(validateBeneficiaryRequest);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse.Beneficiary",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Create beneficiary:
        BeneficiaryDetail beneficiaryDetail;
        try {
            CreateBeneficiaryRequest createBeneficiaryRequest =  mapper.readValue(request.toString(),
                    CreateBeneficiaryRequest.class);
            createBeneficiaryRequest.setBeneficiaryEntityType("individual");
            createBeneficiaryRequest.setOnBehalfOf(userContextService.getCurrentUserUuid());
            ResponseEntity<JsonNode>  response = beneficiaryService.create(createBeneficiaryRequest);
            return mapper.readValue(response.getBody().toString(), BeneficiaryDetail.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse.Beneficiary",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
