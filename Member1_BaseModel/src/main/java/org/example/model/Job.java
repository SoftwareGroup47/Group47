package org.example.model;

import java.util.UUID;

/**
 * Represents a Teaching Assistant job posting.
 * Each job has a unique UUID, a name, skill requirements, the posting MO,
 * and a status (Open or Closed).
 */
public class Job {
    private String jobId;        // Unique identifier (UUID)
    private String jobName;      // Title of the TA position
    private String requirements; // Required skills (comma/space separated)
    private String moName;       // Module Organizer who posted this job
    private String status;       // "Open" or "Closed"

    public Job() {}

    /**
     * Constructs a new job with auto-generated UUID and Open status.
     * @param jobName the name/title of the TA position
     * @param requirements comma or space separated skill requirements
     * @param moName the Module Organizer posting this job
     */
    public Job(String jobName, String requirements, String moName) {
        this.jobId = UUID.randomUUID().toString();
        this.jobName = jobName;
        this.requirements = requirements;
        this.moName = moName;
        this.status = "Open";
    }

    // ── Getters and Setters ──

    public String getJobId() { return jobId; }
    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getJobName() { return jobName; }
    public void setJobName(String jobName) { this.jobName = jobName; }

    public String getRequirements() { return requirements; }
    public void setRequirements(String requirements) { this.requirements = requirements; }

    public String getMoName() { return moName; }
    public void setMoName(String moName) { this.moName = moName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
