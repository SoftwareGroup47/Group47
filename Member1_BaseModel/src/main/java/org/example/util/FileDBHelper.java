package org.example.util;

import org.example.model.Application;
import org.example.model.Job;
import org.example.model.TAProfile;
import org.example.model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * File-based data storage helper for the TA recruitment system.
 * <p>
 * Uses pipe-delimited text files stored in the user's home directory
 * ({@code ~/tarecruit_data/}) for all persistent data. Data is loaded
 * into memory on startup and written back to disk on every mutation.
 * <p>
 * <b>Thread safety:</b> All write operations are synchronized on the class.
 * <p>
 * <b>Password security:</b> Passwords are stored as SHA-256 hashes, not plain text.
 * <p>
 * Storage files:
 * <ul>
 *   <li>{@code users.txt} — username|hashedPassword|role</li>
 *   <li>{@code jobs.txt} — jobId|jobName|requirements|moName</li>
 *   <li>{@code applications.txt} — id|jobName|applicant|applyTime|status</li>
 *   <li>{@code ta_profiles.txt} — username|skills|grades|cvPath</li>
 * </ul>
 */
public class FileDBHelper {
    private static final String DATA_DIR = System.getProperty("user.home") + File.separator + "tarecruit_data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.txt";
    private static final String JOBS_FILE = DATA_DIR + File.separator + "jobs.txt";
    private static final String APPLICATIONS_FILE = DATA_DIR + File.separator + "applications.txt";
    private static final String TA_PROFILES_FILE = DATA_DIR + File.separator + "ta_profiles.txt";

    // In-memory caches: loaded once at startup, written back on mutation
    private static List<User> userCache = new ArrayList<>();
    private static List<Job> jobCache = new ArrayList<>();
    private static List<Application> applicationCache = new ArrayList<>();
    private static List<TAProfile> taProfileCache = new ArrayList<>();

