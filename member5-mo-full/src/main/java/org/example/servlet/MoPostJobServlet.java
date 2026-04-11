package org.example.servlet;

import org.example.model.Job;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class MoPostJobServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 检查用户角色
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"MO".equals(role)) {
            out.println("<script>alert('只有MO可以发布岗位'); window.location.href='login.jsp';</script>");
            return;
        }

        // 获取表单数据
        String jobName = request.getParameter("jobName");
        String requirements = request.getParameter("requirements");
        String moName = (String) session.getAttribute("username");

        // 验证输入
        if (jobName == null || jobName.isEmpty() || requirements == null || requirements.isEmpty()) {
            out.println("<script>alert('请填写所有必填字段'); window.location.href='mo/post_job.jsp';</script>");
            return;
        }

        // 创建岗位对象
        Job job = new Job(jobName, requirements, moName);

        // 发布岗位
        FileDBHelper.addJob(job);

        out.println("<script>alert('岗位发布成功！'); window.location.href='mo/post_job.jsp';</script>");
    }
}
