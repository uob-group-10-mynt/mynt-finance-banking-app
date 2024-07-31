package com.mynt.banking.currency_cloud.convert.rates;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.convert.rates.requests.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/rates")
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @PostMapping("/detailed")
    public Mono<ResponseEntity<JsonNode>> createAccount(@RequestBody GetDetailedRatesRequest request) {
        return rateService.getDetailedRates(request);
    }

    @PostMapping("/basic")
    public Mono<ResponseEntity<JsonNode>> getBasicRates(
            @RequestBody GetBasicRatesRequest request
    ) {
        return rateService.getBasicRates(
                request
        );
    }
}
