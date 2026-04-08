package org.example.model;

public class User {
    private String username;
    private String password;
    private String role; // 角色："TA" (助教) 或 "MO" (模块负责人/老师)

    // 空构造函数（这个必须有，Gson 读取 JSON 时需要用到）
    public User() {}

    // 带参数的构造函数（方便我们在代码里一键创建新用户）
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // --- 下面全是 Getters 和 Setters（获取和设置数据的方法） ---
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
