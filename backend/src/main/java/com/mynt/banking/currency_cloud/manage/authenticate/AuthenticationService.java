package com.mynt.banking.currency_cloud.manage.authenticate;

import com.mynt.banking.auth.JwtUserDetails;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
@Service("currencyCloudAuthenticationService")
public class AuthenticationService {

    private final WebClient webClient;

    private String authToken;

    @Value("${currency.cloud.api.url}")
    private String apiUrl;

    @Value("${currency.cloud.api.userAgent}")
    private String userAgent;

    @Value("${currency.cloud.api.login_id}")
    private String loginId;

    @Value("${currency.cloud.api.key}")
    private String apiKey;

    @PostConstruct
    public void init() {
        refreshAuthToken().block();
    }

    public Mono<AuthenticationResponse> authenticate() {



        return webClient.post()
                .uri("/v2/authenticate/api")
                .header("User-Agent", userAgent)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("login_id", loginId)
                        .with("api_key", apiKey))
                .retrieve()
                .bodyToMono(AuthenticationResponse.class);
    }

    private Mono<String> refreshAuthToken() {
        return authenticate()
                .map(AuthenticationResponse::getAuthToken)
                .doOnNext(token -> this.authToken = token);
    }

    @Bean
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }

}
