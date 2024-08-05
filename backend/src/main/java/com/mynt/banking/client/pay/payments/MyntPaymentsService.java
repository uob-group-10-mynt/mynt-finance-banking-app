package com.mynt.banking.client.pay.payments;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.client.pay.payments.requests.MyntCreatePaymentRequest;
import com.mynt.banking.currency_cloud.pay.payments.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyntPaymentsService {
    private final PaymentService paymentService;

    public ResponseEntity<JsonNode> createPayment(MyntCreatePaymentRequest request) {



        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode response = objectMapper.createObjectNode();

        return ResponseEntity.ok(response);
    }
}
