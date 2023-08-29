package com.side.cooktime.global.controller;

import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.service.GoogleService;
import com.side.cooktime.global.config.auth.LoginResponse;
import com.side.cooktime.global.config.auth.jwt.JwtFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OauthLoginController {
    private final GoogleService googleService;

    @Value("${google.redirectUri}")
    private String REDIRECT_URI;

    //서버 확인용
    //https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=595159133596-ibjnkksdef8bumsndm9vjn0cd4790jtr.apps.googleusercontent.com&scope=email%20profile&redirect_uri=http://localhost/api/v1/callback
    //https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=595159133596-ibjnkksdef8bumsndm9vjn0cd4790jtr.apps.googleusercontent.com&scope=email%20profile&redirect_uri=http://ec2-43-202-30-201.ap-northeast-2.compute.amazonaws.com/api/v1/callback

    @GetMapping(value = "/callback")
    public ResponseEntity<LoginResponse> GoogleSignCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        log.info("## [REQUEST] code : {}", request.getParameter("code"));
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("authorizationCode", request.getParameter("code"));
        requestMap.put("redirectUri", REDIRECT_URI);
        return googleLogin(requestMap);
    }

    @PostMapping(value = "/google-login")
    @ResponseBody
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody Map<String, String> requestMap) throws Exception {
        log.info("googleLogin");
        Member loginMember = googleService.login(requestMap.get("authorizationCode"), requestMap.get("redirectUri"));
        LoginResponse loginResponse = googleService.getJwtResponse(loginMember);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());
        return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
    }
}
