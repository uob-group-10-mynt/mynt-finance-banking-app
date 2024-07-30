package com.mynt.banking.mPesa.flutterwave;

import com.mynt.banking.currency_cloud.manage.authenticate.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class FlutterwaveService {

    private final AuthenticationService authenticationService;

    private final WebClient webClient;

    //TODO: POST and GET Requests

}
