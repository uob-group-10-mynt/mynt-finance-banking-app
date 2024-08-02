package com.mynt.banking.client.convert.rates;


import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.convert.rates.requests.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/rates")
@RequiredArgsConstructor
public class MyntRatesController {

    private final MyntRatesService myntRatesService;

    @GetMapping("/getBasicRates")
    public Mono<ResponseEntity<JsonNode>> getBasicRates(
            @RequestBody MyntGetBasicRatesRequest request
    ) {
        return Mono.just(myntRatesService.getBasicRates(request));
    }

}