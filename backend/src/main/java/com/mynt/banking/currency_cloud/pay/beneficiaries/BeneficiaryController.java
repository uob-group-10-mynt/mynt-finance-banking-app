package com.mynt.banking.currency_cloud.pay.beneficiaries;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.FindBeneficiaryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/currency-cloud/beneficiaries")
@RequiredArgsConstructor
public class BeneficiaryController {

    private final BeneficiaryService beneficiariesService;

    @PostMapping("/find")
    public Mono<ResponseEntity<JsonNode>> find(@RequestBody FindBeneficiaryRequest requestBody){
        return beneficiariesService.find(requestBody) ;
    }

}
