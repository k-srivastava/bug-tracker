package org.complinity.bugtracker.models;

/**
 * Core user model in the application.
 *
 * @param emailAddress Email address of the user; primary key in the database.
 * @param encodedPassword Encoded password of the user.
 */
public record UserModel(String emailAddress, String encodedPassword) {
}
