package com.mynt.mynt.controller;

import com.sun.tools.javac.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.contentType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.http.MediaType;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {

//        mockMvc.perform(get())


    }

    @AfterEach
    public void tearDown() {}

    //TODO: controller intergraion tests - sudo code below
//    @Test
//    @WithMockUser(username = "testuser", password = "testuser", roles = "USER")
//    public void testRegisterUser() throws Exception {
//
//        mockMvc.perform(post("http://localhost:8080/api/v1/currency-cloud/balances/find/")
//                .with(SecurityMockMvcRequestPostProcessors.csrf()) // Add CSRF token
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{}")
//        ).andExpect(status().isOk());
//
//    }


//    @Test
//    @WithMockUser(username = "testuser", password = "testuser", roles = "USER")
//    public void testRegisterUser() throws Exception {
//        mockMvc.perform(post("/api/v1/auth/onfidoSdk"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//    }


}
