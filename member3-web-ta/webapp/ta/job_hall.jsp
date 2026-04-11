<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Job" %>
<%@ page import="org.example.util.FileDBHelper" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>岗位大厅 - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
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
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>TA 岗位浏览</h1>
            <p>查看当前可申请的助教岗位，选择适合你的岗位后点击“申请”进行提交。</p>
        </div>
        <div class="toolbar">
            <input type="text" placeholder="搜索岗位名称或要求">
            <select>
                <option value="">按状态筛选</option>
                <option value="Open">开放</option>
                <option value="Closed">关闭</option>
            </select>
        </div>
        <div class="jobs-grid">
            <% 
                List<Job> jobs = FileDBHelper.getAllJobs();
                if (jobs != null && !jobs.isEmpty()) {
                    for (Job job : jobs) {
            %>
                <div class="job-card">
                    <h3 class="job-title"><%= job.getJobName() %></h3>
                    <div class="job-meta">
                        <span>发布者: <%= job.getMoName() %></span>
                        <span>状态: <%= job.getStatus() %></span>
                    </div>
                    <p class="job-description"><%= job.getRequirements() %></p>
                    <form action="ApplyJobServlet" method="post">
                        <input type="hidden" name="jobId" value="<%= job.getJobId() %>">
                        <input type="hidden" name="jobName" value="<%= job.getJobName() %>">
                        <button type="submit" class="apply-btn">申请该岗位</button>
                    </form>
                </div>
            <% 
                    }
                } else {
            %>
                <div class="empty-state">
                    <p>当前暂无可申请岗位。</p>
                    <p>请稍后刷新页面或联系管理员获取最新岗位信息。</p>
                </div>
            <% 
                }
            %>
        </div>
        <div class="nav-link">
            <a href="ta_profile.jsp">个人资料</a> | <a href="login.jsp">退出登录</a>
        </div>
    </div>
</body>
</html>
