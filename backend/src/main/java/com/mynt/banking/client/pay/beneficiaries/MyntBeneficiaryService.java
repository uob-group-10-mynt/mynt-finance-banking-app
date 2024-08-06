package com.mynt.banking.client.pay.beneficiaries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mynt.banking.currency_cloud.pay.beneficiaries.BeneficiaryService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.FindBeneficiaryRequest;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
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
        ResponseEntity<JsonNode> currencyCloudBeneficiariesResponse = beneficiaryService.find(
                findBeneficiaryRequest);

        // Initialize ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        BeneficiariesDetailResponse response;

        // Map the response
        try {
            response = mapper.readValue(Objects.requireNonNull(currencyCloudBeneficiariesResponse.getBody()).toString(),
                    BeneficiariesDetailResponse.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return response;
    }


    public BeneficiariesDetailResponse.Beneficiary getBeneficiary(String id) {
        ResponseEntity<JsonNode> beneficiaryDetailsResponse = beneficiaryService.get(
                id, userContextService.getCurrentUserUuid());
        JsonNode beneficiaryDetails = beneficiaryDetailsResponse.getBody();
        if (beneficiaryDetails == null || beneficiaryDetails.isEmpty()) {
            throw new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Map the response
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        BeneficiariesDetailResponse.Beneficiary response;
        try {
            response = mapper.readValue(beneficiaryDetails.toString(), BeneficiariesDetailResponse.Beneficiary.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse.Beneficiary",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return response;
    }
}
