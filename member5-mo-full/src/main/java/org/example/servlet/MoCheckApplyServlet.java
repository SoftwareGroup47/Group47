package org.example.servlet;

import org.example.model.Application;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class MoCheckApplyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // 检查用户角色
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"MO".equals(role)) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('只有MO可以查看申请'); window.location.href='login.jsp';</script>");
            return;
        }

        // 获取申请列表
        List<Application> applications = FileDBHelper.getAllApplications();
        request.setAttribute("applications", applications);

        // 转发到审核页面
        request.getRequestDispatcher("mo/check_apply.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 检查用户角色
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"MO".equals(role)) {
            out.println("<script>alert('只有MO可以处理申请'); window.location.href='login.jsp';</script>");
            return;
        }

        // 获取表单数据
        String applicationId = request.getParameter("applicationId");
        String action = request.getParameter("action");

        // 处理申请
        boolean success = FileDBHelper.updateApplicationStatus(applicationId, action.equals("accept") ? "Accepted" : "Rejected");

        if (success) {
            out.println("<script>alert('操作成功！'); window.location.href='MoCheckApplyServlet';</script>");
        } else {
            out.println("<script>alert('操作失败，请重试'); window.location.href='MoCheckApplyServlet';</script>");
        }
    }
}
