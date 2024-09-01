package com.mynt.banking.auth;

import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.responses.SDKResponse;
import com.mynt.banking.Main;
import com.mynt.banking.auth.kyc.KycService;
import com.mynt.banking.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest(classes = Main.class)
public class KycTests {

    @Autowired
    private UserRepository userRepository;

    private int testAccountNum;

    @Autowired
    private KycService kycService;

    @BeforeEach
    public void setUp() {
        this.testAccountNum = (int)userRepository.count();
        this.testAccountNum++;
    }

    @AfterEach
    void tearDown() {}

    @Test
    public void testGetOnfidoSDK() {

        SignUpRequest signUpRequest = SignUpRequest.builder()
                .email("test"+testAccountNum+"@test.com")
                .firstname("James")
                .lastname("Love")
                .dob(LocalDate.parse("16 08 2020", DateTimeFormatter.ofPattern("dd MM yyyy")))
                .address("Bristol")
                .phoneNumber("+44 78233444534")
                .password("password")
                .build();

        ResponseEntity<SDKResponse> response = this.kycService.getOnfidoSDK(signUpRequest);

        assert response != null;
        assertEquals(response.getStatusCode().value(), 200);
        assertEquals(response.getBody().getStage(),"SDKResponseDTO");
    }

//    @Test
//    public void testValidate() throws JsonProcessingException {
//
//        String email = "blahblah@Jameslove.com";
//        //need to update email  within DB before running test
//        ValidateKycRequest requestDtoValid = ValidateKycRequest.builder()
//                .Email(email)
//                .build();
//        SDKResponse responseValid = this.kycService.validateKyc(requestDtoValid);
//
//        assert responseValid != null;
//        assertEquals(responseValid.getStage(),"approved");
//    }
//
//    // test will only work with the basic KYC workflow ID found within the
//    // Application.properties file as tke KYC will be auto approved
//    @Test
//    public void testValidateKyc() throws JsonProcessingException {
//
//        SignUpRequest signUpRequest = SignUpRequest.builder()
//                .email("test"+testAccountNum+"@test.com")
//                .firstname("James")
//                .lastname("Love")
//                .dob(LocalDate.parse("16 08 2020", DateTimeFormatter.ofPattern("dd MM yyyy")))
//                .address("Bristol")
//                .phoneNumber("+44 78233444534")
//                .password("password")
//                .build();
//
//        ResponseEntity<SDKResponse> response = this.kycService.getOnfidoSDK(signUpRequest);
//
//        assert response != null;
//        assertEquals(response.getBody().getStage(),"SDKResponseDTO");
//
//
//        String email = "test"+testAccountNum+"@test.com";
//
//        //need to update email  within DB before running test
//        ValidateKycRequest requestDtoValid = ValidateKycRequest.builder()
//                .Email(email)
//                .build();
//        SDKResponse responseValid = this.kycService.validateKyc(requestDtoValid);
//
//        assert responseValid != null;
//        assertEquals(responseValid.getStage(),"approved");
//
//    }
}
