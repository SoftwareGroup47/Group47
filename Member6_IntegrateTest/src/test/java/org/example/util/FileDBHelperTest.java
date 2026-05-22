package org.example.util;

import org.example.model.*;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the file-based data storage system.
 * Tests CRUD operations for users, jobs, applications, and TA profiles.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FileDBHelperTest {

    private static final String TEST_USER = "test_user_" + System.currentTimeMillis();
    private static final String TEST_TA = "test_ta_" + System.currentTimeMillis();

    @BeforeAll
    static void setup() {
        System.out.println("=== FileDBHelper Tests ===");
        System.out.println("Test users: " + TEST_USER + ", " + TEST_TA);
    }

    // ── User Registration Tests ──

    @Test @Order(1)
    @DisplayName("Register a new TA user successfully")
    void testRegisterTAUser() {
        User user = new User(TEST_TA, "password123", "TA");
        boolean success = FileDBHelper.registerUser(user);
        assertTrue(success, "Registration should succeed for a new user");
        assertTrue(FileDBHelper.isUserExists(TEST_TA), "User should exist after registration");
    }

    @Test @Order(2)
    @DisplayName("Register a new MO user successfully")
    void testRegisterMOUser() {
        User user = new User(TEST_USER, "password456", "MO");
        boolean success = FileDBHelper.registerUser(user);
        assertTrue(success, "Registration should succeed for a new MO user");
    }

    @Test @Order(3)
    @DisplayName("Reject duplicate username registration")
    void testDuplicateRegistration() {
        User user = new User(TEST_TA, "different_pass", "TA");
        boolean success = FileDBHelper.registerUser(user);
        assertFalse(success, "Duplicate username should be rejected");
    }

    @Test @Order(4)
    @DisplayName("Login with correct credentials")
    void testLoginSuccess() {
        User user = FileDBHelper.loginUser(TEST_TA, "password123");
        assertNotNull(user, "Login should succeed with correct credentials");
        assertEquals(TEST_TA, user.getUsername());
        assertEquals("TA", user.getRole());
    }

    @Test @Order(5)
    @DisplayName("Login with incorrect password")
    void testLoginWrongPassword() {
        User user = FileDBHelper.loginUser(TEST_TA, "wrongpass");
        assertNull(user, "Login should fail with incorrect password");
    }

    @Test @Order(6)
    @DisplayName("Login with non-existent username")
    void testLoginNonExistent() {
        User user = FileDBHelper.loginUser("ghost_user", "anything");
        assertNull(user, "Login should fail for non-existent user");
    }

    @Test @Order(7)
    @DisplayName("Get all users returns the list")
    void testGetAllUsers() {
        List<User> users = FileDBHelper.getAllUsers();
        assertNotNull(users, "User list should not be null");
        assertTrue(users.size() > 0, "User list should contain registered users");
    }

    // ── Job Operations Tests ──

    @Test @Order(8)
    @DisplayName("Add a new job posting")
    void testAddJob() {
        int beforeCount = FileDBHelper.getAllJobs().size();
        Job job = new Job("Test Job", "Java, Python, SQL", TEST_USER);
        FileDBHelper.addJob(job);
        int afterCount = FileDBHelper.getAllJobs().size();
        assertEquals(beforeCount + 1, afterCount, "Job count should increase by 1");
        assertNotNull(job.getJobId(), "Job ID should be auto-generated");
        assertEquals("Open", job.getStatus(), "New jobs should default to Open");
    }

    @Test @Order(9)
    @DisplayName("Get all jobs returns non-null list")
    void testGetAllJobs() {
        List<Job> jobs = FileDBHelper.getAllJobs();
        assertNotNull(jobs, "Job list should not be null");
        assertTrue(jobs.size() > 0, "Should have at least 1 job");
    }

    // ── Application Operations Tests ──

    @Test @Order(10)
    @DisplayName("Submit a new application")
    void testAddApplication() {
        Application app = new Application(
                java.util.UUID.randomUUID().toString(),
                "Test Job",
                TEST_TA,
                new java.util.Date().toString(),
                "Pending"
        );
        boolean success = FileDBHelper.addApplication(app);
        assertTrue(success, "Application should be added successfully");
    }

    @Test @Order(11)
    @DisplayName("Check duplicate application detection")
    void testHasApplied() {
        // Test that the system can detect whether a TA has already applied to a job
        boolean applied = FileDBHelper.hasApplied("NonExistentJob", TEST_TA);
        assertFalse(applied, "Should return false for non-existent application");
    }

    @Test @Order(12)
    @DisplayName("Get applications by applicant")
    void testGetApplicationsByApplicant() {
        List<Application> apps = FileDBHelper.getApplicationsByApplicant(TEST_TA);
        assertNotNull(apps, "Application list should not be null");
        for (Application app : apps) {
            assertEquals(TEST_TA, app.getApplicant(), "All apps should belong to test TA");
        }
    }

    // ── TA Profile Operations Tests ──

    @Test @Order(13)
    @DisplayName("Save a new TA profile")
    void testSaveTAProfile() {
        TAProfile profile = new TAProfile(TEST_TA, 
                "Java, Python, Machine Learning, SQL", 
                "GPA 3.8", 
                "cv_test_user.pdf");
        boolean success = FileDBHelper.saveTAProfile(profile);
        assertTrue(success, "Profile should be saved successfully");
    }

    @Test @Order(14)
    @DisplayName("Retrieve saved TA profile")
    void testGetTAProfile() {
        TAProfile profile = FileDBHelper.getTAProfile(TEST_TA);
        assertNotNull(profile, "Profile should be retrievable");
        assertEquals(TEST_TA, profile.getUsername());
        assertTrue(profile.getSkills().toLowerCase().contains("java"), 
                   "Skills should contain Java");
    }

    @Test @Order(15)
    @DisplayName("Update existing TA profile")
    void testUpdateTAProfile() {
        TAProfile updated = new TAProfile(TEST_TA,
                "C++, Docker, Linux, Git",
                "GPA 3.9",
                "cv_updated.pdf");
        boolean success = FileDBHelper.saveTAProfile(updated);
        assertTrue(success, "Profile update should succeed");
        
        TAProfile retrieved = FileDBHelper.getTAProfile(TEST_TA);
        assertTrue(retrieved.getSkills().toLowerCase().contains("c++"),
                   "Updated skills should contain C++");
    }

    @Test @Order(16)
    @DisplayName("Get profile for non-existent user returns null")
    void testGetNonExistentProfile() {
        TAProfile profile = FileDBHelper.getTAProfile("no_such_user");
        assertNull(profile, "Should return null for non-existent profile");
    }

    // ── Application Status Update Tests ──

    @Test @Order(17)
    @DisplayName("Update application status to Accepted")
    void testUpdateApplicationStatus() {
        List<Application> apps = FileDBHelper.getAllApplications();
        if (!apps.isEmpty()) {
            Application app = apps.get(0);
            boolean success = FileDBHelper.updateApplicationStatus(app.getId(), "Accepted");
            assertTrue(success, "Status update should succeed");
        }
    }
}
