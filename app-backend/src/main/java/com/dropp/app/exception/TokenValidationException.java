package com.dropp.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenValidationException extends RuntimeException {

    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
