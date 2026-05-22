<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Job" %>
<%@ page import="org.example.model.TAProfile" %>
<%@ page import="org.example.util.FileDBHelper" %>
<%@ page import="org.example.util.AIMatchService" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Job Hall - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: linear-gradient(135deg, #e0c3fc 0%, #8ec5fc 100%); min-height: 100vh; color: #2f3d4a; background-attachment: fixed; }
        .container { width: min(1100px, 96%); margin: 0 auto; padding: 40px 0; }
        .header { margin-bottom: 32px; }
        h1 { margin: 0 0 8px; font-size: 2rem; color: #1a2a4f; }
        p { margin: 0; color: #55606c; font-size: 1rem; }
        .toolbar { display: flex; flex-wrap: wrap; gap: 12px; margin: 24px 0; }
        .toolbar input, .toolbar select { flex: 1 1 260px; min-width: 220px; padding: 12px 14px; border: 1px solid #cbd3db; border-radius: 10px; background: #fff; color: #2f3d4a; }
        .jobs-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(320px, 1fr)); gap: 20px; }
        .job-card { background: #fff; border: 1px solid #dde3ea; border-radius: 16px; padding: 24px; box-shadow: 0 10px 18px rgba(35, 70, 120, 0.06); }
        .job-title { margin: 0 0 12px; font-size: 1.25rem; color: #112045; }
        .job-meta { display: flex; flex-wrap: wrap; gap: 10px; margin-bottom: 16px; color: #4f5f73; font-size: 0.96rem; }
        .job-meta span { background: #eef3fb; padding: 6px 10px; border-radius: 999px; }
        .job-description { margin: 0 0 20px; line-height: 1.7; color: #4d5f73; }
        .apply-btn { display: inline-flex; align-items: center; justify-content: center; min-width: 130px; padding: 11px 16px; background: #1c6ff1; color: #fff; text-decoration: none; border-radius: 10px; transition: background 0.2s ease; border: none; cursor: pointer; font-size: 1rem; }
        .apply-btn:hover { background: #144fc1; }
        .empty-state { padding: 48px; text-align: center; background: #fff; border: 1px dashed #cfd8e2; border-radius: 16px; color: #52606d; grid-column: 1 / -1; }
        .nav-link { margin-top: 32px; text-align: center; }
        .nav-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; margin: 0 12px; }
        .nav-link a:hover { text-decoration: underline; }

        .ai-panel { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); border-radius: 12px; padding: 16px; margin: 16px 0; color: #fff; }
        .ai-panel-header { display: flex; align-items: center; gap: 8px; margin-bottom: 12px; font-weight: 600; }
        .ai-panel-header span { font-size: 1.2rem; }
        .match-bar { background: rgba(255,255,255,0.3); border-radius: 10px; height: 12px; margin: 10px 0; overflow: hidden; }
        .match-bar-fill { background: #4ade80; height: 100%; border-radius: 10px; transition: width 0.3s ease; }
        .match-score { font-size: 1.5rem; font-weight: 700; }
        .match-stars { color: #fbbf24; margin: 4px 0; }
        .match-detail { font-size: 0.9rem; opacity: 0.95; margin: 8px 0; }
        .match-skill-matched { color: #4ade80; }
        .match-skill-missing { color: #fca5a5; }
        .workload-info { font-size: 0.85rem; margin-top: 8px; padding-top: 8px; border-top: 1px solid rgba(255,255,255,0.2); }
        .no-profile-hint { background: #fef3c7; color: #92400e; padding: 10px 14px; border-radius: 8px; font-size: 0.9rem; margin: 12px 0; }
        .suggestion { font-style: italic; opacity: 0.9; margin-top: 8px; font-size: 0.85rem; }
        .match-breakdown { display: flex; gap: 10px; margin: 8px 0; font-size: 0.8rem; }
        .match-type { background: rgba(255,255,255,0.2); padding: 4px 8px; border-radius: 6px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>Job Hall</h1>
            <p>Browse available Teaching Assistant positions. AI-powered skill matching helps you find the best fit!</p>
        </div>
        <div class="toolbar">
            <input type="text" id="searchInput" placeholder="Search jobs by name or skills...">
            <select>
                <option value="">Filter by Status</option>
                <option value="Open">Open</option>
                <option value="Closed">Closed</option>
            </select>
        </div>
        <div class="jobs-grid">
            <%
                String username = (session != null) ? (String) session.getAttribute("username") : null;
                TAProfile myProfile = (username != null) ? FileDBHelper.getTAProfile(username) : null;
                List<Job> jobs = FileDBHelper.getAllJobs();
                if (jobs != null && !jobs.isEmpty()) {
                    for (Job job : jobs) {
                        AIMatchService.MatchResult matchResult = null;
                        int myWorkload = 0;
                        String suggestion = "";
                        if (myProfile != null) {
                            matchResult = AIMatchService.calculateMatchScore(job, myProfile);
                            myWorkload = AIMatchService.getTAWorkload(username);
                            suggestion = AIMatchService.getMatchSuggestion(matchResult.score);
                        }
            %>
                <div class="job-card">
                    <h3 class="job-title"><%= job.getJobName() %></h3>
                    <div class="job-meta">
                        <span>Posted by: <%= job.getMoName() %></span>
                        <span>Status: <%= job.getStatus() %></span>
                    </div>
                    <p class="job-description"><%= job.getRequirements() %></p>

                    <% if (myProfile != null && matchResult != null) { %>
                    <div class="ai-panel">
                        <div class="ai-panel-header">
                            <span>AI</span> Smart Matching Analysis
                        </div>
                        <div class="match-score"><%= String.format("%.0f", matchResult.score) %>% Match</div>
                        <div class="match-stars"><%= matchResult.getScoreLevel() %></div>
                        <div class="match-bar">
                            <div class="match-bar-fill" style="width: <%= matchResult.score %>%;"></div>
                        </div>
                        <div class="match-breakdown">
                            <span class="match-type">Exact: <%= matchResult.exactMatches %></span>
                            <span class="match-type">Fuzzy: <%= matchResult.fuzzyMatches %></span>
                            <span class="match-type">Partial: <%= matchResult.partialMatches %></span>
                        </div>
                        <div class="match-detail">
                            Matched: <%= matchResult.matchedCount %> skills
                            <span class="match-skill-missing"> | Missing: <%= matchResult.missingCount %></span>
                        </div>
                        <% if (!matchResult.missingSkills.isEmpty()) { %>
                        <div class="match-detail match-skill-missing">
                            Missing Skills: <%= String.join(", ", matchResult.missingSkills) %>
                        </div>
                        <% } %>
                        <div class="suggestion"><%= suggestion %></div>
                        <div class="workload-info">
                            Your Current Workload: <%= myWorkload %> active application(s)
                        </div>
                    </div>
                    <% } else if (username != null) { %>
                    <div class="no-profile-hint">
                        Please complete your profile (add your skills) to get AI matching analysis.
                    </div>
                    <% } %>

                    <form action="ApplyJobServlet" method="post">
                        <input type="hidden" name="jobId" value="<%= job.getJobId() %>">
                        <input type="hidden" name="jobName" value="<%= job.getJobName() %>">
                        <button type="submit" class="apply-btn">Apply</button>
                    </form>
                </div>
            <%
                    }
                } else {
            %>
                <div class="empty-state">
                    <p>No job postings available at the moment.</p>
                    <p>Please check back later or contact your Module Organizer.</p>
                </div>
            <%
                }
            %>
        </div>
        <div class="nav-link">
            <a href="ta_profile.jsp">My Profile</a> |
            <a href="TAApplicationStatusServlet">My Applications</a> |
            <a href="AdminWorkloadServlet">Workload Overview</a> |
            <a href="login.jsp">Logout</a>
        </div>
    </div>
</body>
</html>
