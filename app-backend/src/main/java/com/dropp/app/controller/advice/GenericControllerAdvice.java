package com.dropp.app.controller.advice;

import com.dropp.app.exception.ResourceNotFoundException;
import com.dropp.app.exception.UserAuthorizationException;
import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.Error;
import com.google.firebase.auth.FirebaseAuthException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GenericControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public Error handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        String message = StringUtils.join(violations, '|');
        return Error.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Error handleIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return Error.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Error handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.toList());
        return Error.builder()
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .message(StringUtils.join(errors, "|"))
                .build();
    }

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
