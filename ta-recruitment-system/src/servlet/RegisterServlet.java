package servlet;

import model.User;
import util.FileDBHelper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取前端表单参数（和成员3页面字段对齐）
        String studentId = request.getParameter("studentId"); // 学号
        String username = request.getParameter("username"); // 姓名
        String password = request.getParameter("password"); // 密码
        String role = "TA"; // 固定为TA身份

        // 2. 读取已有用户，校验学号重复
        List<User> userList = FileDBHelper.readUsers();
        for (User u : userList) {
            if (u.getStudentId().equals(studentId)) {
                response.getWriter().write("学号已注册！");
                return;
            }
        }

        // 3. 新建用户对象
        User newUser = new User(studentId, username, role, password);
        userList.add(newUser);

        // 4. 保存到JSON文件（调用成员1工具类）
        FileDBHelper.saveUsers(userList);

        // 5. 注册成功，跳转到登录页
        response.sendRedirect("/login.jsp");
    }
}