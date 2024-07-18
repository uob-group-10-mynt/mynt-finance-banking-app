package services.cloudCurrencyTests;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.currency_cloud.manage.accounts.AccountService;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.accounts.requests.FindAccountRequest;
import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requestsDtos.CreateContact;
import com.mynt.banking.main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = main.class)
public class ContactCCTest {

    private String accountID;

    @Autowired
    private ContactsService contactsService;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        CreateAccountRequest requestBody = CreateAccountRequest.builder()
                .accountName("hello123")
                .legalEntityType("individual")
                .street("The Mall")
                .city("London")
                .country("GB")
                .build();

        ResponseEntity<JsonNode> result = accountService.createAccount(requestBody).block();

        assert result != null;
        this.accountID = Objects.requireNonNull(result.getBody()).get("id").asText();
    }

    @Test
    public void testCreateContact() {

        FindAccountRequest requestBody = FindAccountRequest.builder().build();
        ResponseEntity<JsonNode> accountNumber = accountService.findAccount(requestBody).block();
        accountNumber.getBody().get("pagination").get("total_entries").asText();
        int i = Integer.valueOf(accountNumber.getBody().get("pagination").get("total_entries").asText());
        i++;

        CreateContact contact = CreateContact.builder()
                .accountId(this.accountID)
                .firstName("James")
                .lastName("Love")
                .emailAddress("james.love"+i+"@gmail.com")
                .phoneNumber("+44 7824792"+i)
                .dateOfBirth("1997-08-13")
                .build();

        ResponseEntity<JsonNode> result = this.contactsService.createContact(contact).block();

        assert result != null;
        assertEquals(result.getStatusCode().value(), 200);

        assertEquals(result.getBody().get("account_id").asText(), this.accountID);
        assertEquals(result.getBody().get("first_name").asText(), contact.getFirstName());
        assertEquals(result.getBody().get("last_name").asText(), contact.getLastName());
        assertEquals(result.getBody().get("email_address").asText(), contact.getEmailAddress());
        assertEquals(result.getBody().get("phone_number").asText(), contact.getPhoneNumber());
        assertEquals(result.getBody().get("date_of_birth").asText(), contact.getDateOfBirth().toString());

    }

}
