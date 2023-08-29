package com.side.cooktime.controller;

import com.side.cooktime.config.auth.LoginResponse;
import com.side.cooktime.config.auth.jwt.JwtFilter;
import com.side.cooktime.domain.member.model.Member;
import com.side.cooktime.domain.member.service.GoogleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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

    //임시
    //https://accounts.google.com/o/oauth2/v2/auth?response_type=code&client_id=595159133596-ibjnkksdef8bumsndm9vjn0cd4790jtr.apps.googleusercontent.com&scope=email%20profile&redirect_uri=http://localhost/api/v1/callback
    @GetMapping(value = "/callback")
    public ResponseEntity<LoginResponse> GoogleSignCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        log.info("## [REQUEST] code : {}", request.getParameter("code"));
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("authorizationCode", request.getParameter("code"));
        return googleLogin(requestMap);
    }

    @PostMapping(value = "/google-login")
    @ResponseBody
    public ResponseEntity<LoginResponse> googleLogin(@RequestBody Map<String, String> requestMap) throws Exception {
        log.info("googleLogin");
        Member loginMember  = googleService.login(requestMap.get("authorizationCode"));
        LoginResponse loginResponse = googleService.getJwtResponse(loginMember);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + loginResponse.getToken());
        return new ResponseEntity<>(loginResponse, httpHeaders, HttpStatus.OK);
    }
}
