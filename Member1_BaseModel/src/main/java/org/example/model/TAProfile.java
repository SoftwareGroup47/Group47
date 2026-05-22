package org.example.model;

/**
 * Represents a Teaching Assistant's skill profile.
 * Contains the TA's self-declared skills, academic grades, and CV file path.
 * This profile is used by the AI matching engine to evaluate job fit.
 */
public class TAProfile {
    private String username;  // TA's username
    private String skills;    // Comma/space separated skill keywords
    private String grades;    // Academic performance description
    private String cvPath;    // File path to the uploaded CV

    public TAProfile() {}

    /**
     * Constructs a TA profile.
     * @param username the TA's username
     * @param skills comma/space separated skills (e.g., "Java, Python, SQL")
     * @param grades academic grades or GPA description
     * @param cvPath path to the uploaded CV file
     */
    public TAProfile(String username, String skills, String grades, String cvPath) {
        this.username = username;
        this.skills = skills;
        this.grades = grades;
        this.cvPath = cvPath;
    }

    // ── Getters and Setters ──

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }

    public String getGrades() { return grades; }
    public void setGrades(String grades) { this.grades = grades; }

    public String getCvPath() { return cvPath; }
    public void setCvPath(String cvPath) { this.cvPath = cvPath; }
}
