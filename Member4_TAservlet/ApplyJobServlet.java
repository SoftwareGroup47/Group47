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

/**
 * Handles TA job application submissions.
 * Validates the TA role, checks for duplicate applications,
 * and creates a new application record with "Pending" status.
 */
public class ApplyJobServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Role check: only TAs can apply
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"TA".equals(role)) {
            out.println("<script>alert('Only TAs can apply for jobs'); window.location.href='login.jsp';</script>");
            return;
        }

        // Extract form data
        String jobId = request.getParameter("jobId");
        String jobName = request.getParameter("jobName");
        String applicant = (String) session.getAttribute("username");
        String applyTime = new Date().toString();
        String status = "Pending";

        // Validate input
        if (jobId == null || jobId.isEmpty() || jobName == null || jobName.isEmpty()) {
            out.println("<script>alert('Incomplete job information'); window.location.href='job_hall.jsp';</script>");
            return;
        }

        // Check for duplicate applications
        if (FileDBHelper.hasApplied(jobId, applicant)) {
            out.println("<script>alert('You have already applied for this position'); window.location.href='job_hall.jsp';</script>");
            return;
        }

        // Create and save the application
        Application application = new Application(
                UUID.randomUUID().toString(), jobName, applicant, applyTime, status);
        boolean success = FileDBHelper.addApplication(application);

        if (success) {
            out.println("<script>alert('Application submitted successfully!'); window.location.href='job_hall.jsp';</script>");
        } else {
            out.println("<script>alert('Failed to submit application. Please try again.'); window.location.href='job_hall.jsp';</script>");
        }
    }
}
