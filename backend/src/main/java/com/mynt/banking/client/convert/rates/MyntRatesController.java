package com.mynt.banking.client.convert.rates;


import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.convert.rates.requests.*;
import io.swagger.v3.oas.annotations.Parameter;
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
            @Pattern(regexp = "^([A-z]{3})?$", message = "please follow the pattern \"\" or \"gbp\"")
            @Parameter(description = "Sell currency. " +
                    "If left empty, user's base currency stored in database will be used. " +
                    "Pattern: \"\" or \"gbp\"")
            @RequestParam(value = "sell_currency", required = false)
            String sellCurrency,
            @Valid
            @Size(max = 100, message = "size exceeded limit")
            @Pattern(regexp = "^([A-z]{3}(,[A-z]{3})*)?$", message = "please follow the pattern \"\" or \"gbp\" or \"gbp,usd,eur\"")
            @Parameter(description = "Buy currencies. " +
                    "If left empty, this will be all supported currencies by Currency Cloud. " +
                    "Pattern: \"\" or \"gbp\" or \"gbp,usd,eur\"")
            @RequestParam(value = "buy_currencies", required = false)
            String buyCurrencies
    ) {
        return Mono.just(myntRatesService.getBasicRates(sellCurrency, buyCurrencies));
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