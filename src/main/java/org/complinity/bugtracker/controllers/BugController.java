package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.services.BugService;
import org.complinity.bugtracker.utils.DBTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/bugs")
public class BugController {
    private final BugService bugService;

    @Autowired
    public BugController(BugService bugService) {
        this.bugService = bugService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBug(@PathVariable("id") int id) {
        Map<String, Object> bug = bugService.getBugById(id);

        if (bug == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(bug);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBug(@PathVariable("id") int id, @RequestBody Map<String, Object> bugData) {
        DBTransactionState result = bugService.updateBug(id, bugData);

        return switch (result) {
            case OK -> ResponseEntity.ok("Update successful for bug with ID " + bugData.get("id") + '.');
            case DOES_NOT_EXIST -> ResponseEntity.notFound().build();
            default -> ResponseEntity.internalServerError().body("Database error occurred.");
        };
    }

    @PostMapping("/create")
    public ResponseEntity<String> createBug(@RequestBody Map<String, Object> bugData) {
        DBTransactionState result = bugService.createBug(bugData);

        return switch (result) {
            case OK -> ResponseEntity.ok("Creation successful for bug with ID " + bugData.get("id") + '.');
            case ALREADY_EXISTS -> ResponseEntity.badRequest().body("Bug with ID " + bugData.get("id") + " already exists.");
            default -> ResponseEntity.internalServerError().body("Database error occurred.");
        };
    }
}
