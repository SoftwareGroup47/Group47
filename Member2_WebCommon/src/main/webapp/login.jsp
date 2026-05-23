<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - BUPT TA Recruitment System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', 'Microsoft YaHei', Arial, sans-serif;
            margin: 0; padding: 0;
            background: url('images/bg.png') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh; color: #2f3d4a;
            display: flex; align-items: center; justify-content: center;
        }
        .container { width: min(480px, 96%); }
        .auth-card {
            background: rgba(255, 255, 255, 0.75);
            backdrop-filter: blur(16px);
            -webkit-backdrop-filter: blur(16px);
            border: 1px solid rgba(255, 255, 255, 0.4);
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.12), inset 0 1px 0 rgba(255,255,255,0.5);
            border-radius: 20px; padding: 40px;
            animation: slideUp 0.6s ease-out;
        }
        @keyframes slideUp {
            from { opacity: 0; transform: translateY(40px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .page-header { text-align: center; margin-bottom: 32px; }
        .page-header h1 {
            font-size: 2rem; color: #fff; margin-bottom: 8px;
            text-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
            animation: fadeInDown 0.8s ease-out;
        }
        .page-header p {
            font-size: 1rem; color: rgba(255, 255, 255, 0.95);
            animation: fadeInDown 0.8s ease-out 0.2s both;
            text-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
        }
        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-30px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .form-group { margin-bottom: 24px; }
        .form-group label {
            display: block; margin-bottom: 10px; font-weight: 600;
            color: #1a2a4f; font-size: 1rem;
        }
        .form-group input, .form-group select {
            width: 100%; padding: 14px 18px;
            border: 2px solid #e0e6ed; border-radius: 12px;
            font-size: 1rem; background: #f8fafc;
            transition: all 0.3s ease;
        }
        .form-group input:focus, .form-group select:focus {
            outline: none; border-color: #667eea; background: #fff;
            box-shadow: 0 0 0 4px rgba(102, 126, 234, 0.1);
        }
        .btn-animated {
            position: relative; overflow: hidden;
            transition: all 0.3s ease;
            width: 100%; padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #fff; border: none; border-radius: 12px;
            font-size: 1rem; font-weight: 600; cursor: pointer;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }
        .btn-animated:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
        }
        .btn-animated::before {
            content: ''; position: absolute; top: 0; left: -100%;
            width: 100%; height: 100%;
            background: linear-gradient(90deg, transparent, rgba(255,255,255,0.3), transparent);
            transition: left 0.5s ease;
        }
        .btn-animated:hover::before { left: 100%; }
        .auth-link { margin-top: 24px; text-align: center; color: #4f5f73; }
        .auth-link a { color: #667eea; text-decoration: none; font-weight: 600; }
        .auth-link a:hover { text-decoration: underline; }
        .footer-links {
            text-align: center; margin-top: 40px;
            animation: fadeInDown 0.8s ease-out 0.8s both;
        }
        .footer-links a {
            color: rgba(255, 255, 255, 0.8); text-decoration: none;
            font-weight: 500; transition: all 0.3s ease;
        }
        .footer-links a:hover { color: #fff; text-decoration: none; }
    </style>
</head>
<body>
    <div class="container">
        <div class="page-header">
            <h1>Welcome Back</h1>
            <p>Please login to your account</p>
        </div>

        <div class="auth-card">
            <form action="LoginServlet" method="post">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit" class="btn-animated">Login</button>
                <div class="auth-link">
                    Don't have an account? <a href="register.jsp">Register here</a>
                </div>
            </form>
        </div>

        <div class="footer-links">
            <a href="index.jsp">Home</a> |
            <a href="#">About Us</a> |
            <a href="#">Help Center</a> |
            <a href="#">Privacy Policy</a>
        </div>
    </div>
</body>
</html>
