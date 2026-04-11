package org.example.model;

import java.util.UUID;

public class Job {
    private String jobId;       // 唯一ID，防止两门课名字一样
    private String jobName;     // 岗位名
    private String requirements;// 岗位要求
    private String moName;
    private String status;      // 状态："Open"或 "Closed"

    public Job() {}

    public Job(String jobName, String requirements, String moName) {
        this.jobId = UUID.randomUUID().toString(); // 自动生成一串乱码作为唯一ID
        this.jobName = jobName;
        this.requirements = requirements;
        this.moName = moName;
        this.status = "Open"; // 新发布的岗位默认是开放的
    }

    // --- Getters 和 Setters ---
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
