package services;

import com.mynt.banking.auth.KYCService;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.responses.SDKResponse;
import com.mynt.banking.main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = main.class)
public class KycTests {


    @Autowired
    private KYCService kycService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    public void testKyc() {
        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test123adfadf24dsfdfadf23dsfgsddasfsf@test.com")
                .firstname("James")
                .lastname("Love")
                .dob("1066-08-16")
                .address("Bristol")
                .phoneNumber("+44 78233444534")
                .password("password")
                .build();

        SDKResponse response = this.kycService.getOnfidoSDK(signUpRequest);

        assert response != null;


    }
}
