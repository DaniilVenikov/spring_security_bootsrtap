package ru.venikov.spring.boot_security.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.venikov.spring.boot_security.security.OurUserDetails;
import ru.venikov.spring.boot_security.services.UserService;

@Controller
@RequestMapping("/user")
public class UsersController {

    private final UserService userService;
    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String userPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OurUserDetails userDetails = (OurUserDetails) authentication.getPrincipal();

        model.addAttribute("user", userService.getUser(userDetails.getUser().getId()));

        return "users/userPage";
    }
}
