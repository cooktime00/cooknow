package com.side.cooknow.global;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import com.side.cooknow.domain.user.model.Email;
import com.side.cooknow.domain.user.model.Role;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.config.auth.AuthenticationFacade;
import com.side.cooknow.global.exception.OauthErrorCode;
import com.side.cooknow.global.exception.OauthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FirebaseService {

    private final FirebaseAuth firebaseAuth;
    private final AuthenticationFacade authenticationFacade;

    public User getUserRecord(Email email) throws FirebaseAuthException {
        try {
            UserRecord userRecord = firebaseAuth.getUserByEmail(email.getEmail());
            return buildUser(userRecord);
        } catch (FirebaseAuthException e) {
            throw new OauthException(OauthErrorCode.USER_NOT_FOUND);
        }
    }

    public String verifyToken(String idToken) {
        FirebaseToken token = verifyFirebaseToken(idToken)
                .orElseThrow(() -> new OauthException(OauthErrorCode.INVALID_ID_TOKEN));
        return token.getEmail();
    }

    private Optional<FirebaseToken> verifyFirebaseToken(String token) {
        try {
            return Optional.of(firebaseAuth.verifyIdToken(token));
        } catch (FirebaseAuthException e) {
            throw new OauthException(OauthErrorCode.EXPIRED_ID_TOKEN);
        }
    }

    public void deleteUser() throws FirebaseAuthException {
        String uid = getUid();
        firebaseAuth.deleteUser(uid);
    }

    private String getUid() throws FirebaseAuthException {
        User user = authenticationFacade.getAuthenticatedUser();
        Email email = user.getEmail();
        UserRecord userRecord = firebaseAuth.getUserByEmail(email.getEmail());
        return userRecord.getUid();
    }

    private User buildUser(UserRecord userRecord) {
        String provider = userRecord.getProviderData()[0].getProviderId();
        String[] nameParts = userRecord.getDisplayName().split(" ");
        String firstName = nameParts.length > 0 ? nameParts[0] : "";
        String lastName = nameParts.length > 1 ? nameParts[1] : "";
        return new User(provider, userRecord.getEmail(), firstName, lastName, Role.USER);
    }
}
