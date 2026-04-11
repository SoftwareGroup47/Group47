package org.example.servlet;

import org.example.model.TAProfile;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class TaProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // 检查用户角色
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"TA".equals(role)) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('只有TA可以创建个人资料'); window.location.href='login.jsp';</script>");
            return;
        }

        // 获取TA个人资料
        String username = (String) session.getAttribute("username");
        TAProfile profile = FileDBHelper.getTAProfile(username);
        request.setAttribute("profile", profile);

        // 转发到个人资料页面
        request.getRequestDispatcher("ta_profile.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 检查用户角色
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"TA".equals(role)) {
            out.println("<script>alert('只有TA可以创建个人资料'); window.location.href='login.jsp';</script>");
            return;
        }

        // 获取表单数据
        String username = (String) session.getAttribute("username");
        String skills = request.getParameter("skills");
        String grades = request.getParameter("grades");
        String cvPath = request.getParameter("cvPath");

        // 验证输入
        if (skills == null || skills.isEmpty() || grades == null || grades.isEmpty()) {
            out.println("<script>alert('请填写所有必填字段'); window.location.href='ta_profile.jsp';</script>");
            return;
        }

        // 创建TA个人资料
        TAProfile profile = new TAProfile(username, skills, grades, cvPath);

        // 保存个人资料
        boolean success = FileDBHelper.saveTAProfile(profile);

        if (success) {
            out.println("<script>alert('个人资料保存成功！'); window.location.href='ta_profile.jsp';</script>");
        } else {
            out.println("<script>alert('个人资料保存失败，请重试'); window.location.href='ta_profile.jsp';</script>");
        }
    }
}
