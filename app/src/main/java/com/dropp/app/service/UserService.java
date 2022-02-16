package com.dropp.app.service;

import com.dropp.app.exception.UserNotFoundException;
import com.dropp.app.model.User;
import com.dropp.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getUserByEmailOrUsername(String emailOrUsername) {
        return repository.findByEmailOrUsername(emailOrUsername, emailOrUsername).orElseThrow(() ->
                new UserNotFoundException("User not found with email or username: " + emailOrUsername)
        );
    }
}
