package com.side.cooktime.domain.member.model.dto;

import com.side.cooktime.domain.member.model.User;
import com.side.cooktime.domain.member.model.UserGoogleInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class GoogleUserDto {
    private String id;
    private String email;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String locale;

    public User toUserEntity() {
        UserGoogleInfo userGoogleInfo = new UserGoogleInfo(id, picture, locale);
        return User.ofGoogle(email, given_name, family_name, userGoogleInfo);
    }
}
