package ru.venikov.spring.boot_security.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }
}
