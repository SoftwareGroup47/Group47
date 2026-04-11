<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.model.Application" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理申请 - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(800px, 96%); margin: 0 auto; padding: 40px 0; }
        .check-apply-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
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
        .empty-state { padding: 36px; text-align: center; background: #f9fafb; border: 1px dashed #e5e7eb; border-radius: 16px; color: #6b7280; }
        .nav-link { margin-top: 20px; text-align: center; }
        .nav-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; }
        .nav-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <div class="check-apply-box">
            <h1>管理申请</h1>
            <table>
                <thead>
                    <tr>
                        <th>申请ID</th>
                        <th>岗位名称</th>
                        <th>申请人</th>
                        <th>申请时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Application> applications = (List<Application>) request.getAttribute("applications");
                        if (applications != null && !applications.isEmpty()) {
                            for (Application app : applications) {
                    %>
                        <tr>
                            <td><%= app.getId() %></td>
                            <td><%= app.getJobName() %></td>
                            <td><%= app.getApplicant() %></td>
                            <td><%= app.getApplyTime() %></td>
                            <td><%= app.getStatus() %></td>
                            <td>
                                <% if ("Pending".equals(app.getStatus())) { %>
                                    <form action="../MoCheckApplyServlet" method="post" style="display: inline;">
                                        <input type="hidden" name="applicationId" value="<%= app.getId() %>">
                                        <input type="hidden" name="action" value="accept">
                                        <button type="submit" class="btn btn-accept">接受</button>
                                    </form>
                                    <form action="../MoCheckApplyServlet" method="post" style="display: inline;">
                                        <input type="hidden" name="applicationId" value="<%= app.getId() %>">
                                        <input type="hidden" name="action" value="reject">
                                        <button type="submit" class="btn btn-reject">拒绝</button>
                                    </form>
                                <% } %>
                            </td>
                        </tr>
                    <% 
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="6">
                                <div class="empty-state">
                                    <p>暂无申请记录</p>
                                </div>
                            </td>
                        </tr>
                    <% 
                        }
                    %>
                </tbody>
            </table>
            <div class="nav-link">
                <a href="post_job.jsp">发布岗位</a> | <a href="../login.jsp">退出登录</a>
            </div>
        </div>
    </div>
</body>
</html>
