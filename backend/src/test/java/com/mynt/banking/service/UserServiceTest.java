//package com.mynt.banking.service;
//
//import com.mynt.banking.dto.request.UserRequestDTO;
//import com.mynt.banking.entity.User;
//import com.mynt.banking.repository.UserRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//        // Clear existing data or initialize as needed before each test
//        userRepository.deleteAll();
//    }
//
//    @AfterEach
//    void tearDown() {
//        // Perform cleanup after each test if needed
//    }
//
//    @Test
//    void testSaveUser() {
//        // Create a new user using builder pattern
//        User user = User.builder()
//                .forename("John")
//                .surname("Doe")
//                .email("john.doe@example.com")
//                .password("password123")
//                .build();
//
//
//        // Save the user using UserService
//        User savedUser = userService.createUser(UserRequestDTO(user));
//
//        // Verify that the user was saved successfully
//        assertNotNull(savedUser.getId());
//        assertEquals("John", savedUser.getFirstName());
//        assertEquals("Doe", savedUser.getLastName());
//        assertEquals("john.doe@example.com", savedUser.getEmail());
//    }
//
//    @Test
//    void testGetAllUsers() {
//        // Create some users using builder pattern
//        User user1 = User.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("john.doe@example.com")
//                .password("password123")
//                .build();
//
//        User user2 = User.builder()
//                .firstName("Jane")
//                .lastName("Smith")
//                .email("jane.smith@example.com")
//                .password("password456")
//                .build();
//
//        // Save them using UserService
//        userService.saveUser(user1);
//        userService.saveUser(user2);
//
//        // Retrieve all users using UserService
//        List<User> users = userService.getAllUsers();
//
//        // Verify that all users were retrieved
//        assertEquals(2, users.size());
//    }
//
//    @Test
//    void testGetUserById() {
//        // Create a user using builder pattern
//        User user = User.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("john.doe@example.com")
//                .password("password123")
//                .build();
//
//        userService.saveUser(user);
//
//        // Retrieve the user by id using UserService
//        Optional<User> optionalUser = userService.getUserById(user.getId());
//
//        // Verify that the user was retrieved correctly
//        assertTrue(optionalUser.isPresent());
//        assertEquals(user.getId(), optionalUser.get().getId());
//        assertEquals("John", optionalUser.get().getFirstName());
//        assertEquals("Doe", optionalUser.get().getLastName());
//    }
//
//    @Test
//    void testDeleteUser() {
//        // Create a user using builder pattern
//        User user = User.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .email("john.doe@example.com")
//                .password("password123")
//                .build();
//
//        userService.saveUser(user);
//
//        // Delete the user using UserService
//        userService.deleteUser(user.getId());
//
//        // Verify that the user was deleted
//        Optional<User> optionalUser = userService.getUserById(user.getId());
//        assertFalse(optionalUser.isPresent());
//    }
//}
