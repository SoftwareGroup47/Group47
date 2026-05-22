<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.example.model.TAProfile" %>
<%@ page import="org.example.util.AIMatchService" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    String username = (session != null) ? (String) session.getAttribute("username") : null;
    int workload = 0, accepted = 0, pending = 0;
    String advice = "";
    if (username != null) {
        workload = AIMatchService.getTAWorkload(username);
        accepted = AIMatchService.getAcceptedJobCount(username);
        pending = AIMatchService.getPendingJobCount(username);
        advice = AIMatchService.getWorkloadAdvice(workload, accepted);
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(600px, 96%); margin: 0 auto; padding: 40px 0; }
        .profile-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        h1 { margin: 0 0 24px; font-size: 1.8rem; color: #1a2a4f; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: 600; color: #4f5f73; }
        input, textarea { width: 100%; padding: 12px 16px; border: 1px solid #cbd3db; border-radius: 10px; font-size: 1rem; box-sizing: border-box; }
        textarea { height: 120px; resize: vertical; }
        input:focus, textarea:focus { outline: none; border-color: #1c6ff1; box-shadow: 0 0 0 3px rgba(28, 111, 241, 0.1); }
        .btn { width: 100%; padding: 14px; background: #1c6ff1; color: #fff; border: none; border-radius: 10px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: background 0.2s ease; }
        .btn:hover { background: #144fc1; }
        .nav-link { margin-top: 20px; text-align: center; }
        .nav-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; }
        .nav-link a:hover { text-decoration: underline; }
        .workload-panel { background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); border-radius: 12px; padding: 20px; margin-bottom: 24px; color: #fff; }
        .workload-header { display: flex; align-items: center; gap: 10px; margin-bottom: 16px; font-size: 1.2rem; font-weight: 600; }
        .workload-header span { font-size: 1.5rem; }
        .workload-stats { display: grid; grid-template-columns: repeat(3, 1fr); gap: 12px; margin-bottom: 16px; }
        .workload-stat { background: rgba(255,255,255,0.2); padding: 14px; border-radius: 10px; text-align: center; }
        .workload-stat-value { font-size: 1.8rem; font-weight: 700; }
        .workload-stat-label { font-size: 0.85rem; opacity: 0.9; }
        .workload-advice { background: rgba(255,255,255,0.15); padding: 12px 16px; border-radius: 10px; font-size: 0.95rem; }
        .workload-advice.warning { background: rgba(254, 215, 170, 0.3); border: 1px solid rgba(255,255,255,0.3); }
        .file-input { display: block; margin-bottom: 8px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="profile-box">
            <h1>My Profile</h1>

            <% if (username != null) { %>
            <div class="workload-panel">
                <div class="workload-header">
                    <span>Workload Analysis</span>
                </div>
                <div class="workload-stats">
                    <div class="workload-stat">
                        <div class="workload-stat-value"><%= workload %></div>
                        <div class="workload-stat-label">Total Applied</div>
                    </div>
                    <div class="workload-stat">
                        <div class="workload-stat-value"><%= accepted %></div>
                        <div class="workload-stat-label">Accepted</div>
                    </div>
                    <div class="workload-stat">
                        <div class="workload-stat-value"><%= pending %></div>
                        <div class="workload-stat-label">Pending</div>
                    </div>
                </div>
                <div class="workload-advice <%= (workload >= 3 || accepted >= 2) ? "warning" : "" %>">
                    <%= advice %>
                </div>
            </div>
            <% } %>

            <!-- Profile form with file upload support -->
            <form action="TaProfileServlet" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="skills">Skills (comma-separated)</label>
                    <textarea id="skills" name="skills" placeholder="e.g., Java, Python, Machine Learning, SQL, Communication"
                              required><%= request.getAttribute("profile") != null ? ((TAProfile) request.getAttribute("profile")).getSkills() : "" %></textarea>
                </div>
                <div class="form-group">
                    <label for="grades">Academic Grades</label>
                    <input type="text" id="grades" name="grades" placeholder="e.g., GPA 3.8/4.0, Top 10%"
                           value="<%= request.getAttribute("profile") != null ? ((TAProfile) request.getAttribute("profile")).getGrades() : "" %>" required>
                </div>
                <div class="form-group">
                    <label for="cvFile">Upload CV (PDF, DOC, DOCX)</label>
                    <input type="file" id="cvFile" name="cvFile" accept=".pdf,.doc,.docx" class="file-input">
                    <input type="hidden" id="cvPath" name="cvPath"
                           value="<%= request.getAttribute("profile") != null ? ((TAProfile) request.getAttribute("profile")).getCvPath() : "" %>">
                </div>
                <button type="submit" class="btn">Save Profile</button>
                <div class="nav-link">
                    <a href="job_hall.jsp">Job Hall</a> |
                    <a href="TAApplicationStatusServlet">My Applications</a> |
                    <a href="login.jsp">Logout</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
