//package com.mynt.banking.user;
//
//import com.mynt.banking.auth.KycEntity;
//import com.mynt.banking.auth.KycRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//@Testcontainers
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserRepositoryTest {
//
//    @Container
//    @ServiceConnection
//    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    KycRepository kycRepository; // You should create a repository for KycEntity if you haven't already
//
//    @BeforeEach
//    void setUp() {
//        User user = new User(
//                null,
//                "John",
//                "Doe",
//                "123 Elm Street",
//                "555-1234",
//                "01-01-1990",
//                "password123",
//                "john.doe@example.com",
//                Role.USER
//        );
//        userRepository.save(user);
//
//        KycEntity kyc = new KycEntity(
//                null,
//                "applicationId123",
//                "workflowRunId123",
//                "Approved",
//                user
//        );
//        kycRepository.save(kyc);
//    }
//
//    @Test
//    void connectionEstablished() {
//        assertThat(postgres.isCreated()).isTrue();
//        assertThat(postgres.isRunning()).isTrue();
//    }
//
//    @Test
//    void shouldReturnUserByEmail() {
//        User user = userRepository.findByEmail("john.doe@example.com").orElseThrow();
//        assertEquals("john.doe@example.com", user.getEmail(), "User email should be 'john.doe@example.com'");
//    }
//
//    @Test
//    void shouldNotReturnUserWhenEmailIsNotFound() {
//        Optional<User> user = userRepository.findByEmail("wrong.email@example.com");
//        assertFalse(user.isPresent(), "User should not be present");
//    }
//
//    @Test
//    void shouldReturnKycStatus() {
//        Optional<String> kycStatus = userRepository.getKycStatus("john.doe@example.com");
//        assertThat(kycStatus).isNotEmpty();
//        assertEquals("Approved", kycStatus.orElse(null)); // Replace with the actual expected status
//    }
//
//    @Test
//    void shouldReturnEmptyKycStatusWhenNotFound() {
//        Optional<String> kycStatus = userRepository.getKycStatus("wrong.email@example.com");
//        assertFalse(kycStatus.isPresent(), "KYC status should not be present for non-existent email");
//    }
//}
