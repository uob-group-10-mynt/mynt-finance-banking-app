package com.mynt.banking.client.convert.rates;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.mynt.banking.client.convert.rates.requests.MyntUpdateBaseCurrencyRequest;
import com.mynt.banking.client.convert.rates.responses.MyntGetBasicRatesResponse;
import com.mynt.banking.currency_cloud.convert.rates.RateService;
import com.mynt.banking.currency_cloud.convert.rates.requests.GetBasicRatesRequest;
import com.mynt.banking.currency_cloud.manage.reference.ReferenceService;
import com.mynt.banking.user.User;
import com.mynt.banking.user.UserContextService;
import com.mynt.banking.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@RequiredArgsConstructor
public class MyntRatesService {

    private final UserContextService userContextService;
    private final UserRepository userRepository;
    private final RateService rateService;
    private final ReferenceService referenceService;

    public ResponseEntity<JsonNode> getBasicRates (
            String otherCurrencies
    ) throws NoSuchElementException {
        String currencyPair;
        User user = userRepository.findByEmail(userContextService.getCurrentUsername()).orElseThrow();
        String baseCurrency = user.getBaseCurrency().toUpperCase();
        String contactUUID = userContextService.getCurrentUserUuid();

        if (otherCurrencies == null || otherCurrencies.isBlank()) {
            currencyPair = currencyPairBuilder(baseCurrency, getAvailableCurrenciesAsStringList());
        }
        else {
            String[] arrOfCurrencies = otherCurrencies.split(",");
            currencyPair = currencyPairBuilder(baseCurrency, List.of(arrOfCurrencies));
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

    private List<String> getAvailableCurrenciesAsStringList() {
        List<String> codes = new LinkedList<>();

        ResponseEntity<JsonNode> ccResponse = referenceService.getAvailableCurrencies().block();
        assert ccResponse != null;
        assert ccResponse.getStatusCode().is2xxSuccessful();
        JsonNode currenciesNode = Objects.requireNonNull(ccResponse.getBody()).get("currencies");
        currenciesNode.forEach(node -> codes.add(node.get("code").asText()));

        return codes;
    }

    private String currencyPairBuilder(String baseCurrency, List<String> otherCurrencies) {
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

    public ResponseEntity<String> updateBaseCurrency(
            MyntUpdateBaseCurrencyRequest request
    ) throws NoSuchElementException {
        String newBaseCurrency = request.getNewBaseCurrency().toUpperCase();
        List<String> availableCurrencies = getAvailableCurrenciesAsStringList();
        if (!availableCurrencies.contains(newBaseCurrency)) {
            return ResponseEntity.badRequest().body("Currency not supported!");
        }

        User user = userRepository.findByEmail(userContextService.getCurrentUsername()).orElseThrow();
        user.setBaseCurrency(newBaseCurrency);
        userRepository.save(user);
        return ResponseEntity.ok("Successfully updated base currency with " + newBaseCurrency);
    }
}
