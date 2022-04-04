package com.dropp.app.controller.advice;

import com.dropp.app.exception.ResourceNotFoundException;
import com.dropp.app.exception.UserAuthorizationException;
import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.Error;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class GenericControllerAdviceTest {

    @InjectMocks
    private GenericControllerAdvice advice;

    @Test
    public void testHandleUserNotFoundException() {
        Error error = advice.handleUserNotFoundException(new UserNotFoundException("Not Found"));
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getErrorCode());
        assertEquals("Not Found", error.getMessage());
        assertNull(error.getDescription());
    }

    @Test
    public void testHandleUserAuthorizationException() {
        Error error = advice.handleUserAuthorizationException(new UserAuthorizationException("Not Authorized"));
        assertNotNull(error);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), error.getErrorCode());
        assertEquals("Not Authorized", error.getMessage());
        assertNull(error.getDescription());
    }

    @Test
    public void testHandleResourceNotFoundException() {
        Error error = advice.handleResourceNotFoundException(new ResourceNotFoundException("Not Found"));
        assertNotNull(error);
        assertEquals(HttpStatus.NOT_FOUND.value(), error.getErrorCode());
        assertEquals("Not Found", error.getMessage());
        assertNull(error.getDescription());
    }

    @Test
    public void testHandleFirebaseAuthException() {
        Error error = advice.handleFirebaseAuthException(mock(FirebaseAuthException.class));
        assertNotNull(error);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), error.getErrorCode());
        assertNull(error.getDescription());
    }

}
