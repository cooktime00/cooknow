package com.side.cooknow.global.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.side.cooknow.domain.user.exception.UserErrorCode;
import com.side.cooknow.domain.user.exception.UserException;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.domain.user.service.UserService;
import com.side.cooknow.global.config.auth.AuthenticationFacade;
import com.side.cooknow.global.model.dto.response.*;
import com.side.cooknow.global.model.security.RefreshToken;
import com.side.cooknow.global.service.JwtTokenService;
import com.side.cooknow.global.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final JwtTokenService jwtTokenService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final AuthenticationFacade authenticationFacade;

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<SignInResponse>> signIn() throws FirebaseAuthException {
        User user = getAuthUser();
        String accessToken = jwtTokenService.createAccessToken(user);
        refreshTokenService.deleteAllByEmail(user);
        RefreshToken refreshToken = refreshTokenService.createToken(user);
        SignInResponse responseDto = new SignInResponse(user.getId(), accessToken, refreshToken);
        return createResponse("Sign in successfully", responseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshResponse>> refresh() {
        User user = authenticationFacade.getAuthenticatedUser();
        String accessToken = jwtTokenService.refreshAccessToken(user);
        RefreshResponse responseDto = new RefreshResponse(accessToken);
        return createResponse("Refresh token successfully", responseDto);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse<SignOutResponse>> signOut() {
        User user = authenticationFacade.getAuthenticatedUser();
        refreshTokenService.deleteAllByUser(user);
        return createResponse("Sign out successfully", new SignOutResponse());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<WithdrawResponse>> withdraw() {
        User user = authenticationFacade.getAuthenticatedUser();
        userService.delete(user);
        refreshTokenService.deleteAllByUser(user);
        return createResponse("Withdraw successfully", new WithdrawResponse());
    }

    @PostMapping("/verify-token")
    public ResponseEntity<ApiResponse<VerifyTokenResponse>> verifyToken() {
        return createResponse("Token verified successfully", new VerifyTokenResponse());
    }

    private User getAuthUser() throws FirebaseAuthException {
        User user = authenticationFacade.getAuthenticatedUser();
        if (!userService.existsByEmail(user.getEmail())) {
            return userService.save(user.getEmail());
        }
        return userService.findByEmail(user.getEmail());
    }

    private void isValidateRequest(final User user, final String email) {
        if (!user.isValidateRequest(email)) {
            throw new UserException(UserErrorCode.INVALID_REQUEST);
        }
    }

    private <T> ResponseEntity<ApiResponse<T>> createResponse(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>(HttpStatus.OK.value(), message, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
