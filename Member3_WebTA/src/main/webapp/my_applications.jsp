<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Application" %>
<%
    String username = (session != null) ? (String) session.getAttribute("username") : null;
    String role = (String) session.getAttribute("role");
    if (username == null || !"TA".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Application> myApps = (List<Application>) request.getAttribute("applications");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Applications - BUPT TA Recruitment System</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(900px, 96%); margin: 0 auto; padding: 40px 0; }
        .header { margin-bottom: 32px; }
        h1 { margin: 0 0 8px; font-size: 2rem; color: #1a2a4f; }
        .subtitle { color: #6b7280; margin-bottom: 24px; }
        .status-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px 16px; text-align: left; border-bottom: 1px solid #e5e7eb; }
        th { background: #f9fafb; font-weight: 600; color: #4f5f73; }
        tr:hover { background: #f9fafb; }
        .badge { display: inline-block; padding: 4px 12px; border-radius: 999px; font-size: 0.85rem; font-weight: 600; }
        .badge-pending { background: #fef3c7; color: #92400e; }
        .badge-accepted { background: #d1fae5; color: #065f46; }
        .badge-rejected { background: #fee2e2; color: #991b1b; }
        .summary-stats { display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px; margin-bottom: 24px; }
        .stat-card { background: linear-gradient(135deg, #667eea, #764ba2); border-radius: 12px; padding: 16px; color: #fff; text-align: center; }
        .stat-value { font-size: 2rem; font-weight: 700; }
        .stat-label { font-size: 0.85rem; opacity: 0.9; }
        .empty-state { padding: 40px; text-align: center; color: #6b7280; background: #f9fafb; border-radius: 12px; border: 1px dashed #e5e7eb; }
        .nav-links { text-align: center; margin-top: 24px; }
        .nav-links a { color: #1c6ff1; text-decoration: none; font-weight: 600; margin: 0 12px; }
        .nav-links a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>My Applications</h1>
            <p class="subtitle">Track the status of all your TA position applications</p>
        </div>
        <div class="status-box">
            <div class="summary-stats">
                <%
                    int total = (myApps != null) ? myApps.size() : 0;
                    int accepted = 0, pending = 0, rejected = 0;
                    if (myApps != null) {
                        for (Application a : myApps) {
                            if ("Accepted".equals(a.getStatus())) accepted++;
                            else if ("Pending".equals(a.getStatus())) pending++;
                            else rejected++;
                        }
                    }
                %>
                <div class="stat-card" style="background: linear-gradient(135deg, #667eea, #764ba2);">
                    <div class="stat-value"><%= total %></div>
                    <div class="stat-label">Total Applications</div>
                </div>
                <div class="stat-card" style="background: linear-gradient(135deg, #10b981, #059669);">
                    <div class="stat-value"><%= accepted %></div>
                    <div class="stat-label">Accepted</div>
                </div>
                <div class="stat-card" style="background: linear-gradient(135deg, #f59e0b, #d97706);">
                    <div class="stat-value"><%= pending %></div>
                    <div class="stat-label">Pending</div>
                </div>
            </div>

            <% if (myApps != null && !myApps.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>Job Name</th>
                        <th>Applied On</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (Application app : myApps) {
                        String badgeClass = "badge-pending";
                        if ("Accepted".equals(app.getStatus())) badgeClass = "badge-accepted";
                        else if ("Rejected".equals(app.getStatus())) badgeClass = "badge-rejected";
                %>
                    <tr>
                        <td><strong><%= app.getJobName() %></strong></td>
                        <td><%= app.getApplyTime().length() > 16 ? app.getApplyTime().substring(0, 16) : app.getApplyTime() %></td>
                        <td><span class="badge <%= badgeClass %>"><%= app.getStatus() %></span></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <% } else { %>
                <div class="empty-state">
                    <p>You haven't submitted any applications yet.</p>
                    <p style="margin-top: 8px;">Browse available positions in the <a href="job_hall.jsp" style="color: #1c6ff1; font-weight: 600;">Job Hall</a>.</p>
                </div>
            <% } %>

            <div class="nav-links">
                <a href="job_hall.jsp">Job Hall</a> |
                <a href="ta_profile.jsp">My Profile</a> |
                <a href="login.jsp">Logout</a>
            </div>
        </div>
    </div>
</body>
</html>
