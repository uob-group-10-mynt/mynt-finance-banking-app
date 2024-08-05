package com.mynt.banking.client.convert.rates;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mynt.banking.client.convert.rates.responses.MyntGetBasicRatesResponse;
import com.mynt.banking.currency_cloud.convert.rates.RateService;
import com.mynt.banking.currency_cloud.convert.rates.requests.GetBasicRatesRequest;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class MyntRatesService {

    private final UserContextService userContextService;
    private final UserRepository userRepository;
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


    public ResponseEntity<JsonNode> getBasicRates (
            String otherCurrencies
    ) throws NoSuchElementException {
        String currencyPair;
        User user = userRepository.findByEmail(userContextService.getCurrentUsername()).orElseThrow();
        String baseCurrency = user.getBaseCurrency().toUpperCase();
        String contactUUID = userContextService.getCurrentUserUuid();

        if (otherCurrencies == null || otherCurrencies.isBlank()) {
            currencyPair = currencyPairBuilder(baseCurrency, SUPPORTED_CURRENCIES);
        }
        else {
            String[] arrOfCurrencies = otherCurrencies.split(",");
            currencyPair = currencyPairBuilder(baseCurrency, arrOfCurrencies);
        }

        GetBasicRatesRequest getBasicRatesRequest = GetBasicRatesRequest.builder()
                .currencyPair(currencyPair)
                .onBehalfOf(contactUUID)
                .build();

        ResponseEntity<JsonNode> ccResponse =  rateService.getBasicRates(getBasicRatesRequest).block();

        assert ccResponse != null;
        if (ccResponse.getStatusCode().isError()) return ccResponse;

        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode response = objectMapper.createArrayNode();

        MyntGetBasicRatesResponse firstElement = MyntGetBasicRatesResponse.builder()
                .currency(baseCurrency)
                .rate("1")
                .build();
        JsonNode firstRate = objectMapper.convertValue(firstElement, JsonNode.class);
        response.add(firstRate);

        JsonNode ccRates = Objects.requireNonNull(ccResponse.getBody()).get("rates");
        ccRates.fieldNames().forEachRemaining( key -> {
            MyntGetBasicRatesResponse responseElement = MyntGetBasicRatesResponse.builder()
                    .currency(key.toUpperCase().replace(baseCurrency, ""))
                    .rate(ccRates.get(key).get(0).asText())
                    .build();
            JsonNode rate = objectMapper.convertValue(responseElement, JsonNode.class);
            response.add(rate);
        });
        return ResponseEntity.ok(response);

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
