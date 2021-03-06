package com.dropp.app.service;

import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.UserDetail;
import com.dropp.app.model.UserRequest;
import com.dropp.app.model.dto.UserDetailDTO;
import com.dropp.app.repository.UserDetailRepository;
import com.dropp.app.transformer.UserDetailTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository repository;
    private final UserDetailTransformer transformer;

    /**
     * This method is responsible to find User Details for the requested email or username.
     *
     * @param emailOrUsername email or username
     * @return user information for requested email or username
     */

    public UserDetailDTO getUserByEmailOrUsername(String emailOrUsername) {
        UserDetail userDetail = repository.findByEmailOrUsername(emailOrUsername, emailOrUsername).orElseThrow(() ->
                new UserNotFoundException("User not found with email or username: " + emailOrUsername)
        );
        return transformer.map(userDetail);
    }

    /**
     * This method is responsible to add new user.
     *
     * @param userRequest the information of new user to be added
     * @return Newly added user information
     */

    public UserDetailDTO addUser(UserRequest userRequest) {
        UserDetail userDetail = transformer.map(userRequest);
        return transformer.map(repository.save(userDetail));
    }
}
