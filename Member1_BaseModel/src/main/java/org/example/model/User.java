package org.example.model;

/**
 * Represents a user in the TA recruitment system.
 * Each user has a username, SHA-256 hashed password, and role (TA or MO).
 */
public class User {
    private String username;
    private String password; // SHA-256 hashed
    private String role;     // "TA" or "MO"

    public User() {}

    /**
     * Constructs a new User.
     * @param username unique username
     * @param password plain text password (will be hashed before storage)
     * @param role user role: "TA" (Teaching Assistant) or "MO" (Module Organizer)
     */
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
