package com.mynt.banking.mPesa;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.mPesa.requests.MPesaToCurrencyCloudDto;
import com.mynt.banking.mPesa.requests.MPesaToFlutterWearDto;
import com.mynt.banking.mPesa.requests.SendMpesaDto;
import com.mynt.banking.mPesa.requests.Wallet2WalletDto;
import com.mynt.banking.user.UserContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/flutterwave/")
@RequiredArgsConstructor
public class FlutterwaveController {

    private final FlutterwaveService flutterwaveService;

    private final UserContextService userContextService;

    // /v3/transactions/${ID}/verify
    @GetMapping("/transactions/{id}/verify")
    public Mono<ResponseEntity<JsonNode>> depoistTransactionCheck(@PathVariable(name = "id", required = true) String id) { //@RequestBody FindAccountDetails request
        return flutterwaveService.depoistTransactionCheck(id) ;
    }

    // https://api.flutterwave.com/v3/transfers/${ID}
    @GetMapping("/transfers/{id}")
    public Mono<ResponseEntity<JsonNode>> transactionCheck(@PathVariable(name = "id", required = true) String id) { //@RequestBody FindAccountDetails request
        return flutterwaveService.transactionCheck(id) ;
    }

    // deposit - Mpesa -> cc
    @PostMapping("/mPesaToFlutterWear")
    public Mono<ResponseEntity<JsonNode>> mPesaToFlutterWear(@RequestBody MPesaToFlutterWearDto request) {
        return flutterwaveService.mPesaToFlutterwave(request) ;
    }

    // wallet to wallet transfers
    @PostMapping("/walletNgn2Kes")
    public Mono<ResponseEntity<JsonNode>> walletNgn2Kes(@RequestBody Wallet2WalletDto request) {
        return flutterwaveService.wallet2Wallet(request);
    }

    // transfer - flutterwave -> Mpesa
    @PostMapping("/sendMpesa")
    public Mono<ResponseEntity<JsonNode>> sendMpesa(@RequestBody SendMpesaDto request) {
        return flutterwaveService.sendMPesa(request);
    }

    // mpesa to CC including CC methrods
    @PostMapping("/sendMpesaToCurrencyCloud")
    public ResponseEntity<JsonNode> mpesaToCloudCurrency(@RequestBody MPesaToCurrencyCloudDto request) {
        return flutterwaveService.mpesaToCloudCurrency(request,userContextService.getCurrentUsername());
    }

    //TODO: Intergrate - CC to mpesa including CC methrods

}
