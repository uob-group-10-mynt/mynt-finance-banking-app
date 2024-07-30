package com.mynt.banking.client.convert.rates;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.client.convert.rates.requests.*;
import com.mynt.banking.currency_cloud.convert.rates.RateService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MyntRatesService {

    private final RateService rateService;
    private final String[] SUPPORTED_CURRENCIES = {
            "AUD",
            "JPY",
            "BHD",
            "KES",
            "SAR",
            "CAD",
            "KWD",
            "SGD",
            "CNH",
            "MYR",
            "ZAR",
            "CZK",
            "MXN",
            "SEK",
            "DKK",
            "NZD",
            "CHF",
            "EUR",
            "NOK",
            "THB",
            "HKD",
            "OMR",
            "TRY",
            "PHP",
            "UGX",
            "INR",
            "PLN",
            "GBP",
            "IDR",
            "QAR",
            "AED",
            "ILS",
            "RON"
    };


    public Mono<ResponseEntity<JsonNode>> getBasicRates(
            @NotNull MyntGetBasicRatesRequest request
    ) {
        String currencyPair;
        String baseCurrency = request.getBaseCurrency();
        String otherCurrencies = request.getOtherCurrencies();

        if (otherCurrencies.isBlank()) {
            currencyPair = currencyPairBuilder(baseCurrency, SUPPORTED_CURRENCIES);
        }
        else {
            String[] arrOfCurrencies = otherCurrencies.split(",");
            currencyPair = currencyPairBuilder(baseCurrency, arrOfCurrencies);
        }
        
    }

    private String currencyPairBuilder(String baseCurrency, String[] otherCurrencies) {
        StringBuilder currencyPair = new StringBuilder();
        String delimiter = "";
        for (String curr : otherCurrencies) {
            if (curr.equals(baseCurrency)) continue;

            currencyPair
                    .append(delimiter)
                    .append(baseCurrency)
                    .append(curr);
            delimiter = ",";
        }
        return currencyPair.toString();
    }

}
