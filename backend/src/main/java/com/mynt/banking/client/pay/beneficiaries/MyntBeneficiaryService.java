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

import java.io.DataInput;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyntBeneficiaryService {

    private final BeneficiaryService beneficiaryService;
    private final UserContextService userContextService;

    public BeneficiariesDetailResponse findBeneficiaries(Integer perPage, Integer page) {
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
        } catch (IOException e) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return response;
    }

}
