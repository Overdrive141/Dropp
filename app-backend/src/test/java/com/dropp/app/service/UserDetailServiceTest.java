package com.dropp.app.service;

import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.UserDetail;
import com.dropp.app.model.UserRequest;
import com.dropp.app.model.dto.UserDetailDTO;
import com.dropp.app.model.enums.Avatar;
import com.dropp.app.repository.UserDetailRepository;
import com.dropp.app.transformer.UserDetailTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.dropp.app.model.enums.Avatar.AV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    @Mock
    private UserDetailRepository repository;
    @Mock
    private UserDetailTransformer transformer;
    @InjectMocks
    private UserDetailService service;
    private UserDetail userDetail1, userDetail2;
    private UserDetailDTO userDetailDTO;
    private UserRequest userRequest;

    @BeforeEach
    public void setup() {
        userRequest = UserRequest.builder()
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .avatar(Avatar.AV1)
                .build();
        userDetail1 = UserDetail.builder()
                .id(1l)
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .favDrops(0l)
                .avatar(Avatar.AV1)
                .build();
        userDetail2 = UserDetail.builder()
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .favDrops(0l)
                .avatar(Avatar.AV1)
                .build();
        userDetailDTO = UserDetailDTO.builder()
                .id(1l)
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .favDrops(0l)
                .avatar(Avatar.AV1)
                .build();
    }

    @Test
    public void testGetUserByEmailOrUsername1() {
        when(repository.findByEmailOrUsername(anyString(), anyString())).thenReturn(Optional.of(userDetail1));
        when(transformer.map(eq(userDetail1))).thenReturn(userDetailDTO);
        UserDetailDTO userDetailDTO = service.getUserByEmailOrUsername("user1");
        assertEquals(1l, userDetailDTO.getId());
        assertEquals("user1", userDetailDTO.getUsername());
        assertEquals("user1@uwindsor.ca", userDetailDTO.getEmail());
        assertEquals("1234567890", userDetailDTO.getContactNo());
        assertEquals(0L, userDetailDTO.getFavDrops());
        assertEquals(AV1, userDetailDTO.getAvatar());
        verify(repository, times(1)).findByEmailOrUsername(anyString(), anyString());
        verify(transformer, times(1)).map(eq(userDetail1));
    }

    @Test
    public void testGetUserByEmailOrUsername2() {
        when(repository.findByEmailOrUsername(anyString(), anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> service.getUserByEmailOrUsername("user2"));
    }

    @Test
    public void testAddUser() {
        when(transformer.map(eq(userRequest))).thenReturn(userDetail2);
        when(repository.save(eq(userDetail2))).thenReturn(userDetail1);
        when(transformer.map(eq(userDetail1))).thenReturn(userDetailDTO);
        UserDetailDTO userDetailDTO = service.addUser(userRequest);
        assertEquals("user1", userDetailDTO.getUsername());
        assertEquals("user1@uwindsor.ca", userDetailDTO.getEmail());
        assertEquals("1234567890", userDetailDTO.getContactNo());
        assertEquals(0L, userDetailDTO.getFavDrops());
        assertEquals(AV1, userDetailDTO.getAvatar());
        verify(repository, times(1)).save(eq(userDetail2));
        verify(transformer, times(1)).map(eq(userDetail1));
    }
}
