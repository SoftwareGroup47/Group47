<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>登录 - BUPT TA 系统</title>
    <style>
        body {
            margin: 0;
            font-family: "Segoe UI", Arial, sans-serif;
            background: linear-gradient(180deg, #eef4ff 0%, #f9fbff 100%);
            color: #24355b;
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
            border: 1px solid #d8e0ee;
            border-radius: 24px;
            box-shadow: 0 24px 60px rgba(16, 52, 102, 0.08);
        }
        .auth-card h1 {
            margin: 0;
            font-size: 2rem;
            color: #12234d;
        }
        .auth-card p {
            margin: 0;
            color: #56657a;
            line-height: 1.75;
        }
        .auth-form {
            display: grid;
            gap: 18px;
        }
        .form-group {
            display: grid;
            gap: 8px;
        }
        label {
            font-weight: 600;
            color: #374763;
        }
        input {
            width: 100%;
            padding: 14px 16px;
            border: 1px solid #c5d0e5;
            border-radius: 12px;
            font-size: 1rem;
            background: #fbfcff;
        }
        input:focus {
            outline: none;
            border-color: #5e7eef;
            box-shadow: 0 0 0 4px rgba(94, 126, 239, 0.12);
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
            min-width: 140px;
            padding: 14px 20px;
            border: none;
            border-radius: 999px;
            background: #2f6fdf;
            color: #ffffff;
            font-weight: 700;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-primary:hover {
            background: #254fbd;
        }
        .help-text {
            color: #5a6b87;
            font-size: 0.95rem;
        }
        .link-secondary {
            color: #2f6fdf;
            text-decoration: none;
        }
        .link-secondary:hover {
            text-decoration: underline;
        }
        .page-note {
            font-size: 0.95rem;
            color: #5d6e88;
        }
        @media (min-width: 960px) {
            .page-shell {
                grid-template-columns: 320px minmax(0, 1fr);
                align-items: start;
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
            <h1>登录到助教系统</h1>
            <p>请输入你的学号 / 电子邮件和密码，访问岗位信息与申请功能。</p>
        </div>
        <form class="auth-form" action="#" method="post" onsubmit="return false;">
            <div class="form-group">
                <label for="loginIdentifier">学号 / 邮箱</label>
                <input type="text" id="loginIdentifier" name="loginIdentifier" placeholder="请输入学号或邮箱" required>
            </div>
            <div class="form-group">
                <label for="loginPassword">密码</label>
                <input type="password" id="loginPassword" name="loginPassword" placeholder="请输入密码" required>
            </div>
            <div class="actions">
                <a class="link-secondary" href="register.jsp">还没有账号？去注册</a>
                <button class="btn-primary" type="submit">立即登录</button>
            </div>
        </form>
        <p class="page-note">这是静态演示页面，无后端登录逻辑。数据提交不会实际验证。</p>
    </section>
</div>
</body>
</html>
