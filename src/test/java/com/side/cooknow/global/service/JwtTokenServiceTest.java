package com.side.cooknow.global.service;

import com.side.cooknow.domain.user.model.Role;
import com.side.cooknow.domain.user.model.User;


public class JwtTokenServiceTest {




    public void createAccessToken() {
        User user = new User(5L, "google.com", "jpark0902@kookmin.ac.kr", "정환", "박", Role.USER);
    }

}
