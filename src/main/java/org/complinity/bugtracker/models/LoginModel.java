package org.complinity.bugtracker.models;

/**
 * Login data model in the application.
 *
 * @param emailAddress Email address received at login.
 * @param plainPassword Plaintext password received at login.
 */
public record LoginModel(String emailAddress, String plainPassword) {
}
