package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<String>> getAllUsernames() {
        List<String> usernames = userService.getAllUsernames();

        if (usernames.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(usernames);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable("username") String username) {
        Map<String, Object> user = userService.getUserByUsername(username);

        if (user == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(user);
    }
}
