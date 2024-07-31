package com.mynt.banking.user;

import com.mynt.banking.user.requests.ChangePasswordRequest;
import com.mynt.banking.user.requests.UpdateUserDetailsRequest;
import com.mynt.banking.user.responses.GetUserDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        User user = User.builder()
                .firstname("John")
                .lastname("Doe")
                .address("123 Main St")
                .phone_number("123-456-7890")
                .dob("1990-01-01")
                .password("password")
                .email("john.doe@example.com")
                .role(Role.USER)
                .build();
        userRepository.save(user);
    }

    @Test
    @Transactional
    void testChangePassword() {
        ChangePasswordRequest request = new ChangePasswordRequest("password", "newPassword", "newPassword");

        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/users/changePassword",
                HttpMethod.PATCH, new HttpEntity<>(request), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Transactional
    void testGetUserDetailsSuccess() {
        GetUserDetailsResponse expectedResponse = GetUserDetailsResponse.builder()
                .firstname("John")
                .lastname("Doe")
                .dob("1990-01-01")
                .phoneNumber("123-456-7890")
                .address("123 Main St")
                .build();

        ResponseEntity<GetUserDetailsResponse> response = restTemplate.exchange("/api/v1/users/getUserDetails",
                HttpMethod.GET, null, GetUserDetailsResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getFirstname()).isEqualTo(expectedResponse.getFirstname());
    }

    @Test
    @Transactional
    void testUpdateUserDetails() {
        UpdateUserDetailsRequest request = new UpdateUserDetailsRequest();
        request.setFirstname("John Updated");
        request.setLastname("Doe Updated");
        request.setDob(LocalDate.of(1990, 1, 1));
        request.setAddress("456 Elm St");
        request.setPhoneNumber("098-765-4321");

        ResponseEntity<Void> response = restTemplate.exchange("/api/v1/users/updateUserDetails",
                HttpMethod.POST, new HttpEntity<>(request), Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        User user = userRepository.findByEmail("john.doe@example.com").orElseThrow();
        assertThat(user.getFirstname()).isEqualTo("John Updated");
        assertThat(user.getLastname()).isEqualTo("Doe Updated");
        assertThat(user.getAddress()).isEqualTo("456 Elm St");
        assertThat(user.getPhone_number()).isEqualTo("098-765-4321");
    }
}
