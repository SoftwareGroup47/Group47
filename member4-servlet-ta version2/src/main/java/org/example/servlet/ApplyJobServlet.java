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
import java.util.Date;
import java.util.UUID;

public class ApplyJobServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 检查用户角色
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"TA".equals(role)) {
            out.println("<script>alert('只有TA可以申请岗位'); window.location.href='login.jsp';</script>");
            return;
        }

        // 获取表单数据
        String jobId = request.getParameter("jobId");
        String jobName = request.getParameter("jobName");
        String applicant = (String) session.getAttribute("username");
        String applyTime = new Date().toString();
        String status = "Pending";

        // 验证输入
        if (jobId == null || jobId.isEmpty() || jobName == null || jobName.isEmpty()) {
            out.println("<script>alert('岗位信息不完整'); window.location.href='job_hall.jsp';</script>");
            return;
        }

        // 检查是否已经申请过
        if (FileDBHelper.hasApplied(jobId, applicant)) {
            out.println("<script>alert('您已经申请过该岗位'); window.location.href='job_hall.jsp';</script>");
            return;
        }

        // 创建申请对象
        Application application = new Application(UUID.randomUUID().toString(), jobName, applicant, applyTime, status);

        // 保存申请
        boolean success = FileDBHelper.addApplication(application);

        if (success) {
            out.println("<script>alert('申请成功！'); window.location.href='job_hall.jsp';</script>");
        } else {
            out.println("<script>alert('申请失败，请重试'); window.location.href='job_hall.jsp';</script>");
        }
    }
}
