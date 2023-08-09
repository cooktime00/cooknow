package com.side.cooktime.config.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class OAuth2UserUtils {

    public static String getEmail(Authentication authentication) {
        // 현재 인증된 사용자의 정보 가져오기
        DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
        // oauth2User에서 이메일 정보 가져오기
        return oauth2User.getAttribute("email");
    }

}
