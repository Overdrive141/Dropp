package com.dropp.app.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserAuthorizationException extends RuntimeException {

    public UserAuthorizationException(String message) {
        super(message);
    }
}
