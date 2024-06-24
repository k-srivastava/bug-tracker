package org.complinity.bugtracker.services;

import org.complinity.bugtracker.utils.DBTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get all the users' usernames in the database.
     *
     * @return Usernames of all the users if there are users, else an empty List.
     */
    public List<String> getAllUsernames() {
        String query = "SELECT username FROM users";

        try {
            return jdbcTemplate.queryForList(query, String.class);
        }

        catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Get the user by its email address (primary key) from the database.
     *
     * @param username Username of the user to search for.
     *
     * @return User data if the corresponding user is found, else null.
     */
    public Map<String, Object> getUserByUsername(String username) {
        String query = "SELECT * FROM users where username = ?";

        try {
            return jdbcTemplate.queryForMap(query, username);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Authenticate the login details of a user with the database entry.
     *
     * @param loginData User details received at login with a plaintext password.
     *
     * @return True if the user email and password match, else false.
     */
    public boolean authenticateUser(Map<String, Object> loginData) {
        Map<String, Object> dbUser = getUserByUsername(loginData.get("username").toString());

        if (dbUser == null)
            return false;

        return passwordEncoder.matches(loginData.get("password").toString(), dbUser.get("password").toString());
    }

    /**
     * Register a new user in the database if one does not already exist with the same email address.
     *
     * @param userData User details received with a plaintext password.
     *
     * @return State corresponding to the transaction.
     */
    public DBTransactionState registerUser(Map<String, Object> userData) {
        if (getUserByUsername(userData.get("username").toString()) != null)
            return DBTransactionState.ALREADY_EXISTS;

        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try {
            jdbcTemplate.update(
                query, userData.get("username"), passwordEncoder.encode(userData.get("password").toString())
            );

            return DBTransactionState.OK;
        }

        catch (DataAccessException e) {
            LOGGER.error("Failed to register user.", e);
            return DBTransactionState.ACCESS_ERROR;
        }
    }
}
