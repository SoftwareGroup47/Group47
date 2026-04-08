package servlet;

import model.User;
import util.FileDBHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取参数
        String studentId = request.getParameter("studentId");
        String password = request.getParameter("password");

        // 2. 读取用户列表校验
        List<User> userList = FileDBHelper.readUsers();
        User loginUser = null;
        for (User u : userList) {
            if (u.getStudentId().equals(studentId) && u.getPassword().equals(password)) {
                loginUser = u;
                break;
            }
        }

        if (loginUser == null) {
            response.getWriter().write("账号或密码错误");
            return;
        }

        // 3. 校验身份必须是TA
        if (!"TA".equals(loginUser.getRole())) {
            response.getWriter().write("非TA用户，无法登录");
            return;
        }

        // 4. 登录成功，保存用户到Session
        HttpSession session = request.getSession();
        session.setAttribute("loginUser", loginUser);

        // 5. 跳转到TA档案页
        response.sendRedirect("/ta/ta_profile.jsp");
    }
}