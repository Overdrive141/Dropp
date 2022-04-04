package com.dropp.app.transformer;

import com.dropp.app.model.UserDetail;
import com.dropp.app.model.UserRequest;
import com.dropp.app.model.dto.UserDetailDTO;
import com.dropp.app.model.enums.Avatar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.dropp.app.model.enums.Avatar.AV1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserDetailTransformerTest {

    @InjectMocks
    private UserDetailTransformer transformer;

    @Test
    public void testMap1() {
        UserRequest userRequest = UserRequest.builder()
                .username("user1")
                .contactNo("1234567890")
                .email("user1@gmail.com")
                .avatar(AV1)
                .build();
        UserDetail userDetail = transformer.map(userRequest);
        assertNotNull(userDetail);
        assertEquals("user1", userDetail.getUsername());
        assertEquals("user1@gmail.com", userDetail.getEmail());
        assertEquals("1234567890", userDetail.getContactNo());
        assertEquals(AV1, userDetail.getAvatar());
    }

    @Test
    public void testMap2() {
        UserDetail userDetail = UserDetail.builder()
                .id(1l)
                .username("user1")
                .email("user1@uwindsor.ca")
                .contactNo("1234567890")
                .favDrops(0l)
                .avatar(Avatar.AV1)
                .build();
        UserDetailDTO actualUserDetailDTO = transformer.map(userDetail);
        assertEquals(actualUserDetailDTO.getId(), userDetail.getId());
        assertEquals(actualUserDetailDTO.getUsername(), userDetail.getUsername());
        assertEquals(actualUserDetailDTO.getEmail(), userDetail.getEmail());
        assertEquals(actualUserDetailDTO.getContactNo(), userDetail.getContactNo());
        assertEquals(actualUserDetailDTO.getFavDrops(), userDetail.getFavDrops());
        assertEquals(actualUserDetailDTO.getAvatar(), userDetail.getAvatar());
    }
}
