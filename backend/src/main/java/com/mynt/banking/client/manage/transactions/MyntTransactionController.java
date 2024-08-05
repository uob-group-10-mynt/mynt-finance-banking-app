package com.mynt.banking.client.manage.transactions;

import jakarta.validation.Valid;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor

public class MyntTransactionController {

    private final MyntTransactionService transactionService;

    @GetMapping
    public TransactionDetailResponse findTransactions(
            @Valid
            @RequestParam(required = false) String currency,
            @RequestParam(required = false, name = "related_entity_type") String relatedEntityType,
            @RequestParam(required = false, name = "per_page") Integer perPage,
            @RequestParam(required = false) Integer page) {
        return transactionService.findTransaction(currency, relatedEntityType, perPage, page);
    }

    @GetMapping("/payment/{transaction_id}")
    public PaymentDetailResponse getPaymentDetail(@PathVariable String transaction_id) {
        return transactionService.getPaymentDetail(transaction_id);
    }
}




















