package com.example.springsecurityfinal.controller;

import com.example.springsecurityfinal.domain.auth.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal AuthUser user,
                       @AuthenticationPrincipal OAuth2User oAuth2User,
                       Model model) {
        if(oAuth2User != null) {
            model.addAttribute("loginName", oAuth2User.getAttribute("name"));
        } else {
            model.addAttribute("loginName", user.getUserName());
        }
        return "home";
    }
}
