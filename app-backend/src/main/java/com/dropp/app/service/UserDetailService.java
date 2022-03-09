package com.dropp.app.service;

import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.UserRequest;
import com.dropp.app.model.UserDetail;
import com.dropp.app.repository.UserDetailRepository;
import com.dropp.app.transformer.UserDetailTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {

    private final UserDetailRepository repository;
    private final UserDetailTransformer transformer;

    public UserDetail getUserByEmailOrUsername(String emailOrUsername) {
        return repository.findByEmailOrUsername(emailOrUsername, emailOrUsername).orElseThrow(() ->
                new UserNotFoundException("User not found with email or username: " + emailOrUsername)
        );
    }

    public UserDetail addUser(UserRequest userRequest) {
        UserDetail userDetail = transformer.map(userRequest);
        return repository.save(userDetail);
    }
}