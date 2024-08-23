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

@Transactional
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

    public RefreshToken createToken(final User user) throws FirebaseAuthException {
        RefreshToken refreshToken = generateUniqueToken(user);
        save(refreshToken);
        return refreshToken;
    }

    public void deleteAllByUser(final User user) {
        refreshTokenRepository.updateEmailByUser(user);
    }

    public void deleteAllByEmail(final User user) {
        refreshTokenRepository.updateEmailByUser(user);
    }

    @Transactional(readOnly = true)
    public RefreshToken findByToken(final Token token) {
        return refreshTokenRepository.findByTokenAndDeletedAtIsNull(token)
                .orElseThrow(() -> new OauthException(OauthErrorCode.REFRESH_TOKEN_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public boolean existsByToken(final Token token) {
        return refreshTokenRepository.existsByTokenAndDeletedAtIsNull(token);
    }

    @Transactional(readOnly = true)
    public User verifyToken(final Token token) {
        RefreshToken refreshToken = findByToken(token);

        if (refreshToken.isExpired()) {
            throw new OauthException(OauthErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        return refreshToken.getUser();
    }

    private RefreshToken generateUniqueToken(final User user) {
        RefreshToken refreshToken;
        do {
            refreshToken = RefreshToken.of(user, refreshTokenExpiration);
        } while (existsByToken(refreshToken.getToken()));
        return refreshToken;
    }
}
