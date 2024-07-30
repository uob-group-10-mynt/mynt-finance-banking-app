package com.mynt.banking.currency_cloud.collect.funding;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.collect.demo.requests.DemoFundingDto;
import com.mynt.banking.currency_cloud.collect.funding.requests.FindAccountDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/funding_accounts/")
@RequiredArgsConstructor
public class FundingController {

    private final FundingService fundingService;

    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> createAccount(@RequestBody FindAccountDetails request) {
        return fundingService.find(request) ;
    }

}
