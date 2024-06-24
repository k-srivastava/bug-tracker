package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.services.ProjectService;
import org.complinity.bugtracker.utils.DBTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProject(@PathVariable("id") int id) {
        Map<String, Object> project = projectService.getProjectById(id);

        if (project == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(project);
    }

    @GetMapping("/{id}/developers")
    public ResponseEntity<List<String>> getDevelopers(@PathVariable("id") int id) {
        Map<String, Object> project = projectService.getProjectById(id);

        if (project == null)
            return ResponseEntity.notFound().build();

        List<String> developers = projectService.getDevelopersByProjectId((int) project.get("id"));
        return ResponseEntity.ok(developers);
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Map<String, Object> projectData) {
        DBTransactionState result = projectService.createProject(projectData);

        return switch (result) {
            case OK -> ResponseEntity.ok("Creation successful for " + projectData.get("name") + '.');
            case ALREADY_EXISTS -> ResponseEntity.badRequest().body("Project with ID " + projectData.get("id") + " already exists.");
            default -> ResponseEntity.internalServerError().body("Database error occurred.");
        };
    }

    @PutMapping("/{id}/assign")
    public ResponseEntity<String> assignDeveloper(@PathVariable("id") int id, @RequestBody String developerUsername) {
        DBTransactionState result = projectService.assignDeveloper(id, developerUsername);

        return switch (result) {
            case OK -> ResponseEntity.ok("Assignment successful for project.");
            case DOES_NOT_EXIST -> ResponseEntity.notFound().build();
            default -> ResponseEntity.internalServerError().body("Database error occurred.");
        };
    }
}
