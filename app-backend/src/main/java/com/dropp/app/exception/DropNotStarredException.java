package com.dropp.app.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DropNotStarredException extends RuntimeException {

    public DropNotStarredException(String message) {
        super(message);
    }
}
