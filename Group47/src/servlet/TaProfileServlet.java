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

@WebServlet("/ta/saveProfile")
public class TaProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. 获取登录用户（必须登录才能操作）
        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("/login.jsp");
            return;
        }

        // 2. 获取前端档案表单参数
        String realName = request.getParameter("realName");
        String skills = request.getParameter("skills");
        String intro = request.getParameter("intro");

        // 3. 更新用户信息
        List<User> userList = FileDBHelper.readUsers();
        for (User u : userList) {
            if (u.getStudentId().equals(loginUser.getStudentId())) {
                u.setUsername(realName); // 更新姓名
                // 可扩展：User类添加技能、简介字段后直接赋值
                // u.setSkills(skills);
                // u.setIntro(intro);
                break;
            }
        }

        // 4. 保存更新
        FileDBHelper.saveUsers(userList);
        response.getWriter().write("档案保存成功");
    }
}