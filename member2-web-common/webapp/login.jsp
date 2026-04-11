<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(400px, 96%); margin: 0 auto; padding: 40px 0; }
        .login-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        h1 { margin: 0 0 24px; font-size: 1.8rem; color: #1a2a4f; text-align: center; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: 600; color: #4f5f73; }
        input { width: 100%; padding: 12px 16px; border: 1px solid #cbd3db; border-radius: 10px; font-size: 1rem; box-sizing: border-box; }
        input:focus { outline: none; border-color: #1c6ff1; box-shadow: 0 0 0 3px rgba(28, 111, 241, 0.1); }
        .btn { width: 100%; padding: 14px; background: #1c6ff1; color: #fff; border: none; border-radius: 10px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: background 0.2s ease; }
        .btn:hover { background: #144fc1; }
        .register-link { margin-top: 20px; text-align: center; color: #4f5f73; }
        .register-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; }
        .register-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <div class="login-box">
            <h1>登录</h1>
            <form action="LoginServlet" method="post">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn">登录</button>
                <div class="register-link">
                    还没有账号？<a href="register.jsp">立即注册</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
