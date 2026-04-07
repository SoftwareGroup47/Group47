<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="common-header">
    <div class="header-brand">
        <a href="${pageContext.request.contextPath}/">BUPT TA 招聘系统</a>
    </div>
    <nav class="header-nav">
        <a href="${pageContext.request.contextPath}/login.jsp">登录</a>
        <a href="${pageContext.request.contextPath}/register.jsp">注册</a>
        <a href="${pageContext.request.contextPath}/ta/job_hall.jsp">岗位大厅</a>
    </nav>
</div>
<style>
    .common-header {
        display: flex;
        flex-wrap: wrap;
        justify-content: space-between;
        align-items: center;
        gap: 12px;
        padding: 18px 26px;
        background: #1c4f9c;
        color: #ffffff;
        border-bottom: 4px solid #0f3269;
    }
    .common-header a {
        color: #ffffff;
        text-decoration: none;
    }
    .header-brand a {
        font-size: 1.45rem;
        font-weight: 700;
    }
    .header-nav {
        display: flex;
        flex-wrap: wrap;
        gap: 18px;
        align-items: center;
    }
    .header-nav a {
        padding: 10px 14px;
        border-radius: 999px;
        transition: background 0.2s ease;
    }
    .header-nav a:hover {
        background: rgba(255, 255, 255, 0.16);
    }
</style>
