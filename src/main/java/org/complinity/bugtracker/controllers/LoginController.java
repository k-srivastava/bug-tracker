package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.services.UserService;
import org.complinity.bugtracker.utils.DBTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> loginData) {
        DBTransactionState result = userService.registerUser(loginData);

        return switch (result) {
            case OK -> ResponseEntity.ok("Registration successful for " + loginData.get("emailAddress") + '.');
            case ALREADY_EXISTS -> ResponseEntity.status(HttpStatus.CONFLICT).body("User with email address '" + loginData.get("emailAddress") + "' already exists.");
            default -> ResponseEntity.internalServerError().body("Database error occurred.");
        };
    }
}
