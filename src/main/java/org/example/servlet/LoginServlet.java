package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.util.FileDBHelper;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String u = request.getParameter("username");
        String p = request.getParameter("password");

        // 🌟【第一重判定】：完全契合你的新需求！检查账号是否压根就不存在？
        if (!FileDBHelper.isUserExists(u)) {
            // 连账号都没有，直接打发去注册页，并在网址后面挂上专属暗号
            response.sendRedirect("register.jsp?msg=need_register");
            return; // 拦截结束，不再往下走
        }

        // 🌟【第二重判定】：账号存在，核对密码是否正确
        User loginUser = FileDBHelper.loginUser(u, p);

        if (loginUser != null) {
            // 🎯 密码正确，登录成功！发通行证，进大厅
            HttpSession session = request.getSession();
            session.setAttribute("loggedInUser", loginUser);
            response.sendRedirect("job_hall.jsp");
        } else {
            // 🚫 账号虽然存在，但是密码填错了
            response.sendRedirect("login.jsp?error=wrong_pwd");
        }
    }
}
