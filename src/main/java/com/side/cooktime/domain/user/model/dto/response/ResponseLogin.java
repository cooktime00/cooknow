package com.side.cooktime.domain.user.model.dto.response;

import com.side.cooktime.domain.user.model.User;
import lombok.Data;

@Data
public class ResponseLogin {

    private Long id;
    private String email;
    private String name;
    private String provider;

    public ResponseLogin(User user) {
        this.id = user.getId();
        this.email = user.getEmailValue();
        this.name = user.getFullName();
        this.provider = user.getProvider();
    }
}
