package com.dropp.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserAuthorizationException extends RuntimeException {

    public UserAuthorizationException(String message) {
        super(message);
    }
}
