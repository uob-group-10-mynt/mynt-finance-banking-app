package com.mynt.banking.client.manage.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor

public class MyntTransactionController {

    private final MyntTransactionService transactionService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/find")
    public TransactionResponse findTransaction(
            @RequestParam(required = false) String currency,
            @RequestParam(required = false, name = "related_entity_type") String relatedEntityType,
            @RequestParam(required = false, name = "per_page") Integer perPage,
            @RequestParam(required = false) Integer page) {
        return transactionService.findTransaction(currency, relatedEntityType, perPage, page);
    }

}
