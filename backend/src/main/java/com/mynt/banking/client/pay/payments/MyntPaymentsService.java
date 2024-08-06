package com.mynt.banking.client.pay.payments;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.convert.conversions.ConversionService;
import com.mynt.banking.currency_cloud.convert.conversions.requests.CreateConversionRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.BeneficiaryService;
import com.mynt.banking.currency_cloud.pay.payments.PaymentService;
import com.mynt.banking.currency_cloud.pay.payments.requests.CreatePaymentRequest;
import com.mynt.banking.user.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MyntPaymentsService {
    private final UserContextService userContextService;
    private final PaymentService paymentService;
    private final BeneficiaryService beneficiaryService;
    private final ConversionService conversionService;

    public ResponseEntity<JsonNode> createPayment(MyntCreatePaymentRequest request) {
        String beneficiaryId = request.getBeneficiaryId();
        String amount = request.getAmount();
        String fromCurrency = request.getFromCurrency().toUpperCase();
        String reason = request.getReason();
        String reference = request.getReference();
        String contactUuid = userContextService.getCurrentUserUuid();
        String uniqueRequestId = Instant.now().toString();
        System.out.println(uniqueRequestId);

        ResponseEntity<JsonNode> ccGetBeneficiaryResponse = beneficiaryService.get(beneficiaryId, contactUuid);
        if (!ccGetBeneficiaryResponse.getStatusCode().is2xxSuccessful()) return ccGetBeneficiaryResponse;
        JsonNode ccGetBeneficiaryResponseNode = ccGetBeneficiaryResponse.getBody();
        assert ccGetBeneficiaryResponseNode != null;
        String toCurrency = ccGetBeneficiaryResponseNode.get("currency").asText().toUpperCase();

        if (!fromCurrency.equals(toCurrency)) {
            ResponseEntity<JsonNode> convertResponse = convertBeforePayment(fromCurrency,toCurrency,amount);
            if (!convertResponse.getStatusCode().is2xxSuccessful()) return convertResponse;
        }

        CreatePaymentRequest createPaymentRequest = CreatePaymentRequest.builder()
                .onBehalfOf(contactUuid)
                .beneficiaryId(beneficiaryId)
                .amount(amount)
                .reason(reason)
                .reference(reference)
                .currency(toCurrency)
                .uniqueRequestId(uniqueRequestId)
                .build();

        ResponseEntity<JsonNode> ccCreatePaymentResponse = paymentService.create(createPaymentRequest).block();
        assert ccCreatePaymentResponse != null;
        if(!ccCreatePaymentResponse.getStatusCode().is2xxSuccessful()) return ccCreatePaymentResponse;

        return ResponseEntity.ok().build();
    }

    private ResponseEntity<JsonNode> convertBeforePayment(String fromCurrency, String toCurrency, String amount) {
        String contactUuid = userContextService.getCurrentUserUuid();
        CreateConversionRequest createConversionRequest = CreateConversionRequest.builder()
                .onBehalfOf(contactUuid)
                .sellCurrency(fromCurrency)
                .buyCurrency(toCurrency)
                .fixedSide("buy")
                .amount(amount)
                .termAgreement(true)
                .build();

        return conversionService.createConversion(createConversionRequest).block();
    }
}
