package com.side.cooknow.global.model.security;

import com.side.cooknow.domain.user.model.User;
import com.side.cooknow.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken extends BaseEntity {

    @Embedded
    private Token token;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private LocalDateTime expirationDate;

    public static RefreshToken of(final User user, final long refreshTokenExpiration) {
        return new RefreshToken(user, refreshTokenExpiration);
    }

    public RefreshToken(final User user) {
        this.user = user;
        this.token = Token.createInstance();
    }

    public RefreshToken(User user, long refreshTokenExpiration) {
        this(user);
        this.expirationDate = createExpirationDate(refreshTokenExpiration);
    }

    private LocalDateTime createExpirationDate(long refreshTokenExpiration) {
        Instant futureInstant = Instant.now().plusMillis(refreshTokenExpiration);
        return LocalDateTime.ofInstant(futureInstant, ZoneId.systemDefault());
    }

    public String getTokenValue() {
        return token.getToken();
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDateTime.now());
    }
}
