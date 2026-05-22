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

/**
 * Handles MO job posting form submissions.
 * Validates the MO role and creates a new job posting with auto-generated UUID.
 */
public class MoPostJobServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Role check: only MOs can post jobs
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"MO".equals(role)) {
            out.println("<script>alert('Only MOs can post jobs'); window.location.href='login.jsp';</script>");
            return;
        }

        // Extract form data
        String jobName = request.getParameter("jobName");
        String requirements = request.getParameter("requirements");
        String moName = (String) session.getAttribute("username");

        // Validate required fields
        if (jobName == null || jobName.isEmpty() ||
            requirements == null || requirements.isEmpty()) {
            out.println("<script>alert('Please fill in all required fields'); window.location.href='mo/post_job.jsp';</script>");
            return;
        }

        // Create and publish the job posting
        Job job = new Job(jobName, requirements, moName);
        FileDBHelper.addJob(job);

        out.println("<script>alert('Job posted successfully!'); window.location.href='mo/post_job.jsp';</script>");
    }
}
