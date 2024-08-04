package com.mynt.banking.client.convert.rates;


import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
            @Valid
            @Size(max = 100, message = "size exceeded limit")
            @Pattern(regexp = "[A-z]{3}(,[A-z]{3})*", message = "please follow the pattern \"\" or \"abc\" or \"abc,abc,abc\"")
            @RequestParam("other_currencies")
            String otherCurrencies
    ) {
        return Mono.just(myntRatesService.getBasicRates(otherCurrencies));
    }

}