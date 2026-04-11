<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<aside class="common-sidebar">
    <div class="sidebar-panel">
        <h2>快速入口</h2>
        <ul>
            <li><a href="${pageContext.request.contextPath}/login.jsp">登录</a></li>
            <li><a href="${pageContext.request.contextPath}/register.jsp">注册</a></li>
            <li><a href="${pageContext.request.contextPath}/ta/job_hall.jsp">岗位大厅</a></li>
            <li><a href="${pageContext.request.contextPath}/ta/ta_profile.jsp">创建个人档案</a></li>
        </ul>
    </div>
    <div class="sidebar-panel">
        <h2>系统说明</h2>
        <p>本系统为 BUPT 助教招聘前端静态页面示例，使用 JSP 组件复用公共头部与侧边栏。</p>
    </div>
</aside>
<style>
    .common-sidebar {
        display: grid;
        gap: 20px;
        max-width: 280px;
        padding: 20px;
        background: #f8fafc;
        border: 1px solid #dbe4ed;
        border-radius: 18px;
    }
    .sidebar-panel h2 {
        margin: 0 0 12px;
        font-size: 1rem;
        color: #1f3f63;
    }
    .sidebar-panel ul {
        list-style: none;
        padding: 0;
        margin: 0;
        display: grid;
        gap: 10px;
    }
    .sidebar-panel li a {
        display: block;
        padding: 10px 14px;
        border-radius: 12px;
        background: #ffffff;
        color: #1f3f63;
        text-decoration: none;
        border: 1px solid #dbe4ed;
        transition: background 0.2s ease, transform 0.15s ease;
    }
    .sidebar-panel li a:hover {
        background: #eaf2fa;
        transform: translateX(2px);
    }
    .sidebar-panel p {
        margin: 0;
        color: #4d647c;
        line-height: 1.6;
        font-size: 0.95rem;
    }
</style>
