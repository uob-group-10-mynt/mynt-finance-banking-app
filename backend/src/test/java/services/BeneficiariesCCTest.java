package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mynt.banking.currency_cloud.dto.beneficiaries.FindRequestDto;
import com.mynt.banking.currency_cloud.service.AccountService;
import com.mynt.banking.currency_cloud.service.BeneficiariesService;
import com.mynt.banking.main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = main.class)
public class BeneficiariesCCTest {

    @Autowired
    private BeneficiariesService beneficiariesService;

    @Autowired
    private AccountService accountService;

    @Test
    public void testFindBenificiary(){

        FindRequestDto fDto = FindRequestDto.builder()
                .name("james")
                .bankAccountHolderName("")
                .bankCountry("")
                .currency("")
                .onBehalfOf("")
                .beneficiaryId("")
                .beneficiaryCountry("")
                .beneficiaryEntityType("")
                .accountNumber("")
                .routingCodeType("")
                .routingCodeValue("")
                .iban("")
                .bicSwift("")
                .defaultBeneficiary(null)
                .bankAddress("")
                .bankName("")
                .bankAccountType("")
                .beneficiaryCompanyName("")
                .beneficiaryFirstName("")
                .beneficiaryLastName("")
                .beneficiaryCity("")
                .beneficiaryPostcode("")
                .beneficiaryStateOrProvince("")
                .beneficiaryDateOfBirth("")
                .scope("")
                .page("")
                .perPage("")
                .order("")
                .orderAscDesc("")
                .beneficiaryExternalReference("")
                .build();

        ResponseEntity<JsonNode> result = this.beneficiariesService.find(fDto).block();

        assert result != null;
        int responseCode = result.getStatusCode().value();
        assertEquals(responseCode, 200);

        JsonNode responseBody = result.getBody();
        assert responseBody != null;
        assertEquals(responseBody.get("beneficiaries").get(0).get("name").asText(), "James");
        assertEquals(responseBody.get("beneficiaries").get(0).get("bank_account_holder_name").asText(),"James Love");
        assertEquals(responseBody.get("pagination").get("total_entries").asText(),"1");

    }

}
