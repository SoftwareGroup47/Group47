<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>岗位申请 - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(500px, 96%); margin: 0 auto; padding: 40px 0; }
        .apply-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        h1 { margin: 0 0 24px; font-size: 1.8rem; color: #1a2a4f; }
        .confirm-info { margin-bottom: 28px; padding: 20px; background: #eef6ff; border-radius: 12px; }
        .confirm-info p { margin: 8px 0; color: #3d5268; }
        .confirm-info strong { color: #1a2a4f; }
        .btn-group { display: flex; gap: 14px; }
        .btn { flex: 1; padding: 14px; border: none; border-radius: 10px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: background 0.2s ease; }
        .btn-confirm { background: #1c6ff1; color: #fff; }
        .btn-confirm:hover { background: #144fc1; }
        .btn-cancel { background: #e8eaed; color: #4f5f73; }
        .btn-cancel:hover { background: #d0d4da; }
        .nav-link { margin-top: 24px; text-align: center; }
        .nav-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; }
        .nav-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <div class="apply-box">
            <h1>确认申请</h1>
            <div class="confirm-info">
                <p><strong>岗位名称：</strong><%= request.getParameter("jobName") %></p>
                <p><strong>申请确认：</strong>您即将申请该助教岗位，请确认您的申请信息。</p>
            </div>
            <form action="../ApplyJobServlet" method="post">
                <input type="hidden" name="jobId" value="<%= request.getParameter("jobId") %>">
                <input type="hidden" name="jobName" value="<%= request.getParameter("jobName") %>">
                <div class="btn-group">
                    <button type="submit" class="btn btn-confirm">确认申请</button>
                    <button type="button" class="btn btn-cancel" onclick="window.location.href='job_hall.jsp'">取消</button>
                </div>
            </form>
            <div class="nav-link">
                <a href="job_hall.jsp">返回岗位大厅</a> | <a href="ta_profile.jsp">个人资料</a>
            </div>
        </div>
    </div>
</body>
</html>