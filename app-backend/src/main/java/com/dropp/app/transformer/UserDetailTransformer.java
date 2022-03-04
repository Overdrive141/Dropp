package com.dropp.app.transformer;

import com.dropp.app.model.UserRequest;
import com.dropp.app.model.UserDetail;
import org.springframework.stereotype.Service;

@Service
public class UserDetailTransformer {

    public UserDetail map(UserRequest userRequest) {
        return UserDetail.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .contactNo(userRequest.getContactNo())
                .build();
    }

}
