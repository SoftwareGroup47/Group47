package org.example.servlet;

import org.example.model.TAProfile;
import org.example.util.FileDBHelper;
import org.example.util.FileUploadHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handles TA profile creation and updates.
 * <p>
 * GET: loads and displays the TA's existing profile.
 * POST: saves or updates the TA's profile including CV file upload.
 */
public class TaProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");

        // Role check: only TAs can access this
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"TA".equals(role)) {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('Only TAs can access the profile page'); window.location.href='login.jsp';</script>");
            return;
        }

        // Load existing profile and forward to JSP
        String username = (String) session.getAttribute("username");
        TAProfile profile = FileDBHelper.getTAProfile(username);
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("ta_profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Role check
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute("role");
        if (!"TA".equals(role)) {
            out.println("<script>alert('Only TAs can save profiles'); window.location.href='login.jsp';</script>");
            return;
        }

        // Extract form data
        String username = (String) session.getAttribute("username");
        String skills = request.getParameter("skills");
        String grades = request.getParameter("grades");

        // Handle CV file upload
        String cvPath = "";
        try {
            Part cvPart = request.getPart("cvFile");
            if (cvPart != null && cvPart.getSize() > 0) {
                String uploadedPath = FileUploadHelper.handleFileUpload(cvPart);
                if (uploadedPath != null) {
                    cvPath = uploadedPath;
                }
            } else {
                // Fallback to text field input if no file uploaded
                cvPath = request.getParameter("cvPath");
            }
        } catch (Exception e) {
            cvPath = request.getParameter("cvPath");
        }

        // Validate required fields
        if (skills == null || skills.isEmpty() || grades == null || grades.isEmpty()) {
            out.println("<script>alert('Please fill in both skills and grades'); window.location.href='ta_profile.jsp';</script>");
            return;
        }

        // Save profile
        TAProfile profile = new TAProfile(username, skills, grades, cvPath);
        boolean success = FileDBHelper.saveTAProfile(profile);

        if (success) {
            out.println("<script>alert('Profile saved successfully!'); window.location.href='ta_profile.jsp';</script>");
        } else {
            out.println("<script>alert('Failed to save profile. Please try again.'); window.location.href='ta_profile.jsp';</script>");
        }
    }
}
