package org.complinity.bugtracker.services;

import org.complinity.bugtracker.utils.DBTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

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
        String query = "SELECT developer_username FROM project_developers WHERE project_id = ?";

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
     * @return State corresponding to the transaction.
     */
    public DBTransactionState createProject(Map<String, Object> projectData) {
        String query = "INSERT INTO projects (name, owner_username, description) VALUES (?, ?, ?)";

        try {
            jdbcTemplate.update(query, projectData.get("name"), projectData.get("owner_username"), projectData.get("description"));
            return DBTransactionState.OK;
        }

        catch (DataAccessException e) {
            LOGGER.error("Failed to create project.", e);
            return DBTransactionState.ACCESS_ERROR;
        }
    }

    /**
     * Assign a new developer to an existing project.
     *
     * @param id ID of the project to which a developer is assigned.
     * @param developerUsername Username of the developer to be assigned.
     *
     * @return State corresponding to the transaction.
     */
    public DBTransactionState assignDeveloper(int id, String developerUsername) {
        if (getProjectById(id) == null)
            return DBTransactionState.DOES_NOT_EXIST;

        String query = "INSERT INTO project_developers (project_id, developer_username) VALUES (?, ?)";

        try {
            jdbcTemplate.update(query, id, developerUsername);
            return DBTransactionState.OK;
        }

        catch (DataAccessException e) {
            LOGGER.error("Failed to assign developer.", e);
            return DBTransactionState.ACCESS_ERROR;
        }
    }
}
