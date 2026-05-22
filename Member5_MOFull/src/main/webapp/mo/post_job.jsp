<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Post Job - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; padding: 0; background: #f4f6f8; color: #2f3d4a; }
        .container { width: min(700px, 96%); margin: 0 auto; padding: 40px 0; }
        .post-job-box { background: #fff; border-radius: 16px; padding: 32px; box-shadow: 0 10px 30px rgba(35, 70, 120, 0.1); }
        h1 { margin: 0 0 24px; font-size: 1.8rem; color: #1a2a4f; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: 600; color: #4f5f73; }
        input, textarea { width: 100%; padding: 12px 16px; border: 1px solid #cbd3db; border-radius: 10px; font-size: 1rem; box-sizing: border-box; }
        textarea { height: 140px; resize: vertical; }
        input:focus, textarea:focus { outline: none; border-color: #1c6ff1; box-shadow: 0 0 0 3px rgba(28, 111, 241, 0.1); }
        .hint { font-size: 0.85rem; color: #6b7280; margin-top: 4px; }
        .btn { width: 100%; padding: 14px; background: #1c6ff1; color: #fff; border: none; border-radius: 10px; font-size: 1rem; font-weight: 600; cursor: pointer; transition: background 0.2s ease; }
        .btn:hover { background: #144fc1; }
        .nav-link { margin-top: 20px; text-align: center; }
        .nav-link a { color: #1c6ff1; text-decoration: none; font-weight: 600; margin: 0 10px; }
        .nav-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="container">
        <div class="post-job-box">
            <h1>Post a TA Position</h1>
            <form action="../MoPostJobServlet" method="post">
                <div class="form-group">
                    <label for="jobName">Job Title</label>
                    <input type="text" id="jobName" name="jobName" 
                           placeholder="e.g., Data Structures TA, Algorithms Lab Assistant" required>
                </div>
                <div class="form-group">
                    <label for="requirements">Skill Requirements (comma-separated)</label>
                    <textarea id="requirements" name="requirements" 
                              placeholder="e.g., Java, Python, algorithms, data structures, communication, teamwork"
                              required></textarea>
                    <p class="hint">Enter skills as comma-separated keywords. The AI matching system will use these to evaluate applicants.</p>
                </div>
                <button type="submit" class="btn">Publish Position</button>
                <div class="nav-link">
                    <a href="check_apply.jsp">Review Applications</a> |
                    <a href="../AdminWorkloadServlet">Workload Dashboard</a> |
                    <a href="../login.jsp">Logout</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
