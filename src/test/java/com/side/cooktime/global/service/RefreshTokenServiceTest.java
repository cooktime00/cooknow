package com.side.cooktime.global.service;

import com.side.cooktime.domain.user.model.Role;
import com.side.cooktime.domain.user.model.User;
import com.side.cooktime.global.model.security.RefreshToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RefreshTokenServiceTest {

    @Autowired
    private RefreshTokenService refreshTokenService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "provider", "test@cooknow.com", "firstName", "lastName", Role.USER);
    }

    @Test
    void createToken() {

        System.out.println(UUID.randomUUID().toString());
    }

    @Test
    void save() {
    }

    @Test
    void delete() {
    }

    @Test
    void findByToken() {
    }

    @Test
    void existsByToken() {
    }
}