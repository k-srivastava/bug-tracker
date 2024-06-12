package org.complinity.bugtracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

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
}
