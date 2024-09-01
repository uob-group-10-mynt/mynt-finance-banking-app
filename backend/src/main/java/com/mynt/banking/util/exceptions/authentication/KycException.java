package com.mynt.banking.util.exceptions.authentication;

public class KycException extends RuntimeException {
    public KycException(String message) {
        super(message);
    }

    public static class KycStatusNotFoundException extends KycException {
        public KycStatusNotFoundException(String message) {
            super(message);
        }
    }

    public static class KycNotApprovedException extends KycException {
        public KycNotApprovedException(String message) {
            super(message);
        }
    }
}
