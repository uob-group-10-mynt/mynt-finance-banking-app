package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.auth.KYCService;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.responses.SDKResponse;
import com.mynt.banking.currency_cloud.manage.accounts.AccountService;
import com.mynt.banking.currency_cloud.manage.accounts.requests.FindAccountRequest;
import com.mynt.banking.main;
import com.mynt.banking.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = main.class)
public class KycTests {

    @Autowired
    private UserRepository userRepository;

    private int testAccountNum;

    @Autowired
    private KYCService kycService;

    @BeforeEach
    void setUp() {

        this.testAccountNum = (int)userRepository.count();
        this.testAccountNum++;

    }

    @AfterEach
    void tearDown() {}

    @Test
    public void testKyc() {

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test"+testAccountNum+"@test.com")
                .firstname("James")
                .lastname("Love")
                .dob("1066-08-16")
                .address("Bristol")
                .phoneNumber("+44 78233444534")
                .password("password")
                .build();

        SDKResponse response = this.kycService.getOnfidoSDK(signUpRequest);

        assert response != null;
        assertEquals(response.getStage(),"SDKResponceDTO");

    }
}
