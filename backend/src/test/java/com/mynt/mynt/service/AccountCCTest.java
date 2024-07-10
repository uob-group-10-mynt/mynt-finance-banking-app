package com.mynt.mynt.service;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.AccountRequest;
import com.mynt.banking.currency_cloud.service.AccountService;
import com.mynt.banking.main;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.constraints.AssertTrue;

@SpringBootTest(classes = main.class)
public class AccountCCTest {

    @Autowired
    private AccountService accountService;

//    @Autowired
//    private AccountRequest accountRequest;

    @Test
    public void contextLoads() {
        assertThat(accountService).isNotNull();
    }

    @Test
    public void testLogin() throws JsonProcessingException {
        String result = accountService.login();
        assertThat(result).isNotNull();
    }

    @Test
    public void testCreateAccount() throws JsonProcessingException {

        //TODO: fill out all of the body
        AccountRequest requestBody = AccountRequest.builder()
                .accountName("hello123")
                .legalEntityType("individual")
                .street("The Mall")
                .city("London")
                .country("GB")
                .build();

        String result = accountService.createAccount(requestBody);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(result);
//        System.out.println("\n\n\n\noutput: "+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));

        assertThat(tree.get("id").asText()).isNotNull();
        assertEquals(tree.get("account_name").asText(),"hello123");
        assertEquals(tree.get("street").asText(),"The Mall");
        assertEquals(tree.get("legal_entity_type").asText(),"individual");
        assertEquals(tree.get("city").asText(),"London");
        assertEquals(tree.get("country").asText(),"GB");
    }

    @Test
    public void testCreateAccountPartialDTOFillOut() throws JsonProcessingException {

        AccountRequest requestBody = AccountRequest.builder()
                .accountName("hello123")
                .legalEntityType("individual")
                .street("The Mall")
                .city("London")
                .country("GB")
                .build();

        String result = accountService.createAccount(requestBody);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = mapper.readTree(result);
        System.out.println("\n\n\n\noutput: "+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tree));

        assertThat(tree.get("id").asText()).isNotNull();
        assertEquals(tree.get("account_name").asText(),"hello123");
        assertEquals(tree.get("street").asText(),"The Mall");
        assertEquals(tree.get("legal_entity_type").asText(),"individual");
        assertEquals(tree.get("city").asText(),"London");
        assertEquals(tree.get("country").asText(),"GB");
    }

}