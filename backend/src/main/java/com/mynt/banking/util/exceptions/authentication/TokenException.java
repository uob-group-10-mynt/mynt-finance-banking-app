package com.mynt.banking.util.exceptions.authentication;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public static class TokenGenerationException extends TokenException {
        public TokenGenerationException(String message, Throwable cause) {
            super(message, cause);
        }

        public TokenGenerationException(String message) {
            super(message);
        }
    }

    public static class TokenValidationException extends TokenException {
        public TokenValidationException(String message, Throwable cause) {
            super(message, cause);
        }

        public TokenValidationException(String message) {
            super(message);
        }
    }

    public static class TokenRefreshException extends TokenException {
        public TokenRefreshException(String message, Throwable cause) {
            super(message, cause);
        }

        public TokenRefreshException(String message) {
            super(message);
        }
    }
}
