package com.dropp.app.transformer;

import com.dropp.app.model.UserDetail;
import com.dropp.app.model.UserRequest;
import com.dropp.app.model.dto.UserDetailDTO;
import org.springframework.stereotype.Service;

@Service
public class UserDetailTransformer {

    /**
     * This method transforms UserRequest object into UserDetail object.
     *
     * @param userRequest UserRequest object
     * @return UserDetail object
     */

    public UserDetail map(UserRequest userRequest) {
        return UserDetail.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .contactNo(userRequest.getContactNo())
                .favDrops(0L)
                .avatar(userRequest.getAvatar())
                .build();
    }

    /**
     * This method transforms UserDetail object into UserDetailDTO object.
     *
     * @param userDetail UserDetail object
     * @return UserDetailDTO object
     */

    public UserDetailDTO map(UserDetail userDetail) {
        return UserDetailDTO.builder()
                .id(userDetail.getId())
                .username(userDetail.getUsername())
                .email(userDetail.getEmail())
                .contactNo(userDetail.getContactNo())
                .avatar(userDetail.getAvatar())
                .favDrops(userDetail.getFavDrops())
                .build();
    }

}
