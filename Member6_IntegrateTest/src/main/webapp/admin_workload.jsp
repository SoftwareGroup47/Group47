<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.servlet.AdminWorkloadServlet.TAWorkloadInfo" %>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || (!"MO".equals(role) && !"TA".equals(role))) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<TAWorkloadInfo> workloadList = (List<TAWorkloadInfo>) request.getAttribute("workloadList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Workload Dashboard - BUPT TA Recruitment System</title>
    <style>
        body { font-family: 'Segoe UI', Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(1100px, 96%); margin: 0 auto; padding: 40px 0; }
        h1 { margin: 0 0 8px; font-size: 2rem; color: #1a2a4f; }
        .subtitle { color: #6b7280; margin-bottom: 24px; }
        .dashboard-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px 16px; text-align: left; border-bottom: 1px solid #e5e7eb; }
        th { background: #f9fafb; font-weight: 600; color: #4f5f73; }
        tr:hover { background: #f9fafb; }
        .workload-bar { background: #e5e7eb; border-radius: 8px; height: 10px; overflow: hidden; }
        .workload-fill { height: 100%; border-radius: 8px; }
        .workload-low { background: #10b981; }
        .workload-moderate { background: #f59e0b; }
        .workload-high { background: #ef4444; }
        .advice-text { font-size: 0.85rem; color: #6b7280; font-style: italic; }
        .nav-links { text-align: center; margin-top: 24px; }
        .nav-links a { color: #1c6ff1; text-decoration: none; font-weight: 600; margin: 0 12px; }
        .nav-links a:hover { text-decoration: underline; }
        .empty-state { padding: 40px; text-align: center; color: #6b7280; background: #f9fafb; border-radius: 12px; border: 1px dashed #e5e7eb; }
        .profile-warning { color: #dc2626; font-weight: 600; }
    </style>
</head>
<body>
    <div class="container">
        <h1>TA Workload Dashboard</h1>
        <p class="subtitle">Comprehensive workload overview for all Teaching Assistants</p>
        <div class="dashboard-box">
            <% if (workloadList != null && !workloadList.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>TA Username</th>
                        <th>Total Applied</th>
                        <th>Accepted</th>
                        <th>Pending</th>
                        <th>Workload Level</th>
                        <th>Skills</th>
                        <th>Advice</th>
                    </tr>
                </thead>
                <tbody>
                <%
                    for (TAWorkloadInfo info : workloadList) {
                        int maxWorkload = Math.max(info.workload, 1);
                        int barPercent = Math.min(100, (info.workload * 100) / maxWorkload);
                        String barClass = info.workload >= 5 ? "workload-high" :
                                         info.workload >= 3 ? "workload-moderate" : "workload-low";
                %>
                    <tr>
                        <td><strong><%= info.username %></strong></td>
                        <td><%= info.workload %></td>
                        <td><%= info.accepted %></td>
                        <td><%= info.pending %></td>
                        <td>
                            <div class="workload-bar">
                                <div class="workload-fill <%= barClass %>" style="width: <%= barPercent %>%;"></div>
                            </div>
                        </td>
                        <td>
                            <% if (info.hasProfile) { %>
                                <%= info.skills.length() > 40 ? info.skills.substring(0, 40) + "..." : info.skills %>
                            <% } else { %>
                                <span class="profile-warning">No Profile</span>
                            <% } %>
                        </td>
                        <td><span class="advice-text"><%= info.advice %></span></td>
                    </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <% } else { %>
                <div class="empty-state">
                    <p>No TA users registered yet. TAs must create profiles for workload data to appear.</p>
                </div>
            <% } %>
            <div class="nav-links">
                <a href="mo/post_job.jsp">Post Job</a> |
                <a href="MoCheckApplyServlet">Review Applications</a> |
                <a href="login.jsp">Logout</a>
            </div>
        </div>
    </div>
</body>
</html>
