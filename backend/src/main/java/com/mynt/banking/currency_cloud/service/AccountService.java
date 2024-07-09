package com.mynt.banking.currency_cloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
import com.mynt.banking.currency_cloud.dto.LoginDto;
import com.mynt.banking.currency_cloud.interceptor.AuthenticationInterceptor;
import com.mynt.banking.currency_cloud.interceptor.ErrorHandlingInterceptor;
import com.mynt.banking.currency_cloud.interceptor.LoggingInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import javax.swing.text.html.parser.Entity;
import java.time.Duration;
import java.util.Iterator;
import java.util.Map;

@Service
public class AccountService {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String USER_AGENT;

    @Value("${currency.cloud.api.key}")
    private String apiKey;

    @Value("${currency.cloud.api.login_id}")
    private String loginId;

    private WebClient webClient;

    private String auth_token;

    private final AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public AccountService(AuthenticationInterceptor authenticationInterceptor){
        this.authenticationInterceptor = authenticationInterceptor;
        this.webClient = webClient();
    }

    public String login() throws JsonProcessingException {

        LoginDto dto = new LoginDto();
        dto.setLogin_id(loginId);
        dto.setApi_key(apiKey);


        String response = this.webClient
                .post()
                .uri("https://devapi.currencycloud.com" +"/v2/authenticate/api")
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(response);
//        System.out.println("\n\n\n\ntree: "+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));
        this.auth_token = tree.get("auth_token").asText();

        return response;
    }


    public String createAccount(AccountRequest requestBody) throws JsonProcessingException {

        this.login();

//        .body(BodyInserters.fromValue(requestBody))

         String response =  this.webClient
                 .post()
                 .uri("https://devapi.currencycloud.com" + "/v2/accounts/create")
                 .header("X-Auth-Token",this.auth_token)
                 .body(BodyInserters.fromValue(requestBody))
                 .retrieve()
                 .bodyToMono(String.class)
                 .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(response);
        System.out.println("\n\n\n\nmapper"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));

        return response;
    }

    private WebClient webClient(){
        return WebClient.create();
    }



}
