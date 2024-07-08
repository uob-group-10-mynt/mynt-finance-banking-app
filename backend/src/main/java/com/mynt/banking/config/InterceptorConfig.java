package com.mynt.banking.config;

import com.mynt.banking.currency_cloud.interceptor.AuthenticationInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mynt.banking.currency_cloud.service.AuthenticationProvider;
import org.springframework.context.annotation.Lazy;

@Configuration
@AllArgsConstructor
public class InterceptorConfig {

    private final AuthenticationProvider authenticationProvider;

    @Bean
    @Lazy
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor(authenticationProvider::getAuthToken);
    }
}
