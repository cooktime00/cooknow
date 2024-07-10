package com.side.cooknow.global.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class OAuth2UserUtils {

    public static String getEmail(Authentication authentication) {
        // 현재 로그인된 사용자의 정보 가져오기
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 이메일 정보 가져오기
        return userDetails.getUsername();
    }

}
