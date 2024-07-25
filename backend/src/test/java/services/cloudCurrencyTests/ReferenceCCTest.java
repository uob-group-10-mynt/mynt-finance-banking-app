package services.cloudCurrencyTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.reference.ReferenceService;
import com.mynt.banking.currency_cloud.manage.reference.requests.GetBeneficiaryRequirementsRequest;
import com.mynt.banking.main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


@SpringBootTest(classes = main.class)
public class ReferenceCCTest {

    @Autowired
    private ReferenceService referenceService;

    @Test
    public void testGetBeneficiaryRequirements(){
        GetBeneficiaryRequirementsRequest getBeneficiaryRequirementsRequest = GetBeneficiaryRequirementsRequest.builder()
                .currency("cny")
                .bankAccountCountry("gb")
                .beneficiaryCountry("gb")
                .build();

        ResponseEntity<JsonNode> responseEntity = referenceService
                .getBeneficiaryRequirements(getBeneficiaryRequirementsRequest)
                .block();

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
