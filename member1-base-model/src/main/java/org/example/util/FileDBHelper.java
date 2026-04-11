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

public class FileDBHelper {
    private static final String DATA_DIR = System.getProperty("user.home") + File.separator + "tarecruit_data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.txt";
    private static final String JOBS_FILE = DATA_DIR + File.separator + "jobs.txt";
    private static final String APPLICATIONS_FILE = DATA_DIR + File.separator + "applications.txt";
    private static final String TA_PROFILES_FILE = DATA_DIR + File.separator + "ta_profiles.txt";

    // 🌟 优化一：【内存缓存】启动时加载一次，告别疯狂读硬盘！
    private static List<User> userCache = new ArrayList<>();
    private static List<Job> jobCache = new ArrayList<>();
    private static List<Application> applicationCache = new ArrayList<>();
    private static List<TAProfile> taProfileCache = new ArrayList<>();

    // 静态代码块：系统刚启动时执行，建文件夹并把硬盘数据“吸”进内存
    static {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        loadDataIntoMemory(); // 启动时读取一次
    }

    // ================== 核心底层机制 ==================

    /**
     * 将硬盘里的文本数据一次性加载到内存 List 中
     */
    private static void loadDataIntoMemory() {
        // 加载用户
        loadUsers();
        // 加载岗位
        loadJobs();
        // 加载申请
        loadApplications();
        // 加载TA个人资料
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
                            User user = new User(parts[0], parts[1], parts[2]);
                            userCache.add(user);
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
                            Application application = new Application(parts[0], parts[1], parts[2], parts[3], parts[4]);
                            applicationCache.add(application);
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
                            TAProfile profile = new TAProfile(parts[0], parts[1], parts[2], parts[3]);
                            taProfileCache.add(profile);
                        }
                    }
                }
            } catch (IOException e) { e.printStackTrace(); }
        }
    }

    /**
     * 🌟 优化二：【密码散列加密】(SHA-256)
     * 把 "123456" 变成 "8d969eef6ecad3c29a3a629280e686cf..."
     */
    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // 将字节数组转为 16 进制的字符串
            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("系统缺少 SHA-256 算法", e);
        }
    }

    // ================== 业务逻辑区 ==================

    /**
     * 注册新用户
     */
    public static synchronized boolean registerUser(User newUser) {
        // 1. 直接在内存里查重，极其迅速！
        for (User u : userCache) {
            if (u.getUsername().equals(newUser.getUsername())) {
                return false; // 重名
            }
        }

        // 2. 将明文密码加密后，再存入对象
        String hashedPwd = hashPassword(newUser.getPassword());
        newUser.setPassword(hashedPwd);

        // 3. 存入内存缓存
        userCache.add(newUser);

        // 4. 将更新后的缓存全量写入硬盘（持久化）
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : userCache) {
                writer.write(user.getUsername() + "|" + user.getPassword() + "|" + user.getRole());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }

        return true;
    }

    /**
     * 登录验证
     */
    public static User loginUser(String username, String password) {
        // 1. 先把用户输入的密码也加密
        String inputHashedPwd = hashPassword(password);

        // 2. 直接在内存中极速比对！
        for (User u : userCache) {
            if (u.getUsername().equals(username) && u.getPassword().equals(inputHashedPwd)) {
                return u; // 匹配成功
            }
        }
        return null;
    }

    // ---------------- 岗位相关 ----------------

    public static synchronized void addJob(Job newJob) {
        jobCache.add(newJob);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOBS_FILE))) {
            for (Job job : jobCache) {
                writer.write(job.getJobId() + "|" + job.getJobName() + "|" + job.getRequirements() + "|" + job.getMoName());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<Job> getAllJobs() {
        // 获取岗位时，直接返回内存数据，连 1 毫秒都不需要！
        return jobCache;
    }

    // ---------------- 申请相关 ----------------

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

    public static List<Application> getAllApplications() {
        return applicationCache;
    }

    public static boolean hasApplied(String jobId, String applicant) {
        for (Application app : applicationCache) {
            if (app.getJobName().equals(jobId) && app.getApplicant().equals(applicant)) {
                return true;
            }
        }
        return false;
    }

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

    // ---------------- TA个人资料相关 ----------------

    public static synchronized boolean saveTAProfile(TAProfile profile) {
        // 检查是否已存在
        for (int i = 0; i < taProfileCache.size(); i++) {
            if (taProfileCache.get(i).getUsername().equals(profile.getUsername())) {
                taProfileCache.set(i, profile);
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(TA_PROFILES_FILE))) {
                    for (TAProfile p : taProfileCache) {
                        writer.write(p.getUsername() + "|" + p.getSkills() + "|" + p.getGrades() + "|" + p.getCvPath());
                        writer.newLine();
                    }
                } catch (IOException e) { e.printStackTrace(); return false; }
                return true;
            }
        }
        // 不存在则添加
        taProfileCache.add(profile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TA_PROFILES_FILE))) {
            for (TAProfile p : taProfileCache) {
                writer.write(p.getUsername() + "|" + p.getSkills() + "|" + p.getGrades() + "|" + p.getCvPath());
                writer.newLine();
            }
        } catch (IOException e) { e.printStackTrace(); return false; }
        return true;
    }

    public static TAProfile getTAProfile(String username) {
        for (TAProfile profile : taProfileCache) {
            if (profile.getUsername().equals(username)) {
                return profile;
            }
        }
        return null;
    }

    /**
     * 探测账号是否存在
     * @return true 存在，false 不存在
     */
    public static boolean isUserExists(String username) {
        for (User u : userCache) {
            if (u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}