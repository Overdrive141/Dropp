package com.dropp.app.validation;

import com.dropp.app.common.Constant;
import com.dropp.app.exception.TokenValidationException;
import com.dropp.app.exception.UserAuthorizationException;
import com.dropp.app.service.UserDetailService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidationService {

    private final UserDetailService userDetailService;

    /**
     * This method is responsible to validate the authorization header string passed in the http request.
     *
     * @param authorizationHeader authorization header string
     * @return user id if the validation succeeds
     */
    public long validate(String authorizationHeader) {
        try {
            if (!authorizationHeader.startsWith(Constant.BEARER + " ")) {
                throw new UserAuthorizationException("User not authorized!!");
            }
            String[] headerValues = authorizationHeader.split(" ");
            if (headerValues.length != 2) {
                throw new UserAuthorizationException("User not authorized!!");
            }
            String idToken = headerValues[1];
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return userDetailService.getUserByEmailOrUsername(decodedToken.getEmail()).getId();
        } catch (FirebaseAuthException ex) {
            throw new TokenValidationException("Invalid token!!", ex);
        }
    }
}
