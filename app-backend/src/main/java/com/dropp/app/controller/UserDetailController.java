package com.dropp.app.controller;

import com.dropp.app.common.Constant;
import com.dropp.app.exception.UserAuthorizationException;
import com.dropp.app.model.UserDetail;
import com.dropp.app.model.UserRequest;
import com.dropp.app.service.UserDetailService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
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

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/user/{emailOrUsername}")
    public UserDetail getUserByEmailIdOrUsername(@PathVariable("emailOrUsername") String emailOrUsername, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        if (!authorizationHeader.startsWith(Constant.BEARER + " ")) {
            throw new UserAuthorizationException("User not authorized!!");
        }
        String[] headerValues = authorizationHeader.split(" ");
        if (headerValues.length != 2) {
            throw new UserAuthorizationException("User not authorized!!");
        }
        String idToken = headerValues[1];
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        return userDetailService.getUserByEmailOrUsername(emailOrUsername);
    }

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/user")
    public UserDetail addUser(@Valid @RequestBody UserRequest userRequest, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        if (!authorizationHeader.startsWith(Constant.BEARER + " ")) {
            throw new UserAuthorizationException("User not authorized!!");
        }
        String[] headerValues = authorizationHeader.split(" ");
        if (headerValues.length != 2) {
            throw new UserAuthorizationException("User not authorized!!");
        }
        String idToken = headerValues[1];
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        return userDetailService.addUser(userRequest);
    }
}
