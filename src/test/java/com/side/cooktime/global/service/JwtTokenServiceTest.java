package com.side.cooktime.global.service;

import com.side.cooktime.domain.user.model.Role;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.global.security.JwtTokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenServiceTest {

    @Autowired
    private JwtTokenService tokenProvider;
    private User user;

    @BeforeEach
    public void init() {
        user = new User(1L, "provider", "test@cooknow.com", "firstName", "lastName", Role.USER);
    }

    @Test
    void createAccessToken() {



    }

    @Test
    void createRefreshToken() {
    }

    @Test
    void verifyAccessToken() {
    }
}