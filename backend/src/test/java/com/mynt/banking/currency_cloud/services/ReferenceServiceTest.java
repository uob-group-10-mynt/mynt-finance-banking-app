package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.manage.reference.GetBeneficiaryRequirementsRequest;
import com.mynt.banking.currency_cloud.manage.reference.GetPayerRequirementsRequest;
import com.mynt.banking.currency_cloud.manage.reference.ReferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(classes = Main.class)
public class ReferenceServiceTest {

    @Autowired
    private ReferenceService referenceService;

    @Test
    public void testGetPayerRequirements() {

        GetPayerRequirementsRequest requestBody = GetPayerRequirementsRequest.builder()
                .payerCountry("gb")
                .payerEntityType("individual")
                .paymentType("regular")
                .currency("gbp")
                .build();

        ResponseEntity<JsonNode> result = referenceService.getPayerRequirements(requestBody);

        assert result != null;
        int responseCode = result.getStatusCode().value();
        assertEquals(responseCode, 200);

        JsonNode responseBody = result.getBody();
        assert responseBody != null;

        assertThat(responseBody.has("details"));
        JsonNode details = responseBody.get("details").get(0);

        assertEquals(details.get("currency").asText(),"GBP");
        assertEquals(details.get("payer_entity_type").asText(),"individual");
        assertEquals(details.get("payment_type").asText(),"regular");
        assertThat(details.get("required_fields")!=null);

    }

    @Test
    public void testGetBeneficiaryRequirements(){
        GetBeneficiaryRequirementsRequest getBeneficiaryRequirementsRequest = GetBeneficiaryRequirementsRequest.builder()
                .currency("cny")
                .bankAccountCountry("gb")
                .beneficiaryCountry("gb")
                .build();

        ResponseEntity<JsonNode> responseEntity = referenceService
                .getBeneficiaryRequirements(getBeneficiaryRequirementsRequest);

        assert responseEntity != null;
        assertEquals(responseEntity.getStatusCode().value(), 200);

        JsonNode responseBody = responseEntity.getBody();
        assertNotEquals(responseBody,  null);
        JsonNode details = responseBody.get("details");
        assertNotEquals(details,  null);
        JsonNode forIndividual = details.get(0);
        JsonNode forCompany = details.get(1);

        assertEquals(forIndividual.get("payment_type").asText(), "priority");
        assertEquals(forIndividual.get("beneficiary_entity_type").asText(), "individual");

        assertEquals(forCompany.get("payment_type").asText(), "priority");
        assertEquals(forCompany.get("beneficiary_entity_type").asText(), "company");
    }
}
