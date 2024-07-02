package com.side.cooktime.global;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.side.cooktime.domain.user.model.Role;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.global.config.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FirebaseService {

    private FirebaseToken firebaseToken;
    private final FirebaseAuth firebaseAuth;
    private final AuthenticationFacade authenticationFacade;

    public User getUserRecord() throws FirebaseAuthException {
        String email = authenticationFacade.getAuthenticatedUserEmail();
        UserRecord userRecord = firebaseAuth.getUserByEmail(email);
        return buildUser(userRecord);
    }

    public String getUserEmail() {
        return firebaseToken.getEmail();
    }

    public boolean verifyToken(String idToken) {
        return verifyFirebaseToken(idToken).map(token -> {
            this.firebaseToken = token;
            return true;
        }).orElse(false);
    }

    public void deleteUser() throws FirebaseAuthException {
        String uid = getUid();
        firebaseAuth.deleteUser(uid);
    }

    private String getUid() throws FirebaseAuthException {
        String userEmail = authenticationFacade.getAuthenticatedUserEmail();
        UserRecord userRecord = firebaseAuth.getUserByEmail(userEmail);
        return userRecord.getUid();
    }


    private Optional<FirebaseToken> verifyFirebaseToken(String token) {
        try {
            return Optional.of(firebaseAuth.verifyIdToken(token));
        } catch (FirebaseAuthException e) {
            return Optional.empty();
        }
    }

    private User buildUser(UserRecord userRecord) {
        String provider = userRecord.getProviderData()[0].getProviderId();
        String[] nameParts = userRecord.getDisplayName().split(" ");
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName = nameParts.length > 1 ? nameParts[1] : "";
        return new User(provider, userRecord.getEmail(), firstName, lastName, Role.USER);
    }
}
