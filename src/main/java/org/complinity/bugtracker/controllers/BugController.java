package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.services.BugService;
import org.complinity.bugtracker.utils.DBTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
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

        if (result == DBTransactionState.OK)
            return ResponseEntity.ok("Creation successful for bug.");
        return ResponseEntity.internalServerError().body("Database error occurred.");
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<String> assignBug(@PathVariable("id") int id, @RequestBody String bugOwner) {
        DBTransactionState result = bugService.assignBug(id, bugOwner);

        return switch (result) {
            case OK -> ResponseEntity.ok("Assignment successful for bug.");
            case DOES_NOT_EXIST -> ResponseEntity.notFound().build();
            default -> ResponseEntity.internalServerError().body("Database error occurred.");
        };
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<String> closeBug(@PathVariable("id") int id) {
        DBTransactionState result = bugService.closeBug(id, new Date(System.currentTimeMillis()));

        return switch (result) {
            case OK -> ResponseEntity.ok("Closure successful for bug.");
            case DOES_NOT_EXIST -> ResponseEntity.notFound().build();
            default -> ResponseEntity.internalServerError().body("Database error occurred.");
        };
    }
}
