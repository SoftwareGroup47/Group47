package org.example.model;

/**
 * Represents a TA's application for a job posting.
 * Tracks the job applied for, applicant username, application time, and status.
 */
public class Application {
    private String id;         // Unique UUID for this application
    private String jobName;    // Name of the job applied for
    private String applicant;  // Username of the TA applicant
    private String applyTime;  // Timestamp when the application was submitted
    private String status;     // "Pending", "Accepted", or "Rejected"

    public Application() {}

    /**
     * Constructs a new Application with all fields.
     * @param id unique application ID (UUID)
     * @param jobName name of the job
     * @param applicant applicant's username
     * @param applyTime application timestamp
     * @param status current status (Pending/Accepted/Rejected)
     */
    public Application(String id, String jobName, String applicant, String applyTime, String status) {
        this.id = id;
        this.jobName = jobName;
        this.applicant = applicant;
        this.applyTime = applyTime;
        this.status = status;
    }

    // ── Getters and Setters ──

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }

    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }

    public String getApplyTime() { return applyTime; }
    public void setApplyTime(String applyTime) { this.applyTime = applyTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
