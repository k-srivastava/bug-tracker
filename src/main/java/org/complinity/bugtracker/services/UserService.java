package org.complinity.bugtracker.services;

import org.complinity.bugtracker.models.LoginModel;
import org.complinity.bugtracker.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
     * @return UserModel if the corresponding user is found, else null.
     */
    public UserModel getUserByEmailAddress(String emailAddress) {
        String query = "SELECT * FROM users where email_address = ?";

        try {
            return jdbcTemplate.queryForObject(
                query,
                (rs, rowNum) -> new UserModel(
                    rs.getString("email_address"),
                    rs.getString("password")
                ),
                emailAddress
            );
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * Authenticate the login details of a user with the database entry.
     *
     * @param login User details received at login with a plaintext password.
     *
     * @return True if the user email and password match, else false.
     */
    public boolean authenticateUser(LoginModel login) {
        UserModel dbUser = getUserByEmailAddress(login.emailAddress());

        if (dbUser == null)
            return false;

        return passwordEncoder.matches(login.plainPassword(), dbUser.encodedPassword());
    }
}
