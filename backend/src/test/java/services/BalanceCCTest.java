package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalanceAllCurrencies;
import com.mynt.banking.currency_cloud.manage.balances.BalanceService;
import com.mynt.banking.currency_cloud.manage.balances.requests.FindBalancesRequest;
import com.mynt.banking.main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = main.class)
public class BalanceCCTest {

    @Autowired
    private BalanceService balanceService;

//    @Test
//    public void testBalanceCC() {
//        FindBalanceAllCurrencies data = FindBalanceAllCurrencies.builder().
//                amountTo("")
//                .onBehalfOf("")
//                .amountFrom("")
//                .amountTo("")
//                .asAtDate("")
//                .createdAt("")
//                .page("")
//                .perPage("")
//                .order("")
//                .build();
//
//        ResponseEntity<JsonNode> response = balanceService.find(data).block();
//
//        assertNotNull(response);
//        assertEquals(response.getStatusCode().value(),200);
//
//        assertEquals(response.getBody().get("balances").get(0).get("currency").asText(),"USD");
//        assertEquals(response.getBody().get("pagination").get("order").asText(),"created_at");
//
//    }
//
//    @Test
//    public void testFindBalancesForParticularCurrency() throws JsonProcessingException {
//        FindBalancesRequest data = FindBalancesRequest.builder()
//                .onBehalfOf("")
//                .build();
//
//        ResponseEntity<JsonNode> response = balanceService.findForParticularCurrency(data,"GBP").block();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String output = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getBody());
//
//        assertNotNull(response);
//        assertEquals(response.getStatusCode().value(),200);
//
//        assertEquals(response.getBody().get("currency").asText(),"GBP");
//
//    }
}
