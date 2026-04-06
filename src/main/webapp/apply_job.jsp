<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Apply for Job - BUPT TA Recruitment System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input, textarea, select { width: 100%; padding: 8px; }
        button { padding: 10px 20px; background-color: #28a745; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #218838; }
        .back-link { margin-top: 20px; }
        .back-link a { color: #007BFF; text-decoration: none; }
    </style>
</head>
<body>
    <h1>Apply for TA Position</h1>
    <%
        String jobTitle = request.getParameter("jobTitle");
        if (jobTitle == null) {
            jobTitle = "Selected Position";
        }
    %>
    <p><strong>Position:</strong> <%= jobTitle %></p>

    <form action="${pageContext.request.contextPath}/ta/submitApplication" method="post">
        <input type="hidden" name="jobTitle" value="<%= jobTitle %>">

        <div class="form-group">
            <label for="username">Your Username:</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="coverLetter">Cover Letter:</label>
            <textarea id="coverLetter" name="coverLetter" rows="6" placeholder="Explain why you're interested in this position and what makes you a good fit..." required></textarea>
        </div>

        <div class="form-group">
            <label for="availability">Availability:</label>
            <select id="availability" name="availability" required>
                <option value="">Select your availability</option>
                <option value="full-time">Full Time</option>
                <option value="part-time">Part Time</option>
                <option value="flexible">Flexible</option>
            </select>
        </div>

        <div class="form-group">
            <label for="preferredHours">Preferred Hours per Week:</label>
            <input type="number" id="preferredHours" name="preferredHours" min="1" max="40">
        </div>

        <div class="form-group">
            <label for="additionalInfo">Additional Information:</label>
            <textarea id="additionalInfo" name="additionalInfo" rows="3" placeholder="Any additional information you'd like to provide..."></textarea>
        </div>

        <button type="submit">Submit Application</button>
    </form>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/ta/jobHall">← Back to Job Hall</a>
    </div>
</body>
</html>