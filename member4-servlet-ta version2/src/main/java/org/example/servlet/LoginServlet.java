package org.example.servlet;

import org.example.model.User;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取表单数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 验证输入
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            out.println("<script>alert('请填写用户名和密码'); window.location.href='login.jsp';</script>");
            return;
        }

        // 登录验证
        User user = FileDBHelper.loginUser(username, password);

        if (user != null) {
            // 登录成功，创建会话
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // 根据角色跳转到不同页面
            if ("MO".equals(user.getRole())) {
                response.sendRedirect("mo/post_job.jsp");
            } else if ("TA".equals(user.getRole())) {
                response.sendRedirect("job_hall.jsp");
            }
        } else {
            out.println("<script>alert('用户名或密码错误'); window.location.href='login.jsp';</script>");
        }
    }
}
