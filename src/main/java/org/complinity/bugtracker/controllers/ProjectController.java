package org.complinity.bugtracker.controllers;

import org.complinity.bugtracker.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional<String> result = projectService.createProject(projectData);

        return result
            .map(s -> ResponseEntity.badRequest().body(s))
            .orElseGet(() -> ResponseEntity.ok("Creation successful for " + projectData.get("name") + '.'));
    }
}
