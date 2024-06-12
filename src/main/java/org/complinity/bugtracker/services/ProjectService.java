package org.complinity.bugtracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Get the project by its ID (primary key) from the database.
     *
     * @param id ID of the project to search for.
     *
     * @return Project data if the corresponding project is found, else null.
     */
    public Map<String, Object> getProjectById(int id) {
        String query = "SELECT * FROM projects WHERE id = ?";

        try {
            return jdbcTemplate.queryForMap(query, id);
        }

        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Get all the developers associated with a project by its ID (primary key) from the database.
     *
     * @param id ID of the project to search for.
     *
     * @return All developers if the corresponding project is found, else an empty List.
     */
    public List<String> getDevelopersByProjectId(int id) {
        String query = "SELECT developer_email_address FROM project_developers WHERE project_id = ?";

        try {
            return jdbcTemplate.queryForList(query, String.class, id);
        }

        catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Create a new project in the database if one does not already exist with the same ID.
     *
     * @param projectData Project details received.
     *
     * @return Empty Optional if the project has been successfully created, else Optional with corresponding error message.
     */
    public Optional<String> createProject(Map<String, Object> projectData) {
        if (getProjectById((int) projectData.get("id")) != null)
            return Optional.of("Project with id " + projectData.get("id") + " already exists.");

        String query = "INSERT INTO projects (id, name, owner) VALUES (?, ?, ?)";

        try {
            jdbcTemplate.update(query, projectData.get("id"), projectData.get("name"), projectData.get("owner"));
            return Optional.empty();
        }

        catch (DataAccessException e) {
            return Optional.of("Could not access database: " + e.getMessage());
        }
    }
}
