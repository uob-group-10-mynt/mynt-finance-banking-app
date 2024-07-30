package com.mynt.banking.mPesa.flutterwave;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flutterwave/")
@RequiredArgsConstructor
public class FlutterwaveController {

    private final FlutterwaveService flutterwaveService;

    //TODO: add enpoints

    //TODO: /v3/charges?type=mpesa

    //TODO: /v3/transactions/${ID}/verify

    //TODO: api end point to wrap mPesa depoist monies

    //TODO: api end point to send money to an mpesa account

}
