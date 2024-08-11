package com.mynt.banking.currency_cloud.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.Main;
import com.mynt.banking.currency_cloud.manage.accounts.AccountService;
import com.mynt.banking.currency_cloud.manage.accounts.CreateAccountRequest;
import com.mynt.banking.currency_cloud.manage.accounts.FindAccountRequest;
import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.currency_cloud.manage.contacts.CreateContactRequest;
import com.mynt.banking.currency_cloud.manage.contacts.UpdateContactRequest;
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

        ResponseEntity<JsonNode> result = accountService.create(requestBody);

        assert result != null;
        this.accountID = Objects.requireNonNull(result.getBody()).get("id").asText();
    }

    @Test
    public void testCreateContact() {

        FindAccountRequest requestBody = FindAccountRequest.builder().build();
        ResponseEntity<JsonNode> accountNumber = accountService.find(requestBody);
        accountNumber.getBody().get("pagination").get("total_entries").asText();
        int i = Integer.valueOf(accountNumber.getBody().get("pagination").get("total_entries").asText());
        i++;

        CreateContactRequest contact = CreateContactRequest.builder()
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
        assertEquals(result.getBody().get("date_of_birth").asText(), contact.getDateOfBirth());

    }

    @Test
    public void testUpdateContact() {
        String ContactUUID = "ec883781-41b5-476c-aa05-568cc2023756";
        UpdateContactRequest contact = UpdateContactRequest.builder()
                .firstname("Kelvin")
                .lastname("Luuu")
                //.emailAddress("james.love@gmail.com")
                // A request to update a contact's email address does not immediately update the value. Instead, an email change request flow is initiated

                .phoneNumber("+44 7824792135")
                .dateOfBirth("1998-08-13")
                .build();

        ResponseEntity<JsonNode> result = contactsService.updateContact(ContactUUID, contact);

        assert result != null;
        assertEquals(result.getStatusCode().is2xxSuccessful(), true);

        assertEquals(result.getBody().get("first_name").asText(), contact.getFirstname());
        assertEquals(result.getBody().get("last_name").asText(), contact.getLastname());
        //assertEquals(result.getBody().get("email_address").asText(), contact.getEmailAddress());
        assertEquals(result.getBody().get("phone_number").asText(), contact.getPhoneNumber());
        assertEquals(result.getBody().get("date_of_birth").asText(), contact.getDateOfBirth());

        ResponseEntity<JsonNode> getContactResult = this.contactsService.getContact(ContactUUID);

        assert getContactResult != null;
        assertTrue(getContactResult.getStatusCode().is2xxSuccessful());

        assertEquals(getContactResult.getBody().get("first_name").asText(), contact.getFirstname());
        assertEquals(getContactResult.getBody().get("last_name").asText(), contact.getLastname());
        //assertEquals(getContactResult.getBody().get("email_address").asText(), contact.getEmailAddress());
        assertEquals(getContactResult.getBody().get("phone_number").asText(), contact.getPhoneNumber());
        assertEquals(getContactResult.getBody().get("date_of_birth").asText(), contact.getDateOfBirth());
    }

    @Test
    public void testGetContact() {
        ResponseEntity<JsonNode> result = this.contactsService.getContact("ec883781-41b5-476c-aa05-568cc2023756");

        assert result != null;
        assertTrue(result.getStatusCode().is2xxSuccessful());
        System.out.println(result.getBody());
    }

}
