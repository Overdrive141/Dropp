package com.dropp.app.transformer;

import com.dropp.app.model.UserDetail;
import com.dropp.app.model.UserRequest;
import org.springframework.stereotype.Service;

@Service
public class UserDetailTransformer {

    public UserDetail map(UserRequest userRequest) {
        return UserDetail.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .contactNo(userRequest.getContactNo())
                .avatar(userRequest.getAvatar())
                .build();
    }

}
