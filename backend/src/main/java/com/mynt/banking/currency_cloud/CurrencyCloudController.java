package com.mynt.banking.currency_cloud;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currency-cloud-api")
@AllArgsConstructor
public class CurrencyCloudController {

    private final CurrencyCloudService currencyCloudService;

    @GetMapping("/authenticate")
    public String authenticate() {
//        return currencyCloudService.authenticate();
        return "";
    }
}
