<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>发布岗位 - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(600px, 96%); margin: 0 auto; padding: 40px 0; }
        .post-job-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        h1 { margin: 0 0 24px; font-size: 1.8rem; color: #1a2a4f; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: 600; color: #4f5f73; }
        input, textarea { width: 100%; padding: 12px 16px; border: 1px solid #cbd3db; border-radius: 10px; font-size: 1rem; box-sizing: border-box; }
        textarea { height: 120px; resize: vertical; }
        input:focus, textarea:focus { outline: none; border-color: #1c6ff1; box-shadow: 0 0 0 3px rgba(28, 111, 241, 0.1); }
        .btn { width: 100%; padding: 14px; background: #1c6ff1; color: #fff; border: none; border-radius: 10px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: background 0.2s ease; }
        .btn:hover { background: #144fc1; }
        .nav-link { margin-top: 20px; text-align: center; }
        .nav-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; }
        .nav-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <div class="post-job-box">
            <h1>发布助教岗位</h1>
            <form action="../MoPostJobServlet" method="post">
                <div class="form-group">
                    <label for="jobName">岗位名称</label>
                    <input type="text" id="jobName" name="jobName" required>
                </div>
                <div class="form-group">
                    <label for="requirements">岗位要求</label>
                    <textarea id="requirements" name="requirements" required></textarea>
                </div>
                <button type="submit" class="btn">发布岗位</button>
                <div class="nav-link">
                    <a href="check_apply.jsp">查看申请</a> | <a href="../login.jsp">退出登录</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
