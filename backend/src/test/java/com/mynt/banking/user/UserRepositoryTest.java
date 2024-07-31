package com.mynt.banking.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.0");

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAllUsers() {
        User user1 = User.builder()
                .firstname("Alice")
                .lastname("Smith")
                .address("123 Main St")
                .phone_number("123-456-7890")
                .dob("1990-01-01")
                .password("password1")
                .email("findall1@example.com")
                .role(Role.USER)
                .build();

        userRepository.save(user1);

        User user2 = User.builder()
                .firstname("Bob")
                .lastname("Johnson")
                .address("456 Elm St")
                .phone_number("987-654-3210")
                .dob("1985-05-05")
                .password("password2")
                .email("findall2@example.com")
                .role(Role.ADMIN)
                .build();

        userRepository.save(user2);

        List<User> users = userRepository.findAll();

        assertThat(users).hasSize(2);
        assertThat(users).extracting(User::getEmail)
                .containsExactlyInAnyOrder("findall1@example.com", "findall2@example.com");
    }


    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
        System.out.println("PostgreSQL container is running on: " + postgres.getJdbcUrl());
    }
}
