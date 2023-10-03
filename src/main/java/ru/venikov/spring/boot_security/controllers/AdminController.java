package ru.venikov.spring.boot_security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.venikov.spring.boot_security.models.User;
import ru.venikov.spring.boot_security.repositories.RoleRepository;
import ru.venikov.spring.boot_security.security.OurUserDetails;
import ru.venikov.spring.boot_security.services.RegistrationService;
import ru.venikov.spring.boot_security.services.UserService;
import ru.venikov.spring.boot_security.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    private final UserValidator userValidator;
    private final RegistrationService registrationService;


    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository, UserValidator userValidator, RegistrationService registrationService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping()
    public String adminPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OurUserDetails userDetails = (OurUserDetails) authentication.getPrincipal();

        model.addAttribute("user", userService.getUser(userDetails.getUser().getId()));
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleRepository.getRolesBy());

        return "admin/adminPage";
    }

    @GetMapping("/users/new")
    public String newUser(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.getRolesBy());
        return "admin/newUser";
    }

    @PostMapping("/users")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "admin/newUser";
        }

        registrationService.register(user);
        return "redirect:/admin";
    }

    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute("user") @Valid User user, Model model, @PathVariable("id") int id) {
        model.addAttribute("roles", roleRepository.getRolesBy());
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
