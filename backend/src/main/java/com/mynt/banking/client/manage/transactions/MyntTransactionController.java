package com.mynt.banking.client.manage.transactions;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor

public class MyntTransactionController {

    private final MyntTransactionService transactionService;

    @GetMapping
    public TransactionsDetailResponse find(
            @Valid
            @RequestParam(required = false) String currency,
            @RequestParam(required = false, name = "related_entity_type") String relatedEntityType,
            @RequestParam(required = false, name = "per_page") Integer perPage,
            @RequestParam(required = false) Integer page) {
        return transactionService.find(currency, relatedEntityType, perPage, page);
    }

    @GetMapping("/payment/{id}")
    public PaymentDetailResponse getPaymentDetail(@PathVariable String id) {
        return transactionService.getPaymentDetail(id);
    }
}




















