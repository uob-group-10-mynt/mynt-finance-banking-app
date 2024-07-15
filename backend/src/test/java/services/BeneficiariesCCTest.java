package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.CreateBeneficiaryRequest;
import com.mynt.banking.currency_cloud.pay.beneficiaries.requests.FindBeneficiaryRequest;
import com.mynt.banking.currency_cloud.manage.accounts.AccountService;
import com.mynt.banking.currency_cloud.pay.beneficiaries.BeneficiaryService;
import com.mynt.banking.main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = main.class)
public class BeneficiariesCCTest {

    @Autowired
    private BeneficiaryService beneficiaryService;

    @Autowired
    private AccountService accountService;

    // NOTE: It will only work on my account as it relies on a beneficiary already in existence

//    @Test
//    public void testCreateBeneficiary(){
//
//        // Create Beneficiary:
//        CreateBeneficiaryRequest createBeneficiaryRequest = CreateBeneficiaryRequest.builder()
//                .name("KenyanUser02")
//                .bankAccountHolderName("Kenyan User02")
//                .bankCountry("GB")
//                .currency("KES")
//                .bicSwift("TCCLGB3L")
//                .iban("GB41TCCL12345673185203")
//                .beneficiaryFirstName("Kenyan")
//                .beneficiaryLastName("User")
//                .beneficiaryEntityType("individual")
//                .beneficiaryAddress("Test Address")
//                .beneficiaryCity("Test City")
//                .beneficiaryCountry("KE")
//                .onBehalfOf("fc56ddcc-d3ef-42ed-867f-5759e2a96afe")
//                .build();
//
//        ResponseEntity<JsonNode> createBeneficiaryRequestResult = this.beneficiaryService.create(createBeneficiaryRequest).block();
//
//        assert createBeneficiaryRequestResult != null;
//        int responseCode = createBeneficiaryRequestResult.getStatusCode().value();
//        assertEquals(responseCode, 200);
//
//        JsonNode responseBody = createBeneficiaryRequestResult.getBody();
//        assert responseBody != null;
//        assertEquals(responseBody.get("name").asText(), "KenyanUser02");
//        assertEquals(responseBody.get("bank_account_holder_name").asText(),"Kenyan User02");
//
//
//        // Find Beneficiary - on behalf of Postman: fc56ddcc-d3ef-42ed-867f-5759e2a96afe
//        FindBeneficiaryRequest findBeneficiaryRequest = FindBeneficiaryRequest.builder()
//                .onBehalfOf("fc56ddcc-d3ef-42ed-867f-5759e2a96afe")
//                .build();
//
//        ResponseEntity<JsonNode> findBeneficiaryRequestResult = this.beneficiaryService.find(findBeneficiaryRequest).block();
//
//        assert findBeneficiaryRequestResult != null;
//        int findBeneficiaryRequestResponseCode = createBeneficiaryRequestResult.getStatusCode().value();
//        assertEquals(findBeneficiaryRequestResponseCode, 200);
//
//        JsonNode findBeneficiaryRequestResponseBody = findBeneficiaryRequestResult.getBody();
//        assert findBeneficiaryRequestResponseBody != null;
//        assertEquals(findBeneficiaryRequestResponseBody.get("beneficiaries").get(0).get("name").asText(), "KenyanUser02");
//        assertEquals(findBeneficiaryRequestResponseBody.get("beneficiaries").get(0).get("bank_account_holder_name").asText(),"Kenyan User02");
//
//        // TODO : Delete newly created beneficiary - I am doing throught the dev portal atm:
//        String beneficiaryID = findBeneficiaryRequestResponseBody.get("beneficiaries").get(0).get("id").asText();
//
//    }
}
