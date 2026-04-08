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
# TA端Servlet模块（成员4）

## 模块概述
本模块由成员4开发，负责TA用户的全部后端业务逻辑，包含注册、登录、档案维护、岗位申请四个核心控制器。

## 文件结构
src/

└── servlet/

├── RegisterServlet.java # 处理用户注册

├── LoginServlet.java # 处理 TA 用户登录

├── TaProfileServlet.java # 处理 TA 档案保存与更新

└── ApplyJobServlet.java # 处理 TA 岗位申请

## 功能说明
- RegisterServlet：校验学号是否重复，将新用户信息保存到 users.json
- LoginServlet：验证账号、密码，并校验用户身份是否为TA
- TaProfileServlet：保存/更新TA个人档案信息
- ApplyJobServlet：提交岗位申请，记录申请人信息

## 依赖说明
- model.User（成员1开发）
- model.Job（成员1开发）
- util.FileDBHelper（成员1开发）
=======
# Group47  
The main repository of Group47  
GitHub ID: xsfq21-bot  QMID: 231221043  
GitHub ID: yangshengyu-ai   QMID: 231221504  
GitHub ID: thanatos711   QMID: 231221995  
GitHub ID: YuLin200411   QMID: 231220828  
GitHub ID: Michael-han903   QMID: 231221593  
GitHub ID: MingxuanLiu1229   QMID: 231220150  

>>>>>>> main
