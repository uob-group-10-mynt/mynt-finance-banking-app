package com.mynt.banking.currency_cloud;

import com.currencycloud.client.model.Account;
import com.currencycloud.client.model.DetailedRate;
import com.mynt.banking.currency_cloud.request.CreateAccountRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/currency-cloud-api")
@AllArgsConstructor
public class CurrencyCloudController {

    private final CurrencyCloudClientService currencyCloudClientService;

    @GetMapping("/get-detailed-rate")
    public Optional<DetailedRate> getRate() {
        try {
            return currencyCloudClientService.getDetailedRate("EUR", "GBP", "buy", new BigDecimal("12345.67"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, "Failed to get rate", e);
        }
    }

    @PostMapping("accounts/create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        try {
            currencyCloudClientService.createAccount(request);
            return new ResponseEntity<>("Account created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

