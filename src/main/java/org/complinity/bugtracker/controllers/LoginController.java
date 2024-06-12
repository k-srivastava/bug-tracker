package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Map<String, Object> loginData) {
        if (userService.authenticateUser(loginData))
            return ResponseEntity.ok("Login successful for " + loginData.get("emailAddress") + '.');
        return ResponseEntity.badRequest().body("Invalid email address or password.");
    }
}
