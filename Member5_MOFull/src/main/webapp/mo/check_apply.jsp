<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Application" %>
<%@ page import="org.example.model.Job" %>
<%@ page import="org.example.model.TAProfile" %>
<%@ page import="org.example.util.FileDBHelper" %>
<%@ page import="org.example.util.AIMatchService" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Review Applications - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(1000px, 96%); margin: 0 auto; padding: 40px 0; }
        .review-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        h1 { margin: 0 0 24px; font-size: 1.8rem; color: #1a2a4f; }
        table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #e5e7eb; }
        th { background-color: #f9fafb; font-weight: 600; color: #4f5f73; }
        tr:hover { background-color: #f9fafb; }
        .btn { padding: 8px 16px; border: none; border-radius: 8px; font-size: 0.9rem; font-weight: 600; cursor: pointer; transition: background 0.2s ease; }
        .btn-accept { background: #10b981; color: #fff; }
        .btn-accept:hover { background: #059669; }
        .btn-reject { background: #ef4444; color: #fff; }
        .btn-reject:hover { background: #dc2626; }
        .btn-ai { background: #8b5cf6; color: #fff; padding: 6px 12px; font-size: 0.8rem; }
        .btn-ai:hover { background: #7c3aed; }
        .empty-state { padding: 36px; text-align: center; background: #f9fafb; border: 1px dashed #e5e7eb; border-radius: 16px; color: #6b7280; }
        .nav-link { margin-top: 20px; text-align: center; }
        .nav-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; }
        .nav-link a:hover { text-decoration: underline; }
        .ai-analysis-panel { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); border-radius: 12px; padding: 16px; margin: 12px 0; color: #fff; }
        .ai-analysis-header { display: flex; align-items: center; gap: 8px; margin-bottom: 12px; font-weight: 600; font-size: 1.1rem; }
        .ai-score { font-size: 2rem; font-weight: 700; }
        .ai-stars { color: #fbbf24; margin: 4px 0; }
        .ai-bar { background: rgba(255,255,255,0.3); border-radius: 8px; height: 10px; margin: 10px 0; overflow: hidden; }
        .ai-bar-fill { background: #fff; height: 100%; border-radius: 8px; }
        .ai-detail { font-size: 0.9rem; margin: 6px 0; }
        .ai-workload { display: flex; gap: 16px; margin-top: 10px; padding-top: 10px; border-top: 1px solid rgba(255,255,255,0.2); font-size: 0.85rem; }
        .ai-workload-item { background: rgba(255,255,255,0.2); padding: 6px 12px; border-radius: 8px; }
        .ai-advice { background: rgba(255,255,255,0.15); padding: 10px 14px; border-radius: 8px; margin-top: 10px; font-size: 0.9rem; font-style: italic; }
        .hidden-panel { display: none; }
        .match-breakdown { display: flex; gap: 10px; margin: 8px 0; font-size: 0.8rem; }
        .match-type { background: rgba(255,255,255,0.2); padding: 4px 8px; border-radius: 6px; }
    </style>
    <script>
        function toggleAIAnalysis(appId) {
            var panel = document.getElementById('ai-panel-' + appId);
            panel.style.display = (panel.style.display === 'none' || panel.style.display === '') ? 'block' : 'none';
        }
    </script>
</head>
<body>
    <div class="container">
        <div class="review-box">
            <h1>Review Applications</h1>
            <p style="color: #6b7280; margin-bottom: 20px;">Click "AI Analysis" to view the intelligent matching report for each applicant</p>
            <table>
                <thead>
                    <tr>
                        <th>Application ID</th>
                        <th>Position</th>
                        <th>Applicant</th>
                        <th>Applied On</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Application> applications = (List<Application>) request.getAttribute("applications");
                        if (applications != null && !applications.isEmpty()) {
                            for (Application app : applications) {
                                Job job = null;
                                TAProfile profile = null;
                                AIMatchService.MatchResult matchResult = null;
                                int workload = 0;
                                String advice = "";
                                for (Job j : FileDBHelper.getAllJobs()) {
                                    if (j.getJobName().equals(app.getJobName())) {
                                        job = j;
                                        break;
                                    }
                                }
                                profile = FileDBHelper.getTAProfile(app.getApplicant());
                                if (job != null && profile != null) {
                                    matchResult = AIMatchService.calculateMatchScore(job, profile);
                                    workload = AIMatchService.getTAWorkload(app.getApplicant());
                                    advice = AIMatchService.getWorkloadAdvice(workload, AIMatchService.getAcceptedJobCount(app.getApplicant()));
                                }
                    %>
                        <tr>
                            <td><%= app.getId().substring(0, 8) %>...</td>
                            <td><strong><%= app.getJobName() %></strong></td>
                            <td><%= app.getApplicant() %></td>
                            <td><%= app.getApplyTime().length() > 16 ? app.getApplyTime().substring(0, 16) : app.getApplyTime() %></td>
                            <td><%= app.getStatus() %></td>
                            <td>
                                <% if ("Pending".equals(app.getStatus())) { %>
                                    <form action="../MoCheckApplyServlet" method="post" style="display: inline;">
                                        <input type="hidden" name="applicationId" value="<%= app.getId() %>">
                                        <input type="hidden" name="action" value="accept">
                                        <button type="submit" class="btn btn-accept">Accept</button>
                                    </form>
                                    <form action="../MoCheckApplyServlet" method="post" style="display: inline;">
                                        <input type="hidden" name="applicationId" value="<%= app.getId() %>">
                                        <input type="hidden" name="action" value="reject">
                                        <button type="submit" class="btn btn-reject">Reject</button>
                                    </form>
                                <% } %>
                                <button type="button" class="btn btn-ai" onclick="toggleAIAnalysis('<%= app.getId() %>')">AI Analysis</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6" style="padding: 0; border: none;">
                                <div id="ai-panel-<%= app.getId() %>" class="hidden-panel">
                                    <% if (matchResult != null) { %>
                                    <div class="ai-analysis-panel">
                                        <div class="ai-analysis-header">AI Smart Analysis Report</div>
                                        <div class="ai-score"><%= String.format("%.0f", matchResult.score) %>% Match</div>
                                        <div class="ai-stars"><%= matchResult.getScoreLevel() %></div>
                                        <div class="ai-bar">
                                            <div class="ai-bar-fill" style="width: <%= matchResult.score %>%;"></div>
                                        </div>
                                        <div class="match-breakdown">
                                            <span class="match-type">Exact: <%= matchResult.exactMatches %></span>
                                            <span class="match-type">Fuzzy: <%= matchResult.fuzzyMatches %></span>
                                            <span class="match-type">Partial: <%= matchResult.partialMatches %></span>
                                        </div>
                                        <div class="ai-detail">
                                            Matched Skills: <%= matchResult.matchedCount %>
                                            <span style="color: #fca5a5;"> | Missing: <%= matchResult.missingCount %></span>
                                        </div>
                                        <% if (!matchResult.missingSkills.isEmpty()) { %>
                                        <div class="ai-detail" style="color: #fca5a5;">
                                            Missing Skills: <%= String.join(", ", matchResult.missingSkills) %>
                                        </div>
                                        <% } %>
                                        <div class="ai-workload">
                                            <div class="ai-workload-item">Applications: <%= workload %></div>
                                            <div class="ai-workload-item">Accepted: <%= AIMatchService.getAcceptedJobCount(app.getApplicant()) %></div>
                                            <div class="ai-workload-item">Pending: <%= AIMatchService.getPendingJobCount(app.getApplicant()) %></div>
                                        </div>
                                        <div class="ai-advice"><%= advice %></div>
                                    </div>
                                    <% } else { %>
                                    <div style="padding: 16px; background: #fef3c7; color: #92400e; border-radius: 8px; margin: 8px 0;">
                                        Unable to perform AI analysis: applicant (<%= app.getApplicant() %>) has not completed their profile, or job details are incomplete.
                                    </div>
                                    <% } %>
                                </div>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="6">
                                <div class="empty-state">
                                    <p>No applications received yet.</p>
                                </div>
                            </td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
            <div class="nav-link">
                <a href="post_job.jsp">Post Job</a> |
                <a href="../AdminWorkloadServlet">Workload Dashboard</a> |
                <a href="../login.jsp">Logout</a>
            </div>
        </div>
    </div>
</body>
</html>
