package com.side.cooknow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableJpaAuditing
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class CooknowApplication {

    public static void main(String[] args) {
        SpringApplication.run(CooknowApplication.class, args);
    }

}
