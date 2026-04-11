package test;

import org.example.model.User;
import org.example.model.Job;
import org.example.model.Application;
import org.example.util.FileDBHelper;

import java.util.List;

public class ProjectTest {
    public static void main(String[] args) {
        System.out.println("========== BUPT TA Recruitment System - 功能测试 ==========");
        
        // 测试1: 用户注册
        System.out.println("\n[测试1] 用户注册功能");
        User testUserTA = new User("testTA", "123456", "TA");
        boolean registerTA = FileDBHelper.registerUser(testUserTA);
        System.out.println("TA用户注册: " + (registerTA ? "成功" : "失败(用户名已存在)"));
        
        User testUserMO = new User("testMO", "654321", "MO");
        boolean registerMO = FileDBHelper.registerUser(testUserMO);
        System.out.println("MO用户注册: " + (registerMO ? "成功" : "失败(用户名已存在)"));
        
        // 测试2: 用户登录
        System.out.println("\n[测试2] 用户登录功能");
        User loginTA = FileDBHelper.loginUser("testTA", "123456");
        System.out.println("TA用户登录: " + (loginTA != null ? "成功，角色: " + loginTA.getRole() : "失败"));
        
        User loginMO = FileDBHelper.loginUser("testMO", "654321");
        System.out.println("MO用户登录: " + (loginMO != null ? "成功，角色: " + loginMO.getRole() : "失败"));
        
        User wrongPwd = FileDBHelper.loginUser("testTA", "wrongpwd");
        System.out.println("密码错误登录: " + (wrongPwd != null ? "成功" : "失败(正确)"));
        
        // 测试3: 岗位发布
        System.out.println("\n[测试3] 岗位发布功能");
        Job testJob = new Job("软件工程助教", "Java熟练，了解敏捷开发", "testMO");
        FileDBHelper.addJob(testJob);
        System.out.println("岗位发布: 成功，岗位ID: " + testJob.getJobId());
        
        // 测试4: 岗位查询
        System.out.println("\n[测试4] 岗位查询功能");
        List<Job> jobs = FileDBHelper.getAllJobs();
        System.out.println("查询到岗位数量: " + jobs.size());
        for (Job job : jobs) {
            System.out.println("  - " + job.getJobName() + " (发布者: " + job.getMoName() + ")");
        }
        
        // 测试5: 岗位申请
        System.out.println("\n[测试5] 岗位申请功能");
        Application application = new Application(
            java.util.UUID.randomUUID().toString(), 
            testJob.getJobName(), 
            "testTA", 
            new java.util.Date().toString(), 
            "Pending"
        );
        boolean applySuccess = FileDBHelper.addApplication(application);
        System.out.println("岗位申请: " + (applySuccess ? "成功" : "失败"));
        
        // 测试6: 申请查询
        System.out.println("\n[测试6] 申请查询功能");
        List<Application> applications = FileDBHelper.getAllApplications();
        System.out.println("查询到申请数量: " + applications.size());
        for (Application app : applications) {
            System.out.println("  - " + app.getApplicant() + " 申请了 " + app.getJobName() + " (" + app.getStatus() + ")");
        }
        
        // 测试7: 重复申请检测
        System.out.println("\n[测试7] 重复申请检测");
        boolean hasApplied = FileDBHelper.hasApplied(testJob.getJobId(), "testTA");
        System.out.println("重复申请检测: " + (hasApplied ? "已申请(正确)" : "未申请"));
        
        System.out.println("\n========== 测试完成 ==========");
    }
}