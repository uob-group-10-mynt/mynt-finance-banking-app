package com.mynt.banking.client.pay.beneficiaries;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mynt.banking.currency_cloud.pay.beneficiaries.CurrencyCloudBeneficiariesService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.*;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.util.exceptions.currency_cloud.CurrencyCloudException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
public class MyntBeneficiaryService {

    private final CurrencyCloudBeneficiariesService beneficiaryService;
    private final UserContextService userContextService;

    public MyntBeneficiariesDetailResponse find(Integer perPage, Integer page) {
        // Form Beneficiary Request:
        CurrencyCloudFindBeneficiariesRequest findBeneficiaryRequest = CurrencyCloudFindBeneficiariesRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .perPage(String.valueOf(perPage))
                .page(String.valueOf(page))
                .build();

        // Fetch beneficiaries:
        ResponseEntity<JsonNode> currencyCloudBeneficiariesResponse = beneficiaryService.findBeneficiaries(findBeneficiaryRequest);

        // Map and return the response
        try {
            // Initialize ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(Objects.requireNonNull(currencyCloudBeneficiariesResponse.getBody()).toString(),
                    MyntBeneficiariesDetailResponse.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public MyntBeneficiaryDetail get(String id) {
        CurrencyCloudGetBeneficiaryRequest getBeneficiaryRequest = CurrencyCloudGetBeneficiaryRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .build();
        ResponseEntity<JsonNode> beneficiaryDetailsResponse = beneficiaryService.getBeneficiary(
                id, getBeneficiaryRequest);
        JsonNode beneficiaryDetails = beneficiaryDetailsResponse.getBody();
        if (beneficiaryDetails == null || beneficiaryDetails.isEmpty()) {
            throw new CurrencyCloudException("Currency Cloud Error: Failed to fetch payment details.", HttpStatus.UNPROCESSABLE_ENTITY);
        }

        // Map and return the response
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.readValue(beneficiaryDetails.toString(), MyntBeneficiaryDetail.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse.Beneficiary",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public MyntBeneficiaryDetail create(MyntBeneficiaryDetail request) {
        // Validate beneficiary:
        ObjectMapper mapper = new ObjectMapper();


        CurrencyCloudValidateBeneficiaryRequest validateBeneficiaryRequest = mapToValidateBeneficiaryRequest(request);
        validateBeneficiaryRequest.setBeneficiaryEntityType("individual");
        validateBeneficiaryRequest.setOnBehalfOf(userContextService.getCurrentUserUuid());
        beneficiaryService.validateBeneficiary(validateBeneficiaryRequest);

        // Create beneficiary:
        MyntBeneficiaryDetail beneficiaryDetail;
        try {
            CurrencyCloudCreateBeneficiaryRequest createBeneficiaryRequest = mapToCreateBeneficiaryRequest(request);
            createBeneficiaryRequest.setBeneficiaryEntityType("individual");
            createBeneficiaryRequest.setBankAccountHolderName(request.getBankAccountHolderName());
            createBeneficiaryRequest.setOnBehalfOf(userContextService.getCurrentUserUuid());
            ResponseEntity<JsonNode> response = beneficiaryService.createBeneficiary(createBeneficiaryRequest);
            return mapper.readValue(Objects.requireNonNull(response.getBody()).toString(), MyntBeneficiaryDetail.class);
        } catch (IOException ignore) {
            throw new CurrencyCloudException("Failed to map JSON response to BeneficiariesDetailResponse.Beneficiary",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<Void> delete(String id) {
        CurrencyCloudDeleteBeneficiaryRequest deleteBeneficiaryRequest = CurrencyCloudDeleteBeneficiaryRequest.builder()
                .onBehalfOf(userContextService.getCurrentUserUuid())
                .build();
        ResponseEntity<JsonNode> deleteResponse = beneficiaryService.deleteBeneficiary(id, deleteBeneficiaryRequest);
        if (deleteResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }


    // Private mapper method
    private CurrencyCloudValidateBeneficiaryRequest mapToValidateBeneficiaryRequest(MyntBeneficiaryDetail beneficiaryDetail) {
        if (beneficiaryDetail == null) {
            return null;
        }

        String beneficiaryAddress = null;
        if (beneficiaryDetail.getBeneficiaryAddress() != null) {
            StringJoiner joiner = new StringJoiner(", ");
            for (String address : beneficiaryDetail.getBeneficiaryAddress()) {
                joiner.add(address);
            }
            beneficiaryAddress = joiner.toString();
        }

        return CurrencyCloudValidateBeneficiaryRequest.builder()
                .bankCountry(beneficiaryDetail.getBankCountry())
                .currency(beneficiaryDetail.getCurrency())
                .beneficiaryAddress(beneficiaryAddress)
                .beneficiaryCountry(beneficiaryDetail.getBeneficiaryCountry())
                .bicSwift(beneficiaryDetail.getBicSwift())
                .iban(beneficiaryDetail.getIban())
                .bankName(beneficiaryDetail.getBankName())
                .beneficiaryFirstName(beneficiaryDetail.getBeneficiaryFirstName())
                .beneficiaryLastName(beneficiaryDetail.getBeneficiaryLastName())
                .beneficiaryCity(beneficiaryDetail.getBeneficiaryCity())
                .beneficiaryPostcode(beneficiaryDetail.getBeneficiaryPostcode())
                .accountNumber("") // Assuming default value, adjust as needed
                .routingCodeType1("") // Assuming default value, adjust as needed
                .routingCodeValue1("") // Assuming default value, adjust as needed
                .routingCodeType2("") // Assuming default value, adjust as needed
                .routingCodeValue2("") // Assuming default value, adjust as needed
                .bankAddress("") // Assuming default value, adjust as needed
                .bankAccountType("") // Assuming default value, adjust as needed
                .beneficiaryCompanyName("") // Assuming default value, adjust as needed
                .beneficiaryStateOrProvince("") // Assuming default value, adjust as needed
                .beneficiaryDateOfBirth("") // Assuming default value, adjust as needed
                .beneficiaryIdentificationType("") // Assuming default value, adjust as needed
                .beneficiaryIdentificationValue("") // Assuming default value, adjust as needed
                .paymentTypes(null) // Assuming default value, adjust as needed
                .onBehalfOf("") // Assuming default value, adjust as needed
                .build();
    }

    private CurrencyCloudCreateBeneficiaryRequest mapToCreateBeneficiaryRequest(MyntBeneficiaryDetail beneficiaryDetail) {
        if (beneficiaryDetail == null) {
            return null;
        }

        String beneficiaryAddress = null;
        if (beneficiaryDetail.getBeneficiaryAddress() != null) {
            StringJoiner joiner = new StringJoiner(", ");
            for (String address : beneficiaryDetail.getBeneficiaryAddress()) {
                joiner.add(address);
            }
            beneficiaryAddress = joiner.toString();
        }

        return CurrencyCloudCreateBeneficiaryRequest.builder()
                .name(beneficiaryDetail.getName())
                .bankAccountHolderName(beneficiaryDetail.getBankAccountHolderName())
                .bankCountry(beneficiaryDetail.getBankCountry())
                .currency(beneficiaryDetail.getCurrency())
                .email("") // Assuming default value, adjust as needed
                .beneficiaryAddress(beneficiaryAddress)
                .beneficiaryCountry(beneficiaryDetail.getBeneficiaryCountry())
                .accountNumber("") // Assuming default value, adjust as needed
                .routingCodeType1(null) // Assuming default value, adjust as needed
                .routingCodeValue1(null) // Assuming default value, adjust as needed
                .routingCodeType2(null) // Assuming default value, adjust as needed
                .routingCodeValue2(null) // Assuming default value, adjust as needed
                .bicSwift(beneficiaryDetail.getBicSwift())
                .iban(beneficiaryDetail.getIban())
                .defaultBeneficiary(String.valueOf(false)) // Assuming default value, adjust as needed
                .bankAddress("") // Assuming default value, adjust as needed
                .bankName(beneficiaryDetail.getBankName())
                .bankAccountType("") // Assuming default value, adjust as needed
                .beneficiaryEntityType("individual") // Assuming default value, adjust as needed
                .beneficiaryCompanyName("") // Assuming default value, adjust as needed
                .beneficiaryFirstName(beneficiaryDetail.getBeneficiaryFirstName())
                .beneficiaryLastName(beneficiaryDetail.getBeneficiaryLastName())
                .beneficiaryCity(beneficiaryDetail.getBeneficiaryCity())
                .beneficiaryPostcode(beneficiaryDetail.getBeneficiaryPostcode())
                .beneficiaryStateOrProvince("") // Assuming default value, adjust as needed
                .beneficiaryDateOfBirth("") // Assuming default value, adjust as needed
                .beneficiaryIdentificationType("") // Assuming default value, adjust as needed
                .beneficiaryIdentificationValue("") // Assuming default value, adjust as needed
                .paymentTypes(null) // Assuming default value, adjust as needed
                .onBehalfOf("") // Assuming default value, adjust as needed
                .beneficiaryExternalReference("") // Assuming default value, adjust as needed
                .businessNature("") // Assuming default value, adjust as needed
                .companyWebsite("") // Assuming default value, adjust as needed
                .build();
    }
}