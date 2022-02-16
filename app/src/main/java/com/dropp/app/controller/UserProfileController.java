package com.dropp.app.controller;

import com.dropp.app.model.User;
import com.dropp.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{emailOrUsername}")
    public User getUserByEmailIdOrUsername(@PathVariable("emailOrUsername") String emailOrUsername) {
        return userService.getUserByEmailOrUsername(emailOrUsername);
    }
}
