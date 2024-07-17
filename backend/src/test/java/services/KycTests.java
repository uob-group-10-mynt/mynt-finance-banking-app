package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.mynt.banking.auth.KYCService;
import com.mynt.banking.auth.requests.SignUpRequest;
import com.mynt.banking.auth.requests.ValidateKycRequest;
import com.mynt.banking.auth.responses.SDKResponse;
import com.mynt.banking.currency_cloud.CurrencyCloudEntity;
import com.mynt.banking.currency_cloud.CurrencyCloudRepository;
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

import java.io.IOException;
import java.net.URISyntaxException;

@SpringBootTest(classes = main.class)
public class KycTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyCloudRepository currencyCloudRepository;

    private int testAccountNum;

    @Autowired
    private KYCService kycService;

    @Autowired
    private

    @BeforeEach
    void setUp() {

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
                .dob("1066-08-16")
                .address("Bristol")
                .phoneNumber("+44 78233444534")
                .password("password")
                .build();

        SDKResponse response = this.kycService.getOnfidoSDK(signUpRequest);

        assert response != null;
        assertEquals(response.getStage(),"SDKResponceDTO");

    }



    ///This test requries that you find an email on the DB that is approved or the user has already gone
    ///  thorugh KYC checks other wise the test will fail

    @Test
    public void testValidateKyc() {

        // test what happends if user has not gone thoruhg KYC
        ValidateKycRequest requestDtoInvalid = ValidateKycRequest.builder()
                .Email("test34-693@test.com")
                .build();
        SDKResponse responseInvalid = this.kycService.validateKyc(requestDtoInvalid);

        assert responseInvalid != null;
        assertEquals(responseInvalid.getStage(),"error with email");
        assertEquals(responseInvalid.getData(),"error invalid email please check and try again");

        String email = "test34-abc12345678@test.com";
        int id = userRepository.findByEmail(email).get().getId();
        if (!userRepository.findByEmail(email).isEmpty()&& !currencyCloudRepository.findByUsersId((long)id).isEmpty()){
            CurrencyCloudEntity currencyCloudEntity = currencyCloudRepository.findByUsersId((long)id).get(0);
            currencyCloudRepository.delete(currencyCloudEntity);
        }

        //need to update email  within DB before running test
        ValidateKycRequest requestDtoValid = ValidateKycRequest.builder()
                .Email(email)
                .build();
        SDKResponse responseValid = this.kycService.validateKyc(requestDtoValid);

        assert responseValid != null;
        assertEquals(responseValid.getStage(),"approved");

        // test running the comand twice
        ValidateKycRequest requestDtoDuplicateRequest = ValidateKycRequest.builder()
                .Email(email)
                .build();
        SDKResponse responseDuplicateRequest = this.kycService.validateKyc(requestDtoDuplicateRequest);

        assert responseDuplicateRequest != null;
        assertEquals(responseDuplicateRequest.getStage(),"approved");
        assertEquals(responseDuplicateRequest.getData(),"user already has an account");


    }


}
