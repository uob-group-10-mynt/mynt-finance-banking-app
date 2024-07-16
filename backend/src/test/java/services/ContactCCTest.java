package services;

import com.mynt.banking.currency_cloud.manage.contacts.ContactsService;
import com.mynt.banking.main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = main.class)
public class ContactCCTest {

    @Autowired
    private ContactsService contactsService;

    @Test
    public void test() {}

}
