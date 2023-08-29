package com.side.cooktime.domain.member.model;

import com.side.cooktime.global.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "userGoogleInfo")
public class UserGoogleInfo extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column
    private final Provider provider = Provider.GOOGLE;
    @Column
    private String providerId;
    @Column
    private String picture;
    @Column
    private String locale;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public UserGoogleInfo(String providerId, String picture, String locale) {
        this.providerId = providerId;
        this.picture = picture;
        this.locale = locale;
    }

    public UserGoogleInfo(String providerId, String picture, String locale, User user) {
        this.providerId = providerId;
        this.picture = picture;
        this.locale = locale;
        this.user = user;
    }

    public UserGoogleInfo withUser(User user) {
        return new UserGoogleInfo(providerId, picture, locale, user);
    }
}
