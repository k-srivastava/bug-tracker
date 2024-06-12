package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.models.LoginModel;
import org.complinity.bugtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String login(@RequestBody LoginModel login) {
        if (userService.authenticateUser(login))
            return "Login successful for " + login.emailAddress() + '.';
        return "Invalid email address or password.";
    }
}
