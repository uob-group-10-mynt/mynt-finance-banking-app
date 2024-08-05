package com.mynt.banking.client.convert.rates;


import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.convert.rates.requests.*;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @Pattern(regexp = "([A-z]{3}(,[A-z]{3})*)?", message = "please follow the pattern \"\" or \"gbp\" or \"gbp,usd,eur\"")
            @Parameter(description = "Pattern: \"\" or \"gbp\" or \"gbp,usd,eur\"")
            @RequestParam(value = "other_currencies", required = false)
            String otherCurrencies
    ) {
        return Mono.just(myntRatesService.getBasicRates(otherCurrencies));
    }

    @PostMapping("updateBaseCurrency")
    public Mono<ResponseEntity<String>> updateBaseCurrency(
            @Valid
            @RequestBody MyntUpdateBaseCurrencyRequest request
    ) {
        ResponseEntity<String> response;
        try{
            response = myntRatesService.updateBaseCurrency(request);
        }
        catch (Exception e) {
            return Mono.just(ResponseEntity.badRequest().build());
        }
        return Mono.just(response);
    }
}