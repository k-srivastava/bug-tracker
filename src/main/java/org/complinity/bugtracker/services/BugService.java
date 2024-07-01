package org.complinity.bugtracker.services;

import org.complinity.bugtracker.utils.DBTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Map;

@Service
public class BugService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BugService.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BugService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Get the bug by its ID (primary key) from the database.
     *
     * @param id ID of the bug to search for.
     *
     * @return Bug data is the corresponding bug is found, else null.
     */
    public Map<String, Object> getBugById(int id) {
        String query = "SELECT * FROM bugs WHERE id = ?";

        try {
            return jdbcTemplate.queryForMap(query, id);
        }

        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Create a new bug in the database if one does not already exist with the same ID.
     *
     * @param bugData Bug details received.
     *
     * @return State corresponding to the transaction.
     */
    public DBTransactionState createBug(Map<String, Object> bugData) {
        if (getBugById((int) bugData.get("id")) != null)
            return DBTransactionState.ALREADY_EXISTS;

        String query = "INSERT INTO bugs (id, title, description, creator, owner, created_date, target_date, resolution_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            jdbcTemplate.update(
                query,
                bugData.get("id"),
                bugData.get("title"),
                bugData.get("description"),
                bugData.get("creator"),
                bugData.get("owner"),
                bugData.get("created_date"),
                bugData.get("target_date"),
                bugData.get("resolution_date")
            );

            return DBTransactionState.OK;
        }

        catch (DataAccessException e) {
            LOGGER.error("Failed to create bug.", e);
            return DBTransactionState.ACCESS_ERROR;
        }
    }

    /**
     * Update an existing bug in the database by its ID.
     *
     * @param id ID of the bug to update.
     * @param bugData Updated bug details.
     *
     * @return State corresponding to the transaction.
     */
    public DBTransactionState updateBug(int id, Map<String, Object> bugData) {
        if (getBugById(id) == null)
            return DBTransactionState.DOES_NOT_EXIST;

        String query = "UPDATE bugs SET title = ?, description = ?, creator = ?, owner = ?, created_date = ?, target_date = ?, resolution_date = ? WHERE id = ?";

        try {
            jdbcTemplate.update(
                query,
                bugData.get("title"),
                bugData.get("description"),
                bugData.get("creator"),
                bugData.get("owner"),
                bugData.get("created_date"),
                bugData.get("target_date"),
                bugData.get("resolution_date"),
                id
            );

            return DBTransactionState.OK;
        }

        catch (DataAccessException e) {
            LOGGER.error("Failed to update bug.", e);
            return DBTransactionState.ACCESS_ERROR;
        }
    }

    /**
     * Assign an owner to an existing bug in the database by its ID.
     *
     * @param id ID of the bug to assign an owner to.
     * @param owner Email address of the new bug owner (primary key).
     *
     * @return State corresponding to the transaction.
     */
    public DBTransactionState assignBug(int id, String owner) {
        if (getBugById(id) == null)
            return DBTransactionState.DOES_NOT_EXIST;

        String query = "UPDATE bugs SET owner = ? WHERE id = ?";

        try {
            jdbcTemplate.update(query, owner, id);
            return DBTransactionState.OK;
        }

        catch (DataAccessException e) {
            LOGGER.error("Failed to assign bug.", e);
            return DBTransactionState.ACCESS_ERROR;
        }
    }

    /**
     * Close an existing bug in the database by its ID.
     *
     * @param id ID of the bug to close.
     * @param resolutionDate Date on which the bug is closed / resolved.
     *
     * @return State corresponding to the transaction.
     */
    public DBTransactionState closeBug(int id, Date resolutionDate) {
        if (getBugById(id) == null)
            return DBTransactionState.DOES_NOT_EXIST;

        String query = "UPDATE bugs SET resolution_date = ? WHERE id = ?";

        try {
            jdbcTemplate.update(query, resolutionDate, id);
            return DBTransactionState.OK;
        }

        catch (DataAccessException e) {
            LOGGER.error("Failed to close bug.", e);
            return DBTransactionState.ACCESS_ERROR;
        }
    }
}
