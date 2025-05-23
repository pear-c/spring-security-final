package com.example.springsecurityfinal.controller;

import com.example.springsecurityfinal.domain.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal AuthUser user, Model model) {
        model.addAttribute("loginName", user.getUserName());
        return "home";
    }
}
