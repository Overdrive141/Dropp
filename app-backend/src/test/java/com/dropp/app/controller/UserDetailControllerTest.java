package com.dropp.app.controller;

import com.dropp.app.model.UserRequest;
import com.dropp.app.model.dto.UserDetailDTO;
import com.dropp.app.model.enums.Avatar;
import com.dropp.app.service.UserDetailService;
import com.dropp.app.validation.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailControllerTest {

    @Mock
    private ValidationService validationService;
    @Mock
    private UserDetailService userDetailService;
    @InjectMocks
    private UserDetailController controller;

    private String header = "header";
    private UserRequest userRequest;

    @BeforeEach
    public void setup() {
        userRequest = UserRequest.builder()
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .avatar(Avatar.AV1)
                .build();
    }

    @Test
    public void testGetUserByEmailIdOrUsername() {
        when(userDetailService.getUserByEmailOrUsername(anyString())).thenReturn(mock(UserDetailDTO.class));
        assertNotNull(controller.getUserByEmailIdOrUsername("user1", header));
    }

    @Test
    public void testAddUser() {
        when(userDetailService.addUser(any(UserRequest.class))).thenReturn(mock(UserDetailDTO.class));
        assertNotNull(controller.addUser(userRequest, header));
    }
}
