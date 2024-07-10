package com.mynt.mynt.service;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mynt.banking.currency_cloud.service.AccountService;
import com.mynt.banking.main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = main.class)
public class AccountCCTest {

    @Autowired
    private AccountService sampleService;

    @Test
    public void contextLoads() {
        assertThat(sampleService).isNotNull();
    }

    @Test
    public void testGreet() throws JsonProcessingException {
        String result = sampleService.login();
        assertThat(result).isNotNull();
    }
}