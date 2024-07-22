package com.mynt.banking.currency_cloud.manage.transactions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalanceAllCurrencies;
import com.mynt.banking.currency_cloud.manage.transactions.requests.FindTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/transactions")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/find")
    public Mono<ResponseEntity<JsonNode>> find(@RequestParam FindTransaction request) {
        return this.transactionService.find(request);
    }

    @GetMapping("/{id}/")
    public Mono<ResponseEntity<JsonNode>> find(@PathVariable(name = "id",required = true) String id,
                                               @RequestParam String onBehalfOfId) {
        return this.transactionService.findTransactionID(id, onBehalfOfId);
    }

}
