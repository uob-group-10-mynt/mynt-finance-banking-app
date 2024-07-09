package com.mynt.banking.currency_cloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import com.mynt.banking.currency_cloud.dto.AuthenticationResponse;
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

import java.time.Duration;

@Service
public class AccountService {

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String USER_AGENT;

    private final WebClient webClient;

    private final AuthenticationInterceptor authenticationInterceptor;

    @Autowired
    public AccountService(AuthenticationInterceptor authenticationInterceptor  ){

        this.authenticationInterceptor = authenticationInterceptor;
        this.webClient = webClient();
    }

    public String createAccount(AccountRequest requestBody) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode ogTree = objectMapper.valueToTree(requestBody);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ogTree));

         String response =  this.webClient.post()
                 .uri(apiUrl + "/v2/accounts/create")
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(BodyInserters.fromValue(requestBody))
                 .retrieve()
                 .bodyToMono(String.class)
                 .block();

         System.out.println("\n\n\n\nCreated account " + response);

        return response;
    }




    private WebClient webClient(){
        return WebClient.builder()
                .baseUrl(apiUrl)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
                        .responseTimeout(Duration.ofSeconds(10))))
                .filter(LoggingInterceptor.logRequest())
                .filter(LoggingInterceptor.logResponse())
                .filter(ErrorHandlingInterceptor.handleErrors())
                .filter(authenticationInterceptor.applyAuthentication())
                .build();
    }



}
