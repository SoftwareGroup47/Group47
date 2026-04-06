package org.example.model;

public class Application {
    private String id;
    private String jobName;
    private String applicant;
    private String applyTime;
    private String status;
    
    public Application() {
    }
    
    public Application(String id, String jobName, String applicant, String applyTime, String status) {
        this.id = id;
        this.jobName = jobName;
        this.applicant = applicant;
        this.applyTime = applyTime;
        this.status = status;
    }
    
    // Getters and setters
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