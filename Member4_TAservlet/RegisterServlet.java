package org.example.servlet;

import org.example.model.User;
import org.example.util.FileDBHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Handles user registration form submissions.
 * Validates input, checks for duplicate usernames, hashes passwords,
 * and redirects to the login page on success.
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Extract form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Validate required fields
        if (username == null || username.isEmpty() ||
            password == null || password.isEmpty() ||
            role == null || role.isEmpty()) {
            out.println("<script>alert('Please fill in all required fields'); window.location.href='register.jsp';</script>");
            return;
        }

        // Create and register the user
        User user = new User(username, password, role);
        boolean success = FileDBHelper.registerUser(user);

        if (success) {
            out.println("<script>alert('Registration successful! Please log in.'); window.location.href='login.jsp';</script>");
        } else {
            out.println("<script>alert('Username already exists. Please choose another.'); window.location.href='register.jsp';</script>");
        }
    }
}
