package com.mynt.banking.currency_cloud.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.balances.FindBalanceAllCurrenies;
import com.mynt.banking.currency_cloud.dto.balances.FindBalances;
import com.mynt.banking.currency_cloud.service.BalanceService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> find(FindBalanceAllCurrenies request) {
        return balanceService.find(request);
    }

    @PostMapping("/find/{currencyCode}/")
    public Mono<ResponseEntity<JsonNode>> find( @Schema(description = "Three-letter ISO currency code.")
                                                @PathVariable(name = "currencyCode",required = true) String currencyCode ,
                                                FindBalances request) {
        return balanceService.findForParticularCurrency(request, currencyCode);
    }


}
