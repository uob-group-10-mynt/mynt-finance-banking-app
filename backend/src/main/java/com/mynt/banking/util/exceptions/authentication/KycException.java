package com.mynt.banking.util.exceptions.authentication;

public class KycException extends RuntimeException {
    public KycException(String message) {
        super(message);
    }

    public KycException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class KycStatusNotFoundException extends KycException {
        public KycStatusNotFoundException(String message) {
            super(message);
        }

        public KycStatusNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class KycNotApprovedException extends KycException {
        public KycNotApprovedException(String message) {
            super(message);
        }

        public KycNotApprovedException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
