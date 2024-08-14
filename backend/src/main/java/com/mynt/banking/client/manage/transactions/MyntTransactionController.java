package com.mynt.banking.client.manage.transactions;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor

public class MyntTransactionController {

    private final MyntTransactionService transactionService;

    @GetMapping
    public MyntTransactionsDetailResponse find(
            @Valid
            @RequestParam(required = false) String currency,
            @RequestParam(required = false, name = "related_entity_type") String relatedEntityType,
            @RequestParam(required = false, name = "created_at_from")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate createdAtFrom,
            @RequestParam(required = false, name = "created_at_to")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate createdAtTo,
            @RequestParam(required = false, name = "per_page") Integer perPage,
            @RequestParam(required = false) Integer page) {
        return transactionService.find(currency, relatedEntityType, createdAtFrom, createdAtTo, perPage, page);
    }

    @GetMapping("/payment/{transaction_id}")
    public MyntPaymentDetailResponse getPaymentDetail(@PathVariable String transaction_id) {
        return transactionService.getPaymentDetail(transaction_id);
    }

    @GetMapping("/conversion/{transaction_id}")
    public MyntConversionDetailResponse getConversionDetail(@PathVariable String transaction_id) {
        return transactionService.getConversionDetail(transaction_id);
    }
}




