    // Static initializer: create data directory and load all data into memory
    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadDataIntoMemory();
    }

    // ──────────────────────────────────────────────
    // Core I/O Methods
    // ──────────────────────────────────────────────

    /** Loads all text-file data into in-memory lists at startup. */
    private static void loadDataIntoMemory() {
        loadUsers();
        loadJobs();
        loadApplications();
        loadTAProfiles();
    }

    private static void loadUsers() {
        File uFile = new File(USERS_FILE);
        if (uFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(uFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 3) {
                            userCache.add(new User(parts[0], parts[1], parts[2]));
                        }
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private static void loadJobs() {
        File jFile = new File(JOBS_FILE);
        if (jFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(jFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 4) {
                            Job job = new Job();
                            job.setJobId(parts[0]);
                            job.setJobName(parts[1]);
                            job.setRequirements(parts[2]);
                            job.setMoName(parts[3]);
                            job.setStatus("Open");
                            jobCache.add(job);
                        }
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private static void loadApplications() {
        File aFile = new File(APPLICATIONS_FILE);
        if (aFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(aFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 5) {
                            applicationCache.add(new Application(parts[0], parts[1], parts[2], parts[3], parts[4]));
                        }
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    private static void loadTAProfiles() {
        File tpFile = new File(TA_PROFILES_FILE);
        if (tpFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(tpFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 4) {
                            taProfileCache.add(new TAProfile(parts[0], parts[1], parts[2], parts[3]));
                        }
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    // ──────────────────────────────────────────────
    // Security: SHA-256 Password Hashing
    // ──────────────────────────────────────────────

    /**
     * Hashes a plain text password using SHA-256.
     * @param password the plain text password
     * @return hex-encoded SHA-256 hash string
     */
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    // ──────────────────────────────────────────────
    // User Operations
    // ──────────────────────────────────────────────

    /**
     * Registers a new user. Checks for duplicate usernames and hashes the password.
     * @param newUser the user to register (password should be plain text)
     * @return true if registration succeeded, false if username already exists
     */
    public static synchronized boolean registerUser(User newUser) {
        for (User u : userCache) {
            if (u.getUsername().equals(newUser.getUsername())) {
                return false;
            }
        }

        String hashedPwd = hashPassword(newUser.getPassword());
        newUser.setPassword(hashedPwd);
        userCache.add(newUser);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : userCache) {
                writer.write(user.getUsername() + "|" + user.getPassword() + "|" + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }

        return true;
    }

    /**
     * Authenticates a user by comparing hashed passwords.
     * @param username the username
     * @param password the plain text password
     * @return the User object if authentication succeeds, null otherwise
     */
    public static User loginUser(String username, String password) {
        String inputHashedPwd = hashPassword(password);

        for (User u : userCache) {
            if (u.getUsername().equals(username) && u.getPassword().equals(inputHashedPwd)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Checks if a username already exists in the system.
     * @param username the username to check
     * @return true if the user exists, false otherwise
     */
    public static boolean isUserExists(String username) {
        for (User u : userCache) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /** @return a copy of the user list (prevents external mutation) */
    public static List<User> getAllUsers() {
        return new ArrayList<>(userCache);
    }

    // ──────────────────────────────────────────────
    // Job Operations
    // ──────────────────────────────────────────────

    /**
     * Adds a new job posting and persists to disk.
     * @param newJob the job to add
     */
    public static synchronized void addJob(Job newJob) {
        jobCache.add(newJob);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOBS_FILE))) {
            for (Job job : jobCache) {
                writer.write(job.getJobId() + "|" + job.getJobName() + "|" + job.getRequirements() + "|" + job.getMoName());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    /** @return list of all jobs (live reference to in-memory cache) */
    public static List<Job> getAllJobs() {
        return jobCache;
    }

    /**
     * Updates the status of a job (e.g., closing a position).
     * @param jobId the job's UUID
     * @param status new status value
     * @return true if the job was found and updated
     */
    public static synchronized boolean updateJobStatus(String jobId, String status) {
        for (Job j : jobCache) {
            if (j.getJobId().equals(jobId)) {
                j.setStatus(status);
                persistJobs();
                return true;
            }
        }
        return false;
    }

    /** Writes the in-memory job list back to the jobs file. */
    private static void persistJobs() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOBS_FILE))) {
            for (Job job : jobCache) {
                writer.write(job.getJobId() + "|" + job.getJobName() + "|" + job.getRequirements() + "|" + job.getMoName());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    // ──────────────────────────────────────────────
    // Application Operations
    // ──────────────────────────────────────────────

    /**
     * Submits a new application and persists to disk.
     * @param application the application to submit
     * @return true on success
     */
    public static synchronized boolean addApplication(Application application) {
        applicationCache.add(application);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPLICATIONS_FILE))) {
            for (Application app : applicationCache) {
                writer.write(app.getId() + "|" + app.getJobName() + "|" + app.getApplicant() + "|" + app.getApplyTime() + "|" + app.getStatus());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); return false; }
        return true;
    }

    /** @return list of all applications */
    public static List<Application> getAllApplications() {
        return applicationCache;
    }

    /**
     * Checks whether an applicant has already applied to a specific job.
     * @param jobId the job's UUID
     * @param applicant the applicant's username
     * @return true if a duplicate application exists
     */
    public static boolean hasApplied(String jobId, String applicant) {
        for (Application app : applicationCache) {
            if (app.getJobName().equals(jobId) && app.getApplicant().equals(applicant)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates an application's status (accept/reject).
     * @param applicationId the application's UUID
     * @param status new status ("Accepted" or "Rejected")
     * @return true if the application was found and updated
     */
    public static synchronized boolean updateApplicationStatus(String applicationId, String status) {
        for (Application app : applicationCache) {
            if (app.getId().equals(applicationId)) {
                app.setStatus(status);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPLICATIONS_FILE))) {
                    for (Application a : applicationCache) {
                        writer.write(a.getId() + "|" + a.getJobName() + "|" + a.getApplicant() + "|" + a.getApplyTime() + "|" + a.getStatus());
                        writer.newLine();
                    }
                } catch (IOException e) { e.printStackTrace(); return false; }
                return true;
            }
        }
        return false;
    }

    /**
     * Gets all applications for a specific TA applicant.
     * @param username the TA's username
     * @return list of applications matching the username
     */
    public static List<Application> getApplicationsByApplicant(String username) {
        List<Application> result = new ArrayList<>();
        for (Application app : applicationCache) {
            if (app.getApplicant().equals(username)) {
                result.add(app);
            }
        }
        return result;
    }

    // ──────────────────────────────────────────────
    // TA Profile Operations
    // ──────────────────────────────────────────────

    /**
     * Saves or updates a TA profile. If a profile for this username already
     * exists, it is replaced; otherwise a new one is created.
     * @param profile the profile to save
     * @return true on success
     */
    public static synchronized boolean saveTAProfile(TAProfile profile) {
        // Check if profile already exists (update case)
        for (int i = 0; i < taProfileCache.size(); i++) {
            if (taProfileCache.get(i).getUsername().equals(profile.getUsername())) {
                taProfileCache.set(i, profile);
                persistTAProfiles();
                return true;
            }
        }
        // New profile (insert case)
        taProfileCache.add(profile);
        persistTAProfiles();
        return true;
    }

    /**
     * Retrieves a TA's profile by username.
     * @param username the TA's username
     * @return the TAProfile or null if not found
     */
    public static TAProfile getTAProfile(String username) {
        for (TAProfile profile : taProfileCache) {
            if (profile.getUsername().equals(username)) {
                return profile;
            }
        }
        return null;
    }

    /** Writes the in-memory TA profile list back to the profiles file. */
    private static void persistTAProfiles() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TA_PROFILES_FILE))) {
            for (TAProfile p : taProfileCache) {
                writer.write(p.getUsername() + "|" + p.getSkills() + "|" + p.getGrades() + "|" + p.getCvPath());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }
}
