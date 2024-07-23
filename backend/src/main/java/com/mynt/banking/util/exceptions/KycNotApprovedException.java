package com.mynt.banking.util.exceptions;


public class KycNotApprovedException extends RuntimeException {
    public KycNotApprovedException(String message) {
        super(message);
    }
}