## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## TA 模块页面说明

### 1. `job_hall.jsp`
- 作用：展示 TA 招聘系统中的岗位列表，支持搜索和部门筛选。
- 特点：
  - 页面内通过 `searchInput` 和 `departmentFilter` 进行客户端过滤。
  - 岗位信息来自 `request.getAttribute("jobs")`，若为空则使用默认示例数据。
  - 每个岗位卡片包含岗位名称、部门、工作量、简介和“申请该岗位”按钮。
  - 申请链接会跳转到 `apply_job.jsp?jobTitle=...`。

### 2. `apply_job.jsp`
- 作用：提交 TA 岗位申请。
- 特点：
  - 从 URL 参数 `jobTitle` 读取所选岗位，并显示在页面上。
  - 表单提交到 `${pageContext.request.contextPath}/ta/submitApplication`。
  - 收集用户：`username`、`coverLetter`、`availability`、`preferredHours`、`additionalInfo`。
  - 提交后可继续返回 `jobHall` 页面。

### 3. `ta_profile.jsp`
- 作用：创建/填写 TA 个人档案信息。
- 特点：
  - 表单提交到 `${pageContext.request.contextPath}/ta/profile`。
  - 收集字段：`username`、`name`、`email`、`phone`、`major`、`gpa`、`experience`、`skills`、`cv`。
  - 适合用于 TA 个人信息注册或简历保存。

## 使用说明
1. 访问 `job_hall.jsp` 浏览岗位。
2. 选择岗位后进入 `apply_job.jsp` 填写申请信息并提交。
3. 若要创建 TA 个人资料，访问 `ta_profile.jsp` 并提交表单。
