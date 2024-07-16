package com.mynt.banking.currency_cloud.manage.contacts;

import com.mynt.banking.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ContactsService {

    private final AuthenticationService authenticationService;
    private final WebClient webClient;



}
