package com.dropp.app.controller;

import com.dropp.app.model.UserDetail;
import com.dropp.app.model.UserRequest;
import com.dropp.app.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{emailOrUsername}")
    public UserDetail getUserByEmailIdOrUsername(@PathVariable("emailOrUsername") String emailOrUsername) {
        return userDetailService.getUserByEmailOrUsername(emailOrUsername);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user")
    public UserDetail addUser(@Valid @RequestBody UserRequest userRequest) {
        return userDetailService.addUser(userRequest);
    }
}
