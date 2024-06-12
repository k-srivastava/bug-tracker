package org.complinity.bugtracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get the user by its email address (primary key) from the database.
     *
     * @param emailAddress Email address of the user to search for.
     *
     * @return User data if the corresponding user is found, else null.
     */
    public Map<String, Object> getUserByEmailAddress(String emailAddress) {
        String query = "SELECT * FROM users where email_address = ?";

        try {
            return jdbcTemplate.queryForMap(query, emailAddress);
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
        Map<String, Object> dbUser = getUserByEmailAddress(loginData.get("emailAddress").toString());

        if (dbUser == null)
            return false;

        return passwordEncoder.matches(loginData.get("plainPassword").toString(), dbUser.get("password").toString());
    }

    /**
     * Register a new user in the database if one does not already exist with the same email address.
     *
     * @param userData User details received with a plaintext password.
     *
     * @return Empty Optional if the user has been successfully added, else Optional with corresponding error message.
     */
    public Optional<String> registerUser(Map<String, Object> userData) {
        if (getUserByEmailAddress(userData.get("emailAddress").toString()) != null)
            return Optional.of("User with email address " + userData.get("emailAddress").toString() + " already exists.");

        String query = "INSERT INTO users (email_address, password) VALUES (?, ?)";

        try {
            jdbcTemplate.update(
                query,
                userData.get("emailAddress").toString(),
                passwordEncoder.encode(userData.get("password").toString())
            );

            return Optional.empty();
        }

        catch (DataAccessException e) {
            return Optional.of("Could not access database: " + e.getMessage());
        }
    }
}
