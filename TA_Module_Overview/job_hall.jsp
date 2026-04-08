<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder, java.util.Arrays, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Job Hall - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(1100px, 96%); margin: 0 auto; padding: 26px 0 40px; }
        header { padding: 24px 0 18px; }
        header h1 { margin: 0 0 6px; font-size: 2rem; color: #1a2a4f; }
        header p { margin: 0; color: #55606c; font-size: 1rem; }
        .toolbar { display: flex; flex-wrap: wrap; gap: 12px; margin: 24px 0; }
        .toolbar input,
        .toolbar select { flex: 1 1 260px; min-width: 220px; padding: 12px 14px; border: 1px solid #cbd3db; border-radius: 10px; background: #fff; color: #2f3d4a; }
        .jobs-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 18px; }
        .job-card { background: #fff; border: 1px solid #dde3ea; border-radius: 16px; padding: 20px; box-shadow: 0 10px 18px rgba(35, 70, 120, 0.06); }
        .job-title { margin: 0 0 8px; font-size: 1.18rem; color: #112045; }
        .job-meta { display: flex; flex-wrap: wrap; gap: 10px; margin-bottom: 14px; color: #4f5f73; font-size: 0.96rem; }
        .job-meta span { background: #eef3fb; padding: 6px 10px; border-radius: 999px; }
        .job-description { margin: 0 0 18px; line-height: 1.7; color: #4d5f73; }
        .apply-link { display: inline-flex; align-items: center; justify-content: center; min-width: 130px; padding: 11px 16px; background: #1c6ff1; color: #fff; text-decoration: none; border-radius: 10px; transition: background 0.2s ease; }
        .apply-link:hover { background: #144fc1; }
        .empty-state { padding: 36px; text-align: center; background: #fff; border: 1px dashed #cfd8e2; border-radius: 16px; color: #52606d; }
    </style>
    <script>
        function filterJobs() {
            var query = document.getElementById('searchInput').value.toLowerCase();
            var dept = document.getElementById('departmentFilter').value.toLowerCase();
            document.querySelectorAll('.job-card').forEach(function(card) {
                var title = card.dataset.title.toLowerCase();
                var desc = card.dataset.description.toLowerCase();
                var department = card.dataset.department.toLowerCase();
                var matchesText = query === '' || title.includes(query) || desc.includes(query);
                var matchesDept = dept === '' || department === dept;
                card.style.display = (matchesText && matchesDept) ? 'grid' : 'none';
            });
        }
    </script>
</head>
<body>
    <div class="container">
        <header>
            <h1>TA 岗位浏览</h1>
            <p>查看当前可申请的助教岗位，选择适合你的岗位后点击“申请”进行提交。</p>
        </header>
        <div class="toolbar">
            <input id="searchInput" type="text" placeholder="搜索岗位、课程或技能" oninput="filterJobs()">
            <select id="departmentFilter" onchange="filterJobs()">
                <option value="">按部门筛选</option>
                <option value="networks">Networks</option>
                <option value="systems">Systems</option>
                <option value="database">Database</option>
                <option value="algorithms">Algorithms</option>
            </select>
        </div>
        <div class="jobs-grid">
            <%
                List<String> jobs = (List<String>) request.getAttribute("jobs");
                if (jobs == null) {
                    jobs = Arrays.asList(
                        "Computer Networks TA|Networks|10 hrs/week|协助教授网络协议、实验环境搭建和交换机/路由器配置。",
                        "Operating Systems TA|Systems|12 hrs/week|支持操作系统原理教学、内核编程实验和作业批改。",
                        "Database Systems TA|Database|8 hrs/week|帮助学生完成数据库设计、SQL 查询和课程辅导。",
                        "Data Structures TA|Algorithms|10 hrs/week|引导学生完成数据结构实现、算法分析和练习题讲解。"
                    );
                }
            %>
            <% if (jobs.isEmpty()) { %>
                <div class="empty-state">
                    <p>当前暂无可申请岗位。</p>
                    <p>请稍后刷新页面或联系管理员获取最新岗位信息。</p>
                </div>
            <% } else {
                for (int i = 0; i < jobs.size(); i++) {
                    String item = jobs.get(i);
                    String title = item;
                    String department = "General";
                    String hours = "TBD";
                    String description = "请联系 TA 办公室获取更多岗位信息。";
                    if (item.contains("|")) {
                        String[] parts = item.split("\\|", 4);
                        title = parts.length > 0 ? parts[0] : title;
                        department = parts.length > 1 ? parts[1] : department;
                        hours = parts.length > 2 ? parts[2] : hours;
                        description = parts.length > 3 ? parts[3] : description;
                    } else if (item.contains(" - ")) {
                        String[] parts = item.split(" - ", 2);
                        title = parts[0];
                        description = parts.length > 1 ? parts[1] : description;
                    }
            %>
                <div class="job-card" data-title="<%= title %>" data-department="<%= department %>" data-description="<%= description %>">
                    <div class="job-title"><%= title %></div>
                    <div class="job-meta">
                        <span>部门: <strong><%= department %></strong></span>
                        <span>工作量: <strong><%= hours %></strong></span>
                    </div>
                    <div class="job-description"><%= description %></div>
                    <a class="apply-link" href="apply_job.jsp?jobTitle=<%= URLEncoder.encode(title, "UTF-8") %>">申请该岗位</a>
                </div>
            <% }
            } %>
        </div>
    </div>
</body>
</html>
