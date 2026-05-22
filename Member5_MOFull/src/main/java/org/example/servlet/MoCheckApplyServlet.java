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

/**
 * Handles MO review of TA job applications.
 * <p>
 * GET: lists all applications for the MO to review.
 * POST: accepts or rejects an application (updates status to "Accepted" or "Rejected").
 */
public class MoCheckApplyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // Role check: only MOs can view applications
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"MO".equals(role)) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Only MOs can review applications'); window.location.href='login.jsp';</script>");
            return;
        }

        // Load all applications and forward to review page
        List<Application> applications = FileDBHelper.getAllApplications();
        request.setAttribute("applications", applications);
        request.getRequestDispatcher("mo/check_apply.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Role check
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"MO".equals(role)) {
            out.println("<script>alert('Only MOs can process applications'); window.location.href='login.jsp';</script>");
            return;
        }

        // Process accept/reject action
        String applicationId = request.getParameter("applicationId");
        String action = request.getParameter("action");
        String newStatus = "accept".equals(action) ? "Accepted" : "Rejected";

        boolean success = FileDBHelper.updateApplicationStatus(applicationId, newStatus);

        if (success) {
            out.println("<script>alert('Application ' + '" + newStatus + "' successfully!'); window.location.href='MoCheckApplyServlet';</script>");
        } else {
            out.println("<script>alert('Failed to process application. Please try again.'); window.location.href='MoCheckApplyServlet';</script>");
        }
    }
}
