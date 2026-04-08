package org.example.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Job;
import org.example.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileDBHelper {
    // 跨平台存储路径：系统会自动找到当前电脑的“用户主目录”建一个 tarecruit_data 文件夹
    private static final String DATA_DIR = System.getProperty("user.home") + File.separator + "tarecruit_data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.json";
    private static final String JOBS_FILE = DATA_DIR + File.separator + "jobs.json";

    private static final Gson gson = new Gson();

    // 静态代码块：系统启动时，自动检查并创建数据文件夹
    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("数据文件夹已创建: " + DATA_DIR);
        }
    }

    // ================== 用户 (User) 相关操作 ==================

    /**
     * 读取所有用户 (加锁防止读取时数据被篡改)
     */
    public static synchronized List<User> readUsers() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<User>>(){}.getType();
            List<User> users = gson.fromJson(reader, listType);
            return users == null ? new ArrayList<>() : users;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 写入用户列表到文件 (内部方法)
     */
    private static synchronized void writeUsers(List<User> users) {
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册新用户
     * @return true 注册成功，false 用户名已存在
     */
    public static synchronized boolean registerUser(User newUser) {
        List<User> users = readUsers();
        for (User u : users) {
            if (u.getUsername().equals(newUser.getUsername())) {
                return false; // 重名了
            }
        }
        users.add(newUser);
        writeUsers(users);
        return true;
    }

    /**
     * 登录验证
     * @return 成功返回 User 对象，失败返回 null
     */
    public static User loginUser(String username, String password) {
        List<User> users = readUsers();
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    // ================== 岗位 (Job) 相关操作 ==================

    /**
     * 读取所有岗位
     */
    public static synchronized List<Job> readJobs() {
        File file = new File(JOBS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Job>>(){}.getType();
            List<Job> jobs = gson.fromJson(reader, listType);
            return jobs == null ? new ArrayList<>() : jobs;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 写入岗位列表到文件 (内部方法)
     */
    private static synchronized void writeJobs(List<Job> jobs) {
        try (Writer writer = new FileWriter(JOBS_FILE)) {
            gson.toJson(jobs, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发布新岗位
     */
    public static synchronized void addJob(Job newJob) {
        List<Job> jobs = readJobs();
        jobs.add(newJob);
        writeJobs(jobs);
    }
}
