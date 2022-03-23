//package com.dropp.app.service;
//
//import com.dropp.app.exception.UserAuthorizationException;
//import com.dropp.app.model.UserDetail;
//import com.dropp.app.model.UserRequest;
//import com.dropp.app.repository.UserDetailRepository;
//import com.dropp.app.transformer.UserDetailTransformer;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static com.dropp.app.model.enums.Avatar.AV1;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UserDetailServiceTest {
//
//    @Mock
//    private UserDetailRepository repository;
//    @Mock
//    private UserDetailTransformer transformer;
//    @InjectMocks
//    private UserDetailService service;
//    private UserDetail userDetail1, userDetail2;
//    private UserRequest userRequest;
//
//    @BeforeEach
//    public void setup() {
//        userDetail1 = UserDetail.builder()
//                .username("user1")
//                .contactNo("1234567890")
//                .email("user1@gmail.com")
//                .favDrops(10L)
//                .avatar(AV1)
//                .build();
//        userDetail2 = UserDetail.builder()
//                .username("user1")
//                .contactNo("1234567890")
//                .email("user1@gmail.com")
//                .avatar(AV1)
//                .build();
//        userRequest = UserRequest.builder()
//                .username("user1")
//                .contactNo("1234567890")
//                .email("user1@gmail.com")
//                .avatar(AV1)
//                .build();
//    }
//
//    @Test
//    public void testGetUserByEmailOrUsername1() {
//        when(repository.findByEmailOrUsername(anyString(), anyString())).thenReturn(Optional.of(userDetail1));
//        UserDetail userDetail = service.getUserByEmailOrUsername("user1");
//        assertEquals("user1", userDetail.getUsername());
//        assertEquals("user1@gmail.com", userDetail.getEmail());
//        assertEquals("1234567890", userDetail.getContactNo());
//        assertEquals(10L, userDetail.getFavDrops());
//        assertEquals(AV1, userDetail.getAvatar());
//    }
//
//    @Test
//    public void testGetUserByEmailOrUsername2() {
//        when(repository.findByEmailOrUsername(anyString(), anyString())).thenThrow(UserAuthorizationException.class);
//        Assertions.assertThrows(UserAuthorizationException.class, () -> service.getUserByEmailOrUsername("user2"));
//    }
//
//    @Test
//    public void testAddUser() {
//        when(repository.save(any(UserDetail.class))).thenReturn(userDetail2);
//        when(transformer.map(any(UserRequest.class))).thenReturn(userDetail2);
//        UserDetail userDetail = service.addUser(userRequest);
//        assertEquals("user1", userDetail.getUsername());
//        assertEquals("user1@gmail.com", userDetail.getEmail());
//        assertEquals("1234567890", userDetail.getContactNo());
//        assertEquals(AV1, userDetail.getAvatar());
//    }
//}
