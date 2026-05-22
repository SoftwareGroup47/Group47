package org.example;

import org.example.model.Job;
import org.example.model.User;
import org.example.util.FileDBHelper;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        // 1. Simulate user registration data from frontend
        User testUser = new User("YiHeng", "123456", "MO");
        boolean isRegistered = FileDBHelper.registerUser(testUser);

        if (isRegistered) {
            System.out.println("User registered successfully: " + testUser.getUsername());
        } else {
            System.out.println("Welcome back");
        }

        // 2. Simulate the user posting a TA job
        Job testJob = new Job("Software Engineering Course TA", "Proficient in Java, familiar with Agile development", testUser.getUsername());
        FileDBHelper.addJob(testJob);
        System.out.println("Job posted successfully: " + testJob.getJobName());

        // 3. Print the physical storage location ("no-database" approach)
        String path = System.getProperty("user.home") + File.separator + "tarecruit_data";
        System.out.println(path);
    }
}