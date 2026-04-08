<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>TA Profile - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input, textarea { width: 100%; padding: 8px; }
        button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #45a049; }
    </style>
</head>
<body>
    <h1>TA Profile Creation</h1>
    <form action="${pageContext.request.contextPath}/ta/profile" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label for="name">Full Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required>
        </div>
        <div class="form-group">
            <label for="phone">Phone:</label>
            <input type="tel" id="phone" name="phone">
        </div>
        <div class="form-group">
            <label for="major">Major:</label>
            <input type="text" id="major" name="major" required>
        </div>
        <div class="form-group">
            <label for="gpa">GPA:</label>
            <input type="number" id="gpa" name="gpa" step="0.01" min="0" max="4.0">
        </div>
        <div class="form-group">
            <label for="experience">Teaching Experience:</label>
            <textarea id="experience" name="experience" rows="4" placeholder="Describe your teaching experience..."></textarea>
        </div>
        <div class="form-group">
            <label for="skills">Skills:</label>
            <textarea id="skills" name="skills" rows="3" placeholder="List your relevant skills..."></textarea>
        </div>
        <div class="form-group">
            <label for="cv">CV/Resume:</label>
            <textarea id="cv" name="cv" rows="6" placeholder="Paste your CV content here..."></textarea>
        </div>
        <button type="submit">Create Profile</button>
    </form>
</body>
</html>