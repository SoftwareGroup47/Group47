package org.example.servlet;

import org.example.model.User;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // 验证输入
        if (username == null || username.isEmpty() || password == null || password.isEmpty() || role == null || role.isEmpty()) {
            out.println("<script>alert('请填写所有必填字段'); window.location.href='register.jsp';</script>");
            return;
        }

        // 创建用户对象
        User user = new User(username, password, role);

        // 注册用户
        boolean success = FileDBHelper.registerUser(user);

        if (success) {
            out.println("<script>alert('注册成功！'); window.location.href='login.jsp';</script>");
        } else {
            out.println("<script>alert('用户名已存在，请重新选择'); window.location.href='register.jsp';</script>");
        }
    }
}
