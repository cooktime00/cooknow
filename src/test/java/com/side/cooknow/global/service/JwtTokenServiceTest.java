package com.side.cooknow.global.service;

import com.side.cooknow.domain.user.model.Role;
import com.side.cooknow.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenServiceTest {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Test
    public void createAccessToken() {
        User user = new User(5L, "google.com", "jpark0902@kookmin.ac.kr", "정환", "박", Role.USER);
    }

}
