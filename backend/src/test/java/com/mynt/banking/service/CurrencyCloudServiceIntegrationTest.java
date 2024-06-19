package com.mynt.banking.service;

import com.mynt.banking.entity.CurrencyCloud;
import com.mynt.banking.entity.User;
import com.mynt.banking.repository.CurrencyCloudRepository;
import com.mynt.banking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class CurrencyCloudServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyCloudRepository currencyCloudRepository;

    @Autowired
    private CurrencyCloudService currencyCloudService;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Create and save a test user
        testUser = User.builder()
                .forename("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .build();

        userRepository.save(testUser);
    }

    @Test
    @Transactional
    void testCreateCurrencyCloud() {
        // Create a new CurrencyCloud entry for the test user
        CurrencyCloud currencyCloud = CurrencyCloud.builder()
                .user(testUser)
                .UUID("test-uuid")
                .build();

        CurrencyCloud savedCurrencyCloud = currencyCloudService.createCurrencyCloud(currencyCloud);

        // Assertions
        assertNotNull(savedCurrencyCloud);
        assertEquals("test-uuid", savedCurrencyCloud.getUUID());
        assertNotNull(savedCurrencyCloud.getUser());
        assertEquals(testUser.getId(), savedCurrencyCloud.getUser().getId());

        // Verify the data in the actual database
        CurrencyCloud fetchedCurrencyCloud = currencyCloudRepository.findById(testUser.getId()).orElse(null);
        assertNotNull(fetchedCurrencyCloud);
        assertEquals("test-uuid", fetchedCurrencyCloud.getUUID());
    }
}
