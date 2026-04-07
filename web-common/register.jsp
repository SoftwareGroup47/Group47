<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>注册 - BUPT TA 系统</title>
    <style>
        body {
            margin: 0;
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(180deg, #f6fafd 0%, #ffffff 100%);
            color: #27364a;
        }
        .page-shell {
            display: grid;
            grid-template-columns: minmax(0, 1fr);
            gap: 24px;
            max-width: 1180px;
            margin: 0 auto;
            padding: 28px 22px 40px;
        }
        .auth-card {
            display: grid;
            gap: 26px;
            padding: 32px;
            background: #ffffff;
            border: 1px solid #e0e6f0;
            border-radius: 24px;
            box-shadow: 0 28px 70px rgba(29, 59, 104, 0.08);
        }
        .auth-card h1 {
            margin: 0;
            font-size: 2rem;
            color: #102a50;
        }
        .auth-card p {
            margin: 0;
            color: #5a6c84;
            line-height: 1.75;
        }
        .auth-form {
            display: grid;
            gap: 18px;
        }
        .form-grid {
            display: grid;
            gap: 18px;
        }
        .form-group {
            display: grid;
            gap: 8px;
        }
        label {
            font-weight: 600;
            color: #3f506a;
        }
        input,
        select {
            width: 100%;
            padding: 14px 16px;
            border: 1px solid #c7d2e3;
            border-radius: 12px;
            background: #fbfcff;
            font-size: 1rem;
        }
        textarea {
            width: 100%;
            min-height: 120px;
            padding: 14px 16px;
            border: 1px solid #c7d2e3;
            border-radius: 12px;
            background: #fbfcff;
            font-size: 1rem;
            resize: vertical;
        }
        input:focus,
        select:focus,
        textarea:focus {
            outline: none;
            border-color: #4783f2;
            box-shadow: 0 0 0 4px rgba(71, 131, 242, 0.12);
        }
        .actions {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            gap: 16px;
            align-items: center;
        }
        .btn-primary {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            min-width: 160px;
            padding: 14px 22px;
            border: none;
            border-radius: 999px;
            background: #2c7afe;
            color: #ffffff;
            font-weight: 700;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-primary:hover {
            background: #1d64d3;
        }
        .link-secondary {
            color: #2c7afe;
            text-decoration: none;
        }
        .link-secondary:hover {
            text-decoration: underline;
        }
        .page-note {
            font-size: 0.95rem;
            color: #54657a;
        }
        @media (min-width: 960px) {
            .page-shell {
                grid-template-columns: 320px minmax(0, 1fr);
                align-items: start;
            }
            .form-grid {
                grid-template-columns: repeat(2, minmax(0, 1fr));
            }
            .form-grid .form-group:nth-child(3),
            .form-grid .form-group:nth-child(4),
            .form-grid .form-group:nth-child(5) {
                grid-column: span 2;
            }
        }
    </style>
</head>
<body>
<jsp:include page="/common/header.jsp" />
<div class="page-shell">
    <jsp:include page="/common/sidebar.jsp" />
    <section class="auth-card">
        <div>
            <h1>创建新账号</h1>
            <p>填写基本信息以注册成为助教招聘系统用户。该页面为静态演示，不提交实际后端。</p>
        </div>
        <form class="auth-form" action="#" method="post" onsubmit="return false;">
            <div class="form-grid">
                <div class="form-group">
                    <label for="registerUsername">学号 / 登录名</label>
                    <input type="text" id="registerUsername" name="registerUsername" placeholder="请输入学号或登录名" required>
                </div>
                <div class="form-group">
                    <label for="registerEmail">电子邮箱</label>
                    <input type="email" id="registerEmail" name="registerEmail" placeholder="请输入邮箱地址" required>
                </div>
                <div class="form-group">
                    <label for="registerPassword">密码</label>
                    <input type="password" id="registerPassword" name="registerPassword" placeholder="设置密码" required>
                </div>
                <div class="form-group">
                    <label for="registerConfirm">确认密码</label>
                    <input type="password" id="registerConfirm" name="registerConfirm" placeholder="再次输入密码" required>
                </div>
                <div class="form-group">
                    <label for="registerMajor">专业</label>
                    <input type="text" id="registerMajor" name="registerMajor" placeholder="例如: 计算机科学与技术" required>
                </div>
                <div class="form-group">
                    <label for="registerYear">年级</label>
                    <select id="registerYear" name="registerYear" required>
                        <option value="">请选择年级</option>
                        <option value="2023">2023级</option>
                        <option value="2022">2022级</option>
                        <option value="2021">2021级</option>
                        <option value="2020">2020级</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label for="registerIntro">个人简介</label>
                <textarea id="registerIntro" name="registerIntro" placeholder="简要介绍你的学习背景和助教经历"></textarea>
            </div>
            <div class="actions">
                <a class="link-secondary" href="login.jsp">已有账号？登录</a>
                <button class="btn-primary" type="submit">完成注册</button>
            </div>
        </form>
        <p class="page-note">此页面采用 JSP 包含组件方式复用公共头部与侧边栏，便于后续维护与扩展。</p>
    </section>
</div>
</body>
</html>
