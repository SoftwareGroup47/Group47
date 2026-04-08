<<<<<<< ZiHan-Li
# TA-side Servlet Module (Member 4)

## 📌 Module Overview
This module is responsible for the **backend business logic of TA (Teaching Assistant) users** in the Campus TA Recruitment System.
It implements all core Servlet controllers for TA users, connecting frontend pages with the underlying data storage layer.

## ✅ Core Functions
1.  **User Registration (`RegisterServlet`)**
    - Validates student ID uniqueness to prevent duplicate registration
    - Creates new TA user objects and saves data to `users.json` via `FileDBHelper`
    - Redirects to login page after successful registration

2.  **User Login (`LoginServlet`)**
    - Verifies TA user account and password
    - Validates user identity (only TA role allowed to log in)
    - Manages user session and redirects to TA profile page

3.  **TA Profile Management (`TaProfileServlet`)**
    - Handles TA profile information submission and update
    - Updates user data in `users.json`
    - Requires login authentication before operation

4.  **Job Application (`ApplyJobServlet`)**
    - Processes TA job application requests
    - Records applicant information to corresponding job in `jobs.json`
    - Validates job existence and user login status

## 📁 File Structure
src/

└── servlet/

├── RegisterServlet.java # Handle user registration

├── LoginServlet.java # Handle TA user login

├── TaProfileServlet.java # Handle TA profile saving

└── ApplyJobServlet.java # Handle TA job application

## Functions
- RegisterServlet: Check student ID unique, save new user to users.json
- LoginServlet: Verify account, password, and TA role
- TaProfileServlet: Save/update TA profile information
- ApplyJobServlet: Submit job application and record applicant

## Dependencies
- model.User (Member 1)
- model.Job (Member 1)
- util.FileDBHelper (Member 1)
