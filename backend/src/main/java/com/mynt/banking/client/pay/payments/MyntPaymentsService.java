package com.mynt.banking.client.pay.payments;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.client.pay.payments.requests.MyntCreatePaymentRequest;
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

    public ResponseEntity<JsonNode> createPayment(MyntCreatePaymentRequest request) {
        String beneficiaryId = request.getBeneficiaryId();
        String amount = request.getAmount();
        String reason = request.getReason();
        String reference = request.getReference();
        String contactUuid = userContextService.getCurrentUserUuid();
        String uniqueRequestId = Instant.now().toString();
        System.out.println(uniqueRequestId);

        ResponseEntity<JsonNode> ccGetBeneficiaryResponse = beneficiaryService.get(beneficiaryId, contactUuid);
        if (!ccGetBeneficiaryResponse.getStatusCode().is2xxSuccessful()) return ccGetBeneficiaryResponse;
        JsonNode ccGetBeneficiaryResponseNode = ccGetBeneficiaryResponse.getBody();
        assert ccGetBeneficiaryResponseNode != null;
        String currency = ccGetBeneficiaryResponseNode.get("currency").asText();

        CreatePaymentRequest createPaymentRequest = CreatePaymentRequest.builder()
                .onBehalfOf(contactUuid)
                .beneficiaryId(beneficiaryId)
                .amount(amount)
                .reason(reason)
                .reference(reference)
                .currency(currency)
                .uniqueRequestId(uniqueRequestId)
                .build();

        ResponseEntity<JsonNode> ccCreatePaymentResponse = paymentService.create(createPaymentRequest).block();
        assert ccCreatePaymentResponse != null;
        if(!ccCreatePaymentResponse.getStatusCode().is2xxSuccessful()) return ccCreatePaymentResponse;

        return ResponseEntity.ok().build();
    }
}
