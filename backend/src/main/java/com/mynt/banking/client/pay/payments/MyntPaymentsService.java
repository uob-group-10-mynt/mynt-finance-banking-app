package com.mynt.banking.client.pay.payments;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mynt.banking.client.pay.payments.requests.MyntCreatePaymentRequest;
import com.mynt.banking.currency_cloud.convert.conversions.ConversionService;
import com.mynt.banking.currency_cloud.convert.conversions.requests.CreateConversionRequest;
import com.mynt.banking.currency_cloud.convert.rates.RateService;
import com.mynt.banking.currency_cloud.convert.rates.requests.GetBasicRatesRequest;
import com.mynt.banking.currency_cloud.manage.balances.BalanceService;
import com.mynt.banking.currency_cloud.manage.balances.GetBalanceRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.BeneficiaryService;
import com.mynt.banking.currency_cloud.pay.payments.PaymentService;
import com.mynt.banking.currency_cloud.pay.payments.requests.CreatePaymentRequest;
import com.mynt.banking.user.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MyntPaymentsService {
    private final UserContextService userContextService;
    private final PaymentService paymentService;
    private final BeneficiaryService beneficiaryService;
    private final ConversionService conversionService;
    private final BalanceService balanceService;
    private final RateService rateService;

    public ResponseEntity<JsonNode> createPayment(MyntCreatePaymentRequest request) {
        String beneficiaryId = request.getBeneficiaryId();
        String amount = request.getAmount();
        String fromCurrency = request.getFromCurrency().toUpperCase();
        String reason = request.getReason();
        String reference = request.getReference();
        String contactUuid = userContextService.getCurrentUserUuid();
        String uniqueRequestId = Instant.now().toString();

        ResponseEntity<JsonNode> ccGetBeneficiaryResponse = beneficiaryService.get(beneficiaryId, contactUuid);
        if (!ccGetBeneficiaryResponse.getStatusCode().is2xxSuccessful()) return ccGetBeneficiaryResponse;
        JsonNode ccGetBeneficiaryResponseNode = ccGetBeneficiaryResponse.getBody();
        assert ccGetBeneficiaryResponseNode != null;
        String toCurrency = ccGetBeneficiaryResponseNode.get("currency").asText().toUpperCase();

        double sellAmount = 0;
        if (fromCurrency.equals(toCurrency)) {
            sellAmount = Double.parseDouble(amount);
        }
        else {
            ResponseEntity<JsonNode> rateResponse = getRateForSellAmount(fromCurrency, toCurrency);
            if (!rateResponse.getStatusCode().is2xxSuccessful()) return rateResponse;
            double rate = Objects.requireNonNull(rateResponse.getBody()).get("rates").get(0).get(0).asDouble();
            sellAmount = rate * Double.parseDouble(amount);
        }

        if (!hasEnoughBalance(fromCurrency, sellAmount)) {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode notEnoughBalanceBody = objectMapper.createObjectNode();
            notEnoughBalanceBody.put("Error", "Not enough balance in " + fromCurrency + " account");
            return ResponseEntity.badRequest().body(notEnoughBalanceBody);
        }
        if (!fromCurrency.equals(toCurrency)) {
            ResponseEntity<JsonNode> convertResponse = convertBeforePayment(fromCurrency,toCurrency,amount);
            if (!convertResponse.getStatusCode().is2xxSuccessful()) return convertResponse;
        }

        CreatePaymentRequest createPaymentRequest = CreatePaymentRequest.builder()
                .onBehalfOf(contactUuid)
                .beneficiaryId(beneficiaryId)
                .amount(amount)
                .reason(reason)
                .reference(reference)
                .currency(toCurrency)
                .uniqueRequestId(uniqueRequestId)
                .build();

        ResponseEntity<JsonNode> ccCreatePaymentResponse = paymentService.create(createPaymentRequest).block();
        assert ccCreatePaymentResponse != null;
        if(!ccCreatePaymentResponse.getStatusCode().is2xxSuccessful()) return ccCreatePaymentResponse;

        return ResponseEntity.ok().build();
    }

    private boolean hasEnoughBalance(String currency, double amount) {
        String contactUuid = userContextService.getCurrentUserUuid();
        GetBalanceRequest getBalanceRequest = GetBalanceRequest.builder()
                .onBehalfOf(contactUuid)
                .build();
        ResponseEntity<JsonNode> getBalanceResponse = balanceService.getBalance(currency,getBalanceRequest).block();
        assert getBalanceResponse != null;
        if (!getBalanceResponse.getStatusCode().is2xxSuccessful()) return false;
        double balance = Objects.requireNonNull(getBalanceResponse.getBody()).get("amount").asDouble();
        return balance > amount;
    }

    private ResponseEntity<JsonNode> getRateForSellAmount(String sellCurrency, String buyCurrency) {
        String contactUuid = userContextService.getCurrentUserUuid();
        GetBasicRatesRequest getBasicRatesRequest = GetBasicRatesRequest.builder()
                .onBehalfOf(contactUuid)
                .currencyPair(sellCurrency+buyCurrency)
                .build();
        return rateService.getBasicRates(getBasicRatesRequest).block();
    }
    private ResponseEntity<JsonNode> convertBeforePayment(String fromCurrency, String toCurrency, String amount) {
        String contactUuid = userContextService.getCurrentUserUuid();
        CreateConversionRequest createConversionRequest = CreateConversionRequest.builder()
                .onBehalfOf(contactUuid)
                .sellCurrency(fromCurrency)
                .buyCurrency(toCurrency)
                .fixedSide("buy")
                .amount(amount)
                .termAgreement(true)
                .build();
        return conversionService.createConversion(createConversionRequest).block();
    }
}
