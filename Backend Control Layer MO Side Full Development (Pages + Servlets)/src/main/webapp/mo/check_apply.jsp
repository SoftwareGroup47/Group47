<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>MO - 查看申请</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            background-color: white;
            margin-top: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .no-data {
            text-align: center;
            padding: 40px;
            color: #999;
            font-style: italic;
        }
        .message {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 4px;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>查看岗位申请</h1>
        
        <%-- 显示错误消息 --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="message error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <%-- 显示申请列表 --%>
        <% List<model.Application> applications = (List<model.Application>) request.getAttribute("applications"); %>
        <% if (applications != null && !applications.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>申请ID</th>
                        <th>岗位名称</th>
                        <th>申请人</th>
                        <th>申请时间</th>
                        <th>状态</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (model.Application app : applications) { %>
                        <tr>
                            <td><%= app.getId() %></td>
                            <td><%= app.getJobName() %></td>
                            <td><%= app.getApplicant() %></td>
                            <td><%= app.getApplyTime() %></td>
                            <td><%= app.getStatus() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else { %>
            <div class="no-data">
                暂无申请记录
            </div>
        <% } %>
    </div>
</body>
</html>