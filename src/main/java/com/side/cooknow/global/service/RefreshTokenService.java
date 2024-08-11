package com.side.cooknow.global.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.exception.OauthErrorCode;
import com.side.cooknow.global.exception.OauthException;
import com.side.cooknow.global.model.security.RefreshToken;
import com.side.cooknow.global.model.security.Token;
import com.side.cooknow.global.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private long refreshTokenExpiration;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void save(final RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshToken createToken(final User user) throws FirebaseAuthException {
        RefreshToken refreshToken = generateUniqueToken(user);
        save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public void deleteAllByUser(final User user) {
        refreshTokenRepository.updateEmailByUser(user);
    }

    @Transactional
    public void deleteAllByEmail(final User user) {
        refreshTokenRepository.updateEmailByUser(user);
    }

    public RefreshToken findByToken(final Token token) {
        return refreshTokenRepository.findByTokenAndDeletedAtIsNull(token)
                .orElseThrow(() -> new OauthException(OauthErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    public boolean existsByToken(final Token token) {
        return refreshTokenRepository.existsByTokenAndDeletedAtIsNull(token);
    }

    private RefreshToken generateUniqueToken(final User user) {
        RefreshToken refreshToken;
        do {
            refreshToken = RefreshToken.of(user, refreshTokenExpiration);
        } while (existsByToken(refreshToken.getToken()));
        return refreshToken;
    }
}
