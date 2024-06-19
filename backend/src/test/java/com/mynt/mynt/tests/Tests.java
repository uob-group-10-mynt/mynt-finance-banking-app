package com.mynt.mynt.tests;

import com.mynt.mynt.service.JWT;
import com.nimbusds.jose.JOSEException;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class Tests {

    @Test
    void testJasonWebTokes() throws InterruptedException, JOSEException, ParseException {

        JWT jwt = new JWT();
        jwt.generateSecretKey();

        // test for if token is valid within alocated time
        String token = jwt.createJWT("james",30000);
        boolean validToken = jwt.authenticateJWT(token);
        assertTrue(validToken);

        // test for if token is not valid within alocated time
        token = jwt.createJWT("james",1000);
        Thread.sleep(1500);
        validToken = jwt.authenticateJWT(token);
        assertFalse(validToken);

    }


}
