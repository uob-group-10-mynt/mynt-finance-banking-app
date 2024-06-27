package com.mynt.mynt.util;

import com.mynt.banking.jwt.JWT;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestJWT {

    private JWT jwt;

    @BeforeEach
    public void setUp() {
        jwt = new JWT();
    }

    @AfterEach
    public void tearDown() {
        jwt.setExpirationTimeMillis(30000);
    }

    @Test
    void testGenerateSecretKey() throws JOSEException {
        System.out.println("NewKye -> "+JWT.generateSecretKey());
    }

    @Test
    void testLoadSecretKey() throws ParseException, JOSEException {
        JWT key = new JWT();
        key.loadSecretKey();
    }

    @Test
    void testGeneratingJWT() throws InterruptedException, JOSEException, ParseException {
        String token = jwt.createJWT("james");
        boolean validToken = jwt.authenticateJWT(token);
        assertTrue(validToken);
    }


    @Test
    void testExpiredTime() throws InterruptedException, JOSEException, ParseException {
        // test for if token is valid within alocated time
        String token = jwt.createJWT("james");
        boolean validToken = jwt.authenticateJWT(token);
        assertTrue(validToken);
        jwt.setExpirationTimeMillis(1000);

        // test for if token is not valid within alocated time
        token = jwt.createJWT("james");
        Thread.sleep(1500);
        validToken = jwt.authenticateJWT(token);
        assertFalse(validToken);
    }


}
