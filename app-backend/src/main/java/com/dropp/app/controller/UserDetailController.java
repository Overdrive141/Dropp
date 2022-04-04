package com.dropp.app.controller;

import com.dropp.app.model.UserRequest;
import com.dropp.app.model.dto.UserDetailDTO;
import com.dropp.app.service.UserDetailService;
import com.dropp.app.validation.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailService userDetailService;
    private final ValidationService validationService;

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{emailOrUsername}")
    public UserDetailDTO getUserByEmailIdOrUsername(@PathVariable("emailOrUsername") String emailOrUsername, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return userDetailService.getUserByEmailOrUsername(emailOrUsername);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user")
    public UserDetailDTO addUser(@Valid @RequestBody UserRequest userRequest, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) {
        validationService.validate(authorizationHeader);
        return userDetailService.addUser(userRequest);
    }
}
