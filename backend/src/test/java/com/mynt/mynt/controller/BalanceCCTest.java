package com.mynt.mynt.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.controller.BalanceController;
import com.mynt.banking.currency_cloud.dto.balances.FindBalanceAllCurrenies;
import com.mynt.banking.currency_cloud.service.BalanceService;

import com.mynt.banking.main;
import com.mynt.banking.util.HashMapToQuiryPrams;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@ContextConfiguration(classes = main.class)
@WebFluxTest(controllers = BalanceController.class)
public class BalanceCCTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BalanceService balanceService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    @WithMockUser
    public void testBalanceCC() throws Exception {


        FindBalanceAllCurrenies data = FindBalanceAllCurrenies.builder().
                amountTo("")
                .order("")
                .amountFrom("")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map = objectMapper.convertValue(data, HashMap.class);
        String url = "/api/v1/currency-cloud/balances/find" ; //+HashMapToQuiryPrams.hashMapToString(map);

        JsonNode responseBody = webTestClient.get()
                .uri(url)
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(JsonNode.class)
                .getResponseBody()
                .blockFirst();


        System.out.println(responseBody);


    }



}
