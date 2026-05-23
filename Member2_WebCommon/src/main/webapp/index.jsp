<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>BUPT TA Recruitment System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', 'Microsoft YaHei', Arial, sans-serif;
            margin: 0; padding: 0;
            background: url('images/bupt-campus.png') no-repeat center center fixed;
            background-size: cover;
            min-height: 100vh;
            color: #2f3d4a;
        }
        .container {
            width: min(1200px, 96%);
            margin: 0 auto; padding: 40px 0;
            min-height: 100vh;
            display: flex; flex-direction: column; justify-content: center;
        }
        .page-header { text-align: center; margin-bottom: 40px; position: relative; z-index: 1; }
        .logo-container { position: relative; display: inline-block; margin-bottom: 16px; }
        .logo-glow {
            position: absolute; top: 50%; left: 50%;
            transform: translate(-50%, -50%);
            width: 400px; height: 200px;
            background: radial-gradient(ellipse, rgba(102, 126, 234, 0.4) 0%, transparent 70%);
            filter: blur(30px); z-index: -1;
            animation: pulse 3s ease-in-out infinite;
        }
        @keyframes pulse {
            0%, 100% { opacity: 0.6; transform: translate(-50%, -50%) scale(1); }
            50% { opacity: 1; transform: translate(-50%, -50%) scale(1.1); }
        }
        .bupt-logo {
            width: 520px; max-width: 90%;
            animation: fadeInDown 0.8s ease-out;
            filter: brightness(0.7) saturate(1.5);
        }
        @keyframes fadeInDown {
            from { opacity: 0; transform: translateY(-30px); }
            to { opacity: 1; transform: translateY(0); }
        }
        .page-title {
            font-size: 2.8rem; color: #1e40af; margin-bottom: 8px;
            text-shadow: 0 4px 20px rgba(30, 64, 175, 0.3);
            font-weight: 700; letter-spacing: 2px;
            animation: fadeInDown 0.8s ease-out 0.2s both;
        }
        .page-subtitle {
            font-size: 1.2rem; color: #1e40af; margin-bottom: 12px;
            animation: fadeInDown 0.8s ease-out 0.4s both;
        }
        .tagline {
            font-size: 1rem; color: #1e40af; letter-spacing: 4px;
            animation: fadeInDown 0.8s ease-out 0.6s both;
        }
        .decorative-shapes {
            position: fixed; top: 0; left: 0; width: 100%; height: 100%;
            pointer-events: none; z-index: 0; overflow: hidden;
        }
        .shape { position: absolute; border-radius: 50%; opacity: 0.1; }
        .shape-1 {
            width: 400px; height: 400px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            top: -100px; left: -100px;
            animation: float 15s ease-in-out infinite;
        }
        .shape-2 {
            width: 300px; height: 300px;
            background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
            bottom: -50px; right: -50px;
            animation: float 12s ease-in-out infinite reverse;
        }
        .shape-3 {
            width: 200px; height: 200px;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            top: 50%; right: 10%;
            animation: float 18s ease-in-out infinite;
        }
        @keyframes float {
            0%, 100% { transform: translate(0, 0) rotate(0deg); }
            25% { transform: translate(20px, -20px) rotate(5deg); }
            50% { transform: translate(0, -40px) rotate(0deg); }
            75% { transform: translate(-20px, -20px) rotate(-5deg); }
        }
        .btn-container {
            display: flex; justify-content: center; gap: 20px;
            flex-wrap: wrap; position: relative; z-index: 1;
        }
        .btn-animated {
            position: relative; overflow: hidden;
            transition: all 0.3s ease;
            padding: 14px 32px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: #fff; border: none; border-radius: 12px;
            font-size: 1rem; font-weight: 600; cursor: pointer;
            text-decoration: none;
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
        .btn-secondary {
            background: rgba(255, 255, 255, 0.9); color: #667eea;
            backdrop-filter: blur(10px);
        }
        .btn-secondary:hover { background: #fff; }
        .footer-links {
            text-align: center; margin-top: 40px; padding: 20px;
            animation: fadeInDown 0.8s ease-out 0.8s both;
            position: relative; z-index: 1;
        }
        .footer-links a {
            color: rgba(255, 255, 255, 0.8); text-decoration: none;
            margin: 0 16px; font-weight: 500; transition: all 0.3s ease;
        }
        .footer-links a:hover { color: #fff; }
    </style>
</head>
<body>
    <div class="decorative-shapes">
        <div class="shape shape-1"></div>
        <div class="shape shape-2"></div>
        <div class="shape shape-3"></div>
    </div>

    <div class="container">
        <div class="page-header">
            <div class="logo-container">
                <div class="logo-glow"></div>
                <img src="images/bupt-logo.png" alt="BUPT Logo" class="bupt-logo">
            </div>
            <h1 class="page-title">BUPT TA Recruitment System</h1>
            <p class="page-subtitle">Beijing University of Posts and Telecommunications</p>
            <p class="tagline">Explore Opportunities &middot; Build Your Future</p>
        </div>

        <div class="btn-container">
            <a href="login.jsp" class="btn-animated">Login</a>
            <a href="register.jsp" class="btn-animated btn-secondary">Register</a>
        </div>

        <div class="footer-links">
            <a href="#">About Us</a> |
            <a href="#">Help Center</a> |
            <a href="#">Privacy Policy</a>
        </div>
    </div>
</body>
</html>
