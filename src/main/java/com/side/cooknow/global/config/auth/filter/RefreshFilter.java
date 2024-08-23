package com.side.cooknow.global.config.auth.filter;

import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.config.auth.OauthAuthenticationToken;
import com.side.cooknow.global.exception.OauthErrorCode;
import com.side.cooknow.global.exception.OauthException;
import com.side.cooknow.global.model.security.RefreshToken;
import com.side.cooknow.global.model.security.Token;
import com.side.cooknow.global.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;

@Log4j2
@RequiredArgsConstructor
public class RefreshFilter extends TokenFilter {
    private final RefreshTokenService refreshTokenService;


    @Override
    protected boolean authenticateToken(String authToken, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
//        RefreshToken refreshToken = refreshTokenService.findByToken(Token.of(authToken));
        User user = refreshTokenService.verifyToken(Token.of(authToken));


//        if (refreshToken.isExpired()) {
//            throw new OauthException(OauthErrorCode.EXPIRED_REFRESH_TOKEN);
//        }
        log.info("Refresh token verified");
//        User user = refreshToken.getUser();
        setAuthenticationAndContinueChain(new OauthAuthenticationToken(user, Collections.emptyList()), request, response, filterChain);
        return true;
    }
}
