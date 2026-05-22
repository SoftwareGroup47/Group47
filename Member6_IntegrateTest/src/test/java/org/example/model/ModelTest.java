package org.example.model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for all model classes (User, Job, Application, TAProfile).
 * Tests constructors, getters, setters, and business logic.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModelTest {

    @BeforeAll
    static void setup() {
        System.out.println("=== Model Class Tests ===");
    }

    // ── User Model Tests ──

    @Test @Order(1)
    @DisplayName("User: constructor and field values")
    void testUserConstructor() {
        User user = new User("alice", "secret", "TA");
        assertEquals("alice", user.getUsername());
        assertEquals("secret", user.getPassword());
        assertEquals("TA", user.getRole());
    }

    @Test @Order(2)
    @DisplayName("User: default constructor and setters")
    void testUserSetters() {
        User user = new User();
        user.setUsername("bob");
        user.setPassword("pass");
        user.setRole("MO");
        assertEquals("bob", user.getUsername());
        assertEquals("MO", user.getRole());
    }

    // ── Job Model Tests ──

    @Test @Order(3)
    @DisplayName("Job: constructor generates UUID and defaults to Open")
    void testJobConstructor() {
        Job job = new Job("Algorithms TA", "Java, DSA", "mo1");
        assertNotNull(job.getJobId(), "Job ID should be auto-generated UUID");
        assertEquals("Algorithms TA", job.getJobName());
        assertEquals("Java, DSA", job.getRequirements());
        assertEquals("mo1", job.getMoName());
        assertEquals("Open", job.getStatus(), "New job should default to Open");
    }

    @Test @Order(4)
    @DisplayName("Job: each instance has a unique UUID")
    void testJobUniqueId() {
        Job job1 = new Job("JobA", "Java", "mo1");
        Job job2 = new Job("JobB", "Python", "mo2");
        assertNotEquals(job1.getJobId(), job2.getJobId(), 
                        "Each job should have a unique ID");
    }

    @Test @Order(5)
    @DisplayName("Job: status can be updated")
    void testJobStatusUpdate() {
        Job job = new Job("JobX", "skill", "mo");
        job.setStatus("Closed");
        assertEquals("Closed", job.getStatus());
    }

    // ── Application Model Tests ──

    @Test @Order(6)
    @DisplayName("Application: constructor and field values")
    void testApplicationConstructor() {
        Application app = new Application("id-001", "Java TA", "alice", 
                                          "2026-05-21", "Pending");
        assertEquals("id-001", app.getId());
        assertEquals("Java TA", app.getJobName());
        assertEquals("alice", app.getApplicant());
        assertEquals("2026-05-21", app.getApplyTime());
        assertEquals("Pending", app.getStatus());
    }

    @Test @Order(7)
    @DisplayName("Application: status transitions")
    void testApplicationStatusTransitions() {
        Application app = new Application("id-002", "DB TA", "bob", "now", "Pending");
        app.setStatus("Accepted");
        assertEquals("Accepted", app.getStatus());
        app.setStatus("Rejected");
        assertEquals("Rejected", app.getStatus());
    }

    // ── TAProfile Model Tests ──

    @Test @Order(8)
    @DisplayName("TAProfile: constructor and field values")
    void testTAProfileConstructor() {
        TAProfile profile = new TAProfile("ta_alice", 
                "Java, Python, SQL", "GPA 3.8", "cv.pdf");
        assertEquals("ta_alice", profile.getUsername());
        assertEquals("Java, Python, SQL", profile.getSkills());
        assertEquals("GPA 3.8", profile.getGrades());
        assertEquals("cv.pdf", profile.getCvPath());
    }

    @Test @Order(9)
    @DisplayName("TAProfile: default constructor")
    void testTAProfileDefaultConstructor() {
        TAProfile profile = new TAProfile();
        assertNull(profile.getUsername());
        profile.setUsername("new_ta");
        profile.setSkills("C++, Docker");
        assertEquals("new_ta", profile.getUsername());
        assertEquals("C++, Docker", profile.getSkills());
    }
}
