package org.example.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.model.Job;
import org.example.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class FileDBHelper {
    private static final String DATA_DIR = System.getProperty("user.home") + File.separator + "tarecruit_data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.json";
    private static final String JOBS_FILE = DATA_DIR + File.separator + "jobs.json";
    private static final Gson gson = new Gson();

    // 🌟 优化一：【内存缓存】启动时加载一次，告别疯狂读硬盘！
    private static List<User> userCache = new ArrayList<>();
    private static List<Job> jobCache = new ArrayList<>();

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
     * 将硬盘里的 JSON 一次性加载到内存 List 中
     */
    private static void loadDataIntoMemory() {
        // 加载用户
        File uFile = new File(USERS_FILE);
        if (uFile.exists()) {
            try (Reader reader = new FileReader(uFile)) {
                Type listType = new TypeToken<ArrayList<User>>(){}.getType();
                List<User> loaded = gson.fromJson(reader, listType);
                if (loaded != null) userCache = loaded;
            } catch (IOException e) { e.printStackTrace(); }
        }

        // 加载岗位
        File jFile = new File(JOBS_FILE);
        if (jFile.exists()) {
            try (Reader reader = new FileReader(jFile)) {
                Type listType = new TypeToken<ArrayList<Job>>(){}.getType();
                List<Job> loaded = gson.fromJson(reader, listType);
                if (loaded != null) jobCache = loaded;
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
        try (Writer writer = new FileWriter(USERS_FILE)) {
            gson.toJson(userCache, writer);
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
        try (Writer writer = new FileWriter(JOBS_FILE)) {
            gson.toJson(jobCache, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static List<Job> getAllJobs() {
        // 获取岗位时，直接返回内存数据，连 1 毫秒都不需要！
        return jobCache;
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