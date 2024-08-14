package com.mynt.banking.currency_cloud.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.accounts.CreateAccountRequest;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.manage.accounts.UpdateAccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

@SpringBootTest(classes = Main.class)
public class AccountServiceTest {

    @Autowired
    private com.mynt.banking.currency_cloud.manage.accounts.AccountService accountService;

    @Test
    public void testCreateAccount() {

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
                .apiTrading(null)
                .onlineTrading(null)
                .phoneTrading(null)
                .termsAndConditionsAccepted(null)
                .build();

        ResponseEntity<JsonNode> result = accountService.create(requestBody);

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
        assertEquals(responseBody.get("spread_table").asText(),"flat_0.00");
        assertEquals(responseBody.get("identification_type").asText(),"none");
        assertEquals(responseBody.get("identification_value").asText(),"null");
        assertEquals(responseBody.get("api_trading").asText(),"true");
        assertEquals(responseBody.get("online_trading").asText(),"true");
        assertEquals(responseBody.get("phone_trading").asText(),"true");
        assertEquals(responseBody.get("terms_and_conditions_accepted").asText(),"null");

    }

    @Test
    public void testCreateAccountPartialDTOFillOut() {

        CreateAccountRequest requestBody = CreateAccountRequest.builder()
                .accountName("hello123")
                .legalEntityType("individual")
                .street("The Mall")
                .city("London")
                .country("GB")
                .build();

        ResponseEntity<JsonNode> result = accountService.create(requestBody);

        assert result != null;
        assertEquals(result.getStatusCode().value(),200);

        assertThat(Objects.requireNonNull(result.getBody()).get("id").asText()).isNotNull();
        assertEquals(result.getBody().get("account_name").asText(),"hello123");
        assertEquals(result.getBody().get("street").asText(),"The Mall");
        assertEquals(result.getBody().get("legal_entity_type").asText(),"individual");
        assertEquals(result.getBody().get("city").asText(),"London");
        assertEquals(result.getBody().get("country").asText(),"GB");
    }

    @Test
    public void testUpdateAccount() {

        UpdateAccountRequest requestBody = UpdateAccountRequest.builder()
                .accountName("Kelvin")
                .legalEntityType("individual")
                //.brand("Kelvin") this cannot be set, or will have permission error
                .yourReference("Kelvin")
                .status("enabled")
                .street("Mall")
                .city("London")
                .stateOrProvince("London")
                .postalCode("BS1 111")
                .country("gb")
                //.spreadTable("I don't think we need that")
                .apiTrading("True")
                .onlineTrading("True")
                .phoneTrading("True")
                .identificationType("none")
                //.identificationValue("Don't have that now")
                //.termsAndConditionsAccepted("True") No effect?
                .build();

        String id = "44f353c2-d97f-4bf6-86d1-4b668c9dbcef";
        ResponseEntity<JsonNode> result = accountService.update(requestBody, id);

        assert result != null;
        assertEquals(result.getStatusCode().value(),200);
        assertEquals(result.getBody().get("id").asText(),"44f353c2-d97f-4bf6-86d1-4b668c9dbcef");
        assertEquals(result.getBody().get("account_name").asText(),"Kelvin");
        assertEquals(result.getBody().get("brand").asText(),"currencycloud");
        assertEquals(result.getBody().get("your_reference").asText(),"Kelvin");
        assertEquals(result.getBody().get("status").asText(),"enabled");
        assertEquals(result.getBody().get("street").asText(),"Mall");
        assertEquals(result.getBody().get("city").asText(),"London");
        assertEquals(result.getBody().get("state_or_province").asText(),"London");
        assertEquals(result.getBody().get("country").asText(),"GB");
        assertEquals(result.getBody().get("postal_code").asText(),"BS1 111");
        assertEquals(result.getBody().get("spread_table").asText(),"flat_0.00");
        assertEquals(result.getBody().get("legal_entity_type").asText(),"individual");
        assertThat(result.getBody().get("created_at").asText()).isNotNull();
        assertThat(result.getBody().get("updated_at").asText()).isNotNull();
        assertEquals(result.getBody().get("identification_type").asText(),"none");
        assertEquals(result.getBody().get("identification_value").asText(),"null");
        assertThat(result.getBody().get("short_reference").asText()).isNotNull();
        assertTrue(result.getBody().get("api_trading").asBoolean());
        assertTrue(result.getBody().get("online_trading").asBoolean());
        assertTrue(result.getBody().get("phone_trading").asBoolean());
        assertFalse(result.getBody().get("process_third_party_funds").asBoolean());
        assertEquals(result.getBody().get("settlement_type").asText(),"net");
        assertFalse(result.getBody().get("agent_or_reliance").asBoolean());
        assertEquals(result.getBody().get("terms_and_conditions_accepted").asText(),"null");
        assertEquals(result.getBody().get("bank_account_verified").asText(),"not required");
    }
}