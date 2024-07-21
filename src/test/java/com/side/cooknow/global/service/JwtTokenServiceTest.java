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
        User user = new User(4L, "google.com", "alex970902@gmail.com", "정혁", "", Role.USER);

        System.out.println(jwtTokenService.generateToken(user));
    }

}
