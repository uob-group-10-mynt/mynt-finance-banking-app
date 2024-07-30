package com.mynt.banking.mPesa.flutterwave;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.collect.funding.requests.FindAccountDetails;
import com.mynt.banking.mPesa.flutterwave.requests.MPesaToFlutterWearDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/flutterwave/")
@RequiredArgsConstructor
public class FlutterwaveController {

    private final FlutterwaveService flutterwaveService;


    //TODO: /v3/transactions/${ID}/verify
    @GetMapping("/transactions/{id}/verify")
    public Mono<ResponseEntity<JsonNode>> depoistTransactionCheck(@PathVariable(name = "id", required = true) String id) { //@RequestBody FindAccountDetails request
        return flutterwaveService.depoistTransactionCheck(id) ;
    }


    //TODO: deposit - Mpesa -> cc
    @PostMapping("/mPesaToFlutterWear")
    public Mono<ResponseEntity<JsonNode>> mPesaToFlutterWear(@RequestBody MPesaToFlutterWearDto request) {
        return flutterwaveService.mPesaToFlutterWear(request) ;
    }

    //TODO: wallet to wallet transfers
    //TODO: transfer - cc -> Mpesa
    //TODO: https://api.flutterwave.com/v3/charges?type=mpesa

    //TODO: https://api.flutterwave.com/v3/transfers/${ID}







}
