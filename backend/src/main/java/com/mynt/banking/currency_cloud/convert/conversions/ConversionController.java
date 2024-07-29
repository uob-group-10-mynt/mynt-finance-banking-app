package com.mynt.banking.currency_cloud.convert.conversions;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.convert.conversions.requests.CreateConversionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/conversion")
@RequiredArgsConstructor
public class ConversionController {

    private final ConversionService conversionService;

    @PostMapping("/create")
    public Mono<ResponseEntity<JsonNode>> createAccount(@RequestBody CreateConversionRequest request) {
        return conversionService.createConversion(request);
    }
}
