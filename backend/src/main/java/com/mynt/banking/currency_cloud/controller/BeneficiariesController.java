package com.mynt.banking.currency_cloud.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.dto.beneficiaries.FindBeneficiaries;
import com.mynt.banking.currency_cloud.service.BeneficiariesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/payments")
@RequiredArgsConstructor
public class BeneficiariesController {

    private final BeneficiariesService beneficiariesService;

    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> find(@RequestBody FindBeneficiaries requestBody){
        return beneficiariesService.find(requestBody) ;
    }

}
