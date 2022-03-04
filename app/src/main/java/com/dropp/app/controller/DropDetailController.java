package com.dropp.app.controller;

import com.dropp.app.common.Constant;
import com.dropp.app.exception.UserAuthorizationException;
import com.dropp.app.model.DropDetail;
import com.dropp.app.model.DropRequest;
import com.dropp.app.service.DropDetailService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DropDetailController {

    private final DropDetailService service;

    @GetMapping("/drop/{id}")
    public DropDetail getDrop(@PathVariable Long id) {
        return service.getDrop(id);
    }

    @PostMapping("/user/{userId}/drop")
    public DropDetail addDropForUser(@PathVariable @NotNull @NotEmpty Long userId, @RequestBody @Valid DropRequest dropRequest, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
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
        return service.addDrop(userId, dropRequest);
    }

    @GetMapping("/user/{userId}/drop")
    public List<DropDetail> getDropsForUser(@PathVariable @NotNull @NotEmpty Long userId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
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
        return service.getDropsForUser(userId);
    }
}
