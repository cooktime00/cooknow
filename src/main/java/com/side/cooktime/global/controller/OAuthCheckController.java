package com.side.cooktime.global.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class OAuthCheckController {

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String loginRequest() {
        return "login";
    }

    @GetMapping("/user/main")
    public String userMain(Model model, OAuth2AuthenticationToken authentication) {
        OAuth2User principal = authentication.getPrincipal();
        log.info(principal.getAttributes());
        model.addAttribute("userName", principal.getAttribute("name"));
        model.addAttribute("userEmail", principal.getAttributes().get("email"));
        model.addAttribute("userImageUrl", principal.getAttributes().get("picture"));
        model.addAttribute("userName", authentication.getName());
        return "main";
    }
}
