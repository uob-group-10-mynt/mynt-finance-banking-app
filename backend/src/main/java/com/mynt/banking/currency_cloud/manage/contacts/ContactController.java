package com.mynt.banking.currency_cloud.manage.contacts;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/currency-cloud/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactsService contactsService;

}
