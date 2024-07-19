package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.reference.ReferenceService;
import com.mynt.banking.currency_cloud.manage.reference.requests.GetPayerRequirementsRequest;
import com.mynt.banking.main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = main.class)
public class ReferenceCCTest {

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

        ResponseEntity<JsonNode> result = referenceService.getPayerRequirements(requestBody).block();

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
}