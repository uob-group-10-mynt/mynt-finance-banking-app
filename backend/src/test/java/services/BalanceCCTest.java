package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.balances.FindBalanceAllCurrenies;
import com.mynt.banking.currency_cloud.dto.balances.FindBalances;
import com.mynt.banking.currency_cloud.service.BalanceService;
import com.mynt.banking.main;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = main.class)
public class BalanceCCTest {

    @Autowired
    private BalanceService balanceService;

    @Test
    public void testBalanceCC() throws JsonProcessingException {
        FindBalanceAllCurrenies data = FindBalanceAllCurrenies.builder().
                amountTo("")
                .onBehalfOf("")
                .amountFrom("")
                .amountTo("")
                .asAtDate("")
                .createdAt("")
                .page("")
                .perPage("")
                .order("")
                .build();

        ResponseEntity<JsonNode> response = balanceService.find(data).block();

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(),200);

        assertEquals(response.getBody().get("balances").get(0).get("currency").asText(),"USD");
        assertEquals(response.getBody().get("pagination").get("order").asText(),"created_at");

    }

    @Test
    public void testFindBalancesForParticularCurrency() throws JsonProcessingException {
        FindBalances data = FindBalances.builder()
                .onBehalfOf("")
                .build();

        ResponseEntity<JsonNode> response = balanceService.findForParticularCurrency(data,"GBP").block();

        ObjectMapper mapper = new ObjectMapper();
        String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody());

        assertNotNull(response);
        assertEquals(response.getStatusCode().value(),200);

        assertEquals(response.getBody().get("currency").asText(),"GBP");

    }

}
