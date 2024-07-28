package com.mynt.banking.util.exceptions.authentication;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}