package com.side.cooktime.global.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.side.cooktime.domain.user.service.UserService;
import com.side.cooktime.global.FirebaseService;
import com.side.cooktime.global.model.dto.response.ResponseSignIn;
import com.side.cooktime.global.model.dto.response.ResponseRefresh;
import com.side.cooktime.global.model.dto.response.ResponseSignOut;
import com.side.cooktime.global.model.security.RefreshToken;
import com.side.cooktime.global.security.JwtTokenService;
import com.side.cooktime.global.service.RefreshTokenService;
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
    private final FirebaseService firebaseService;

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseSignIn> signIn() throws FirebaseAuthException {
        userService.save();
        String accessToken = jwtTokenService.createAccessToken();
        RefreshToken refreshToken = refreshTokenService.createToken();
        refreshTokenService.save(refreshToken);
        ResponseSignIn responseDto = new ResponseSignIn(accessToken, refreshToken);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseRefresh> refresh() {
        String accessToken = jwtTokenService.refreshAccessToken();
        ResponseRefresh responseDto = new ResponseRefresh(accessToken);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<ResponseSignOut> signOut() {
        refreshTokenService.delete();
        return ResponseEntity.ok(new ResponseSignOut());
    }

    //    @PostMapping("/validate")
//    public

//    @PostMapping("/withdraw")

}
