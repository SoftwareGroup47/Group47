package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.util.FileDBHelper;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String u = request.getParameter("username");
        String p = request.getParameter("password");
        String r = request.getParameter("role");

        // 🌟【第一重判定】：先拿去试试能不能“登录”
        // 这一步是专门为你提的那个前卫需求设计的！
        User existingUser = FileDBHelper.loginUser(u, p);

        if (existingUser != null) {
            // 🎯 触发了你的特殊机制：账号已存在，且密码正确！
            // 1. 给这个用户发一张“通行证”(Session)，代表他已登录
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", existingUser);

            // 2. 直接放行进入网站内部大厅，并在网址后面挂个信号 ?msg=auto_login
            response.sendRedirect("job_hall.jsp?msg=auto_login");
            return; // 极其重要：直接结束，不再往下执行注册逻辑
        }

        // 🌟【第二重判定】：如果登录失败，开始走正规的“注册”流程
        User newUser = new User(u, p, r);
        boolean registerSuccess = FileDBHelper.registerUser(newUser);

        if (registerSuccess) {
            // 新用户注册成功！为了体验连贯，我们也直接发通行证，让他免去重新登录的麻烦
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", newUser);

            // 跳转到大厅，挂上另一个信号 ?msg=welcome
            response.sendRedirect("job_hall.jsp?msg=welcome");
        } else {
            // 🚫【第三重判定】：登录失败（密码错），注册也失败（重名）
            // 结论：这是一个想要注册的新人，但他起的名字被别人用过了！
            response.sendRedirect("register.jsp?error=exist");
        }
    }
}