package com.mynt.banking.client.pay.payments;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.pay.payments.requests.MyntCreatePaymentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class MyntPaymentsController {

    private final MyntPaymentsService myntPaymentsService;

    @PostMapping("/createPayment")
    public Mono<ResponseEntity<JsonNode>> createPayment(
            @Valid @RequestBody MyntCreatePaymentRequest request
    ) {
        return Mono.just(myntPaymentsService.createPayment(request));
    }
}