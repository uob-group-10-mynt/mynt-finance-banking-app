package com.mynt.banking.currency_cloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@Service
public class BeneficiariesService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;


}
