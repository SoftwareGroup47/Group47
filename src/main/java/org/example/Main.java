package org.example;

import org.example.model.Job;
import org.example.model.User;
import org.example.util.FileDBHelper;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        // 1. 模拟前端传来的用户注册数据
        User testUser = new User("YiHeng", "123456", "MO");
        boolean isRegistered = FileDBHelper.registerUser(testUser);

        if (isRegistered) {
            System.out.println("用户注册成功: " + testUser.getUsername());
        } else {
            System.out.println("欢迎回来");
        }

        // 2. 模拟该用户发布了一个助教岗位
        Job testJob = new Job("软件工程课程助教", "Java熟练，了解敏捷开发", testUser.getUsername());
        FileDBHelper.addJob(testJob);
        System.out.println("岗位发布成功: " + testJob.getJobName());

        // 3. 打印出你的“无数据库”物理存放位置
        String path = System.getProperty("user.home") + File.separator + "tarecruit_data";
        System.out.println(path);
    }
}