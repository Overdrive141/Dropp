package com.dropp.app.controller;

import com.dropp.app.model.DropRequest;
import com.dropp.app.model.dto.DropDetailDTO;
import com.dropp.app.service.DropDetailService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DropDetailController {

    private final DropDetailService dropDetailService;

    @GetMapping("/drop/{id}")
    public DropDetailDTO getDrop(@PathVariable Long id) {
        return dropDetailService.getDrop(id);
    }

    @PostMapping("/user/{userId}/drop")
    public DropDetailDTO addDropForUser(@PathVariable @NotNull @NotEmpty Long userId, @RequestBody @Valid DropRequest dropRequest, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
//        if (!authorizationHeader.startsWith(Constant.BEARER + " ")) {
//            throw new UserAuthorizationException("User not authorized!!");
//        }
//        String[] headerValues = authorizationHeader.split(" ");
//        if (headerValues.length != 2) {
//            throw new UserAuthorizationException("User not authorized!!");
//        }
//        String idToken = headerValues[1];
//        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//        String uid = decodedToken.getUid();
        return dropDetailService.addDrop(userId, dropRequest);
    }

    @GetMapping("/user/{userId}/drop")
    public List<DropDetailDTO> getDropsForUser(@PathVariable @NotNull @NotEmpty Long userId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
//        if (!authorizationHeader.startsWith(Constant.BEARER + " ")) {
//            throw new UserAuthorizationException("User not authorized!!");
//        }
//        String[] headerValues = authorizationHeader.split(" ");
//        if (headerValues.length != 2) {
//            throw new UserAuthorizationException("User not authorized!!");
//        }
//        String idToken = headerValues[1];
//        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
//        String uid = decodedToken.getUid();
        return dropDetailService.getDropsForUser(userId);
    }

    @PostMapping("/user/{userId}/drop/{dropId}/star")
    public DropDetailDTO starDrop(@PathVariable @NotNull @NotEmpty Long userId, @PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        return dropDetailService.starDrop(userId, dropId);
    }

    @PutMapping("/user/{userId}/drop/{dropId}/star")
    public DropDetailDTO unstarDrop(@PathVariable @NotNull @NotEmpty Long userId, @PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        return dropDetailService.unstarDrop(userId, dropId);
    }

//    @GetMapping("/user/{userId}/star")
//    public Set<DropDetailDTO> getStarredDrops(@PathVariable @NotNull @NotEmpty Long userId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
//        return dropDetailService.getStarredDrops(userId);
//    }

    @PostMapping("/user/{userId}/drop/{dropId}/explore")
    public DropDetailDTO exploreDrop(@PathVariable @NotNull @NotEmpty Long userId, @PathVariable @NotNull @NotEmpty Long dropId, @NotNull @NotEmpty @RequestHeader("Authorization") String authorizationHeader) throws FirebaseAuthException {
        return dropDetailService.exploreDrop(userId, dropId);
    }
}
