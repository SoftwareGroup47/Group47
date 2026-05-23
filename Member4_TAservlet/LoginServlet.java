package org.example.servlet;

import org.example.model.User;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handles user login form submissions (for TA and MO roles).
 * Authenticates credentials using SHA-256 hashed password comparison,
 * creates a session, and redirects users based on their role.
 * <p>
 * Role-based redirect:
 * <ul>
 *   <li>TA (Teaching Assistant) → job_hall.jsp</li>
 *   <li>MO (Module Organizer) → mo/post_job.jsp</li>
 * </ul>
 * <p>
 * Note: Administrator login is handled by {@link AdminLoginServlet}
 * via the dedicated admin login page ({@code admin_login.jsp}).
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Extract form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate required fields
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty()) {
            out.println("<script>alert('Please enter both username and password'); window.location.href='login.jsp';</script>");
            return;
        }

        // Authenticate user
        User user = FileDBHelper.loginUser(username, password);

        if (user != null) {
            // Create session and store user attributes
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            // Role-based redirect
            if ("MO".equals(user.getRole())) {
                response.sendRedirect("mo/post_job.jsp");
            } else if ("TA".equals(user.getRole())) {
                response.sendRedirect("job_hall.jsp");
            }
        } else {
            out.println("<script>alert('Invalid username or password'); window.location.href='login.jsp';</script>");
        }
    }
}
