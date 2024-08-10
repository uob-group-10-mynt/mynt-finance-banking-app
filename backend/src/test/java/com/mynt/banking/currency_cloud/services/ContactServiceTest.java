package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.manage.accounts.CurrencyCloudAccountsService;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CurrencyCloudCreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.accounts.requests.CurrencyCloudFindAccountsRequest;
import com.mynt.banking.currency_cloud.manage.contacts.CurrencyCloudContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.requests.CurrencyCloudCreateContactRequest;
import com.mynt.banking.currency_cloud.manage.contacts.requests.CurrencyCloudUpdateContactRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
public class ContactServiceTest {

    private String accountID;

    @Autowired
    private CurrencyCloudContactsService contactsService;

    @Autowired
    private CurrencyCloudAccountsService accountService;

    @BeforeEach
    void setUp() {
        CurrencyCloudCreateAccountRequest requestBody = CurrencyCloudCreateAccountRequest.builder()
                .accountName("hello123")
                .legalEntityType("individual")
                .street("The Mall")
                .city("London")
                .country("GB")
                .build();

        ResponseEntity<JsonNode> result = accountService.createAccount(requestBody);

        assert result != null;
        this.accountID = Objects.requireNonNull(result.getBody()).get("id").asText();
    }

    @Test
    public void testCreateContact() {

        CurrencyCloudFindAccountsRequest requestBody = CurrencyCloudFindAccountsRequest.builder().build();
        ResponseEntity<JsonNode> accountNumber = accountService.findAccounts(requestBody);
        accountNumber.getBody().get("pagination").get("total_entries").asText();
        int i = Integer.valueOf(accountNumber.getBody().get("pagination").get("total_entries").asText());
        i++;

        CurrencyCloudCreateContactRequest contact = CurrencyCloudCreateContactRequest.builder()
                .accountId(this.accountID)
                .firstName("James")
                .lastName("Love")
                .emailAddress("james.love"+i+"@gmail.com")
                .phoneNumber("+44 7824792"+i)
                .dateOfBirth("1997-08-13")
                .build();

        ResponseEntity<JsonNode> result = this.contactsService.createContact(contact);

        assert result != null;
        assertEquals(result.getStatusCode().value(), 200);

        assertEquals(result.getBody().get("account_id").asText(), this.accountID);
        assertEquals(result.getBody().get("first_name").asText(), contact.getFirstName());
        assertEquals(result.getBody().get("last_name").asText(), contact.getLastName());
        assertEquals(result.getBody().get("email_address").asText(), contact.getEmailAddress());
        assertEquals(result.getBody().get("phone_number").asText(), contact.getPhoneNumber());
        assertEquals(result.getBody().get("date_of_birth").asText(), contact.getDateOfBirth().toString());

    }

    @Test
    public void testUpdateContact() {
        String ContactUUID = "ec883781-41b5-476c-aa05-568cc2023756";
        CurrencyCloudUpdateContactRequest contact = CurrencyCloudUpdateContactRequest.builder()
                .firstName("Kelvin")
                .lastName("Luuu")
                //.emailAddress("james.love@gmail.com")
                // A request to update a contact's email address does not immediately update the value. Instead, an email change request flow is initiated

                .phoneNumber("44 7824792135")
                .build();

        ResponseEntity<JsonNode> result = contactsService.updateContact(ContactUUID, contact);

        assert result != null;
        assertEquals(result.getStatusCode().is2xxSuccessful(), true);

        assertEquals(result.getBody().get("first_name").asText(), contact.getFirstName());
        assertEquals(result.getBody().get("last_name").asText(), contact.getLastName());
        //assertEquals(result.getBody().get("email_address").asText(), contact.getEmailAddress());
        assertEquals(result.getBody().get("phone_number").asText(), contact.getPhoneNumber());

        ResponseEntity<JsonNode> getContactResult = this.contactsService
                .getContact(ContactUUID);


        assert getContactResult != null;
        assertEquals(getContactResult.getStatusCode().is2xxSuccessful(), true);

        assertEquals(getContactResult.getBody().get("first_name").asText(), contact.getFirstName());
        assertEquals(getContactResult.getBody().get("last_name").asText(), contact.getLastName());
        //assertEquals(getContactResult.getBody().get("email_address").asText(), contact.getEmailAddress());
        assertEquals(getContactResult.getBody().get("phone_number").asText(), contact.getPhoneNumber());
    }

    @Test
    public void testGetContact() {
        ResponseEntity<JsonNode> result = this.contactsService.getContact("ec883781-41b5-476c-aa05-568cc2023756");

        assert result != null;
        assertEquals(result.getStatusCode().is2xxSuccessful(), true);
        System.out.println(result.getBody());
    }

}
