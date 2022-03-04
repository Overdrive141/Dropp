package com.dropp.app.controller.advice;

import com.dropp.app.exception.ResourceNotFoundException;
import com.dropp.app.exception.UserAuthorizationException;
import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.Error;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GenericControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public Error handleUserNotFoundException(UserNotFoundException ex) {
        Error error = Error.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .description(null)
                .build();
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(ResourceNotFoundException.class)
    public Error handleResourceNotFoundException(ResourceNotFoundException ex) {
        Error error = Error.builder()
                .errorCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .description(null)
                .build();
        return error;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(UserAuthorizationException.class)
    public Error handleUserAuthorizationException(UserAuthorizationException ex) {
        Error error = Error.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .description(null)
                .build();
        return error;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    @ExceptionHandler(FirebaseAuthException.class)
    public Error handleFirebaseAuthException(FirebaseAuthException ex) {
        Error error = Error.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .description(null)
                .build();
        return error;
    }
}
