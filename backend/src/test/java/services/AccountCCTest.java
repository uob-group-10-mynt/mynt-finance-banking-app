package services;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.mynt.banking.currency_cloud.dto.AccountRequest;
//import com.mynt.banking.currency_cloud.service.AccountService;
import com.mynt.banking.currency_cloud.dto.CreateAccountRequest;
import com.mynt.banking.currency_cloud.dto.CreateAccountResponse;
import com.mynt.banking.currency_cloud.service.AccountService;
import com.mynt.banking.main;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import javax.validation.constraints.AssertTrue;
import java.util.Objects;

@SpringBootTest(classes = main.class)
public class AccountCCTest {

    @Autowired
    private AccountService accountService;

//    @Test
//    public void contextLoads() {
//        assertThat(accountService).isNotNull();
//    }

//    @Test
//    public void testLogin() throws JsonProcessingException {
//        String result = accountService.login();
//        assertThat(result).isNotNull();
//    }

    @Test
    public void testCreateAccount() throws JsonProcessingException {

        CreateAccountRequest requestBody = CreateAccountRequest.builder()
                .accountName("hello123")
                .legalEntityType("individual")
                .street("The Mall")
                .city("London")
                .country("GB")
                .postalCode("SW1A 1AA")
                .stateOrProvince("string")
                .brand("currencycloud")
                .yourReference("MyntFinance")
                .status("enabled")
                .spreadTable("")
                .identificationType("none")
                .identificationValue("")
                .apiTrading(true)
                .onlineTrading(true)
                .phoneTrading(true)
                .termsAndConditionsAccepted(true)
                .build();

        ResponseEntity<JsonNode> result = accountService.createAccount(requestBody).block();

        assert result != null;
        int responseCode = result.getStatusCode().value();
        assertEquals(responseCode, 200);

        JsonNode responseBody = result.getBody();
        assert responseBody != null;
        assertThat(responseBody.get("id").asText()).isNotNull();
        assertEquals(responseBody.get("account_name").asText(),"hello123");
        assertEquals(responseBody.get("street").asText(),"The Mall");
        assertEquals(responseBody.get("legal_entity_type").asText(),"individual");
        assertEquals(responseBody.get("city").asText(),"London");
        assertEquals(responseBody.get("country").asText(),"GB");
        assertEquals(responseBody.get("postal_code").asText(),"SW1A 1AA");
        assertEquals(responseBody.get("state_or_province").asText(),"string");
        assertEquals(responseBody.get("brand").asText(),"currencycloud");
        assertEquals(responseBody.get("your_reference").asText(),"MyntFinance");
        assertEquals(responseBody.get("status").asText(),"enabled");
        assertEquals(responseBody.get("spread_table").asText(),"");
        assertEquals(responseBody.get("identification_type").asText(),"none");
        assertEquals(responseBody.get("identification_value").asText(),"");
        assertEquals(responseBody.get("api_trading").asText(),"true");
        assertEquals(responseBody.get("online_trading").asText(),"true");
        assertEquals(responseBody.get("phone_trading").asText(),"true");
        assertEquals(responseBody.get("terms_and_conditions_accepted").asText(),"null");

    }

    @Test
    public void testCreateAccountPartialDTOFillOut() throws JsonProcessingException {

        CreateAccountRequest requestBody = CreateAccountRequest.builder()
                .accountName("hello123")
                .legalEntityType("individual")
                .street("The Mall")
                .city("London")
                .country("GB")
                .build();

        ResponseEntity<JsonNode> result = accountService.createAccount(requestBody).block();

        assert result != null;
        assertEquals(result.getStatusCode().value(),200);

        assertThat(Objects.requireNonNull(result.getBody()).get("id").asText()).isNotNull();
        assertEquals(result.getBody().get("account_name").asText(),"hello123");
        assertEquals(result.getBody().get("street").asText(),"The Mall");
        assertEquals(result.getBody().get("legal_entity_type").asText(),"individual");
        assertEquals(result.getBody().get("city").asText(),"London");
        assertEquals(result.getBody().get("country").asText(),"GB");
    }

}