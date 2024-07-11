package com.mynt.banking.currency_cloud.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.balances.FindBalanceAllCurrenies;
import com.mynt.banking.currency_cloud.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/balances")
@RequiredArgsConstructor
public class BalanceController {

    private final BalanceService balanceService;

    @GetMapping("/find")
    public Mono<ResponseEntity<JsonNode>> find(FindBalanceAllCurrenies request) {

//        @RequestParam(name = "on_behalf_of", required = false) String onBehalfOf,
//        @RequestParam(name = "amount_from", required = false) String amountFrom,
//        @RequestParam(name = "amount_to", required = false) String amountTo,
//        @RequestParam(name = "as_at_date", required = false) String asAtDate,
//        @RequestParam(name = "scope", required = false) String createdAt,
//        @RequestParam(name = "page", required = false) String page,
//        @RequestParam(name = "per_page", required = false) String perPage,
//        @RequestParam(name = "order", required = false) String order,
//        @RequestParam(name = "order_asc_desc", required = false) String orderAscDesc

//        System.out.println("\n\n\n\nnotes: "+onBehalfOf+
//                amountFrom+
//                amountTo+
//                asAtDate+
//                createdAt+
//                page+
//                perPage+
//                order+
//                orderAscDesc);

//        onBehalfOf,amountFrom,amountTo,asAtDate,createdAt,page,perPage,order,orderAscDesc

//        balanceService.find(request);
        return balanceService.find(request);
    }


}
