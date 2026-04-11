# BUPT TA Recruitment System

北京邮电大学助教招聘系统

## 项目概述

本项目是一个基于Java Web的助教招聘系统，旨在为北京邮电大学的课程负责人（MO）和教学助理（TA）提供一个便捷的招聘与申请平台。系统支持用户注册、登录、岗位发布、岗位申请、申请管理等功能，并通过本地文件存储实现数据持久化。

## 技术栈

- **前端**：HTML5, CSS3, JavaScript
- **后端**：Java, Servlet, JSP
- **数据存储**：本地文件存储（JSON格式）
- **构建工具**：Maven
- **依赖**：Gson（JSON序列化/反序列化）

## 项目结构

```
SE_group47/
├── pom.xml                    # Maven配置文件
├── README.md                  # 项目说明
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── model/        # 数据模型
│   │   │   │   ├── User.java  # 用户实体类
│   │   │   │   └── Job.java   # 岗位实体类
│   │   │   ├── servlet/       # Servlet控制器
│   │   │   │   ├── RegisterServlet.java     # 用户注册处理
│   │   │   │   ├── LoginServlet.java        # 用户登录校验
│   │   │   │   ├── TaProfileServlet.java    # TA档案保存
│   │   │   │   ├── ApplyJobServlet.java     # 岗位申请提交
│   │   │   │   ├── MoPostJobServlet.java    # MO发布岗位逻辑
│   │   │   │   └── MoCheckApplyServlet.java # MO查看申请逻辑
│   │   │   └── util/          # 工具类
│   │   │       └── FileDBHelper.java        # 文件读写工具类
│   │   └── webapp/            # 前端页面
│   │       ├── common/        # 公共组件
│   │       │   ├── header.jsp # 公共头部
│   │       │   └── sidebar.jsp # 公共侧边栏
│   │       ├── ta/            # TA端页面
│   │       │   ├── ta_profile.jsp # TA创建档案页面
│   │       │   ├── job_hall.jsp   # TA浏览岗位页面
│   │       │   └── apply_job.jsp  # TA申请岗位页面
│   │       ├── mo/            # MO端页面
│   │       │   ├── post_job.jsp    # MO发布岗位页面
│   │       │   └── check_apply.jsp # MO查看申请页面
│   │       ├── login.jsp      # 登录页面
│   │       ├── register.jsp    # 注册页面
│   │       └── WEB-INF/
│   │           └── web.xml     # Servlet配置
│   └── test/
│       └── java/
│           └── ProjectTest.java # 功能测试代码
└── start_server.py            # 启动服务器脚本
```

## 运行指南

### 1. 环境要求

- JDK 8或更高版本
- Maven 3.0或更高版本
- Tomcat 8.5或更高版本

### 2. 构建项目

```bash
# 进入项目目录
cd SE_group47

# 构建项目
mvn clean package
```

### 3. 部署到Tomcat

1. 将生成的WAR文件复制到Tomcat的webapps目录
2. 启动Tomcat服务器
3. 访问 `http://localhost:8080/SE_group47`

### 4. 本地开发运行

```bash
# 启动简易HTTP服务器
python start_server.py

# 访问 http://localhost:8082
```

## 功能模块

### 1. 用户认证模块
- 用户注册（支持TA/MO角色）
- 用户登录
- 密码加密存储
- 角色权限控制

### 2. TA端功能
- 个人档案管理（技能、成绩、简历上传）
- 岗位浏览与搜索
- 岗位申请
- 申请状态查询

### 3. MO端功能
- 岗位发布
- 申请管理（查看、审核）
- 申请人资料查看

### 4. 管理员功能
- TA工作负荷分析
- 系统管理

## 团队成员职责

### 成员 1：后端基础层・数据模型 + 文件工具类开发
- **唯一核心**：搭建项目底层数据与文件读写，所有人依赖但不修改你的代码
- **独立代码文件**：
  - pom.xml：引入 Gson 依赖（实现 JSON 序列化）
  - model/User.java：用户实体类（学号、姓名、身份、密码）
  - model/Job.java：岗位实体类（岗位名、要求、发布人）
  - util/FileDBHelper.java：文件读写工具类（新增 / 查询 / 保存数据到本地 JSON）
- **核心工作**：
  - 实现无数据库存储，把用户 / 岗位数据写入users.json/jobs.json，供所有 Servlet 调用
- **GitHub 分支**：dev-base-model
- **中期产出**：项目基础架构、数据模型、文件工具类

### 成员 2：前端页面组・公共页面 + 登录 / 注册页开发
- **独立负责**：系统公共前端页面，纯 JSP/HTML/CSS，无后端逻辑
- **独立代码文件**：
  - webapp/common/header.jsp：公共头部
  - webapp/common/sidebar.jsp：公共侧边栏
  - webapp/login.jsp：登录页面
  - webapp/register.jsp：注册页面
- **核心工作**：
  - 按原型图做静态页面，用<jsp:include>复用公共组件，纯前端独立开发
- **GitHub 分支**：dev-web-common
- **中期产出**：登录 / 注册 + 公共页面，可直接对接 Servlet

### 成员 3：前端页面组・TA 端功能页面开发
- **独立负责**：TA 用户所有前端界面，纯页面开发
- **独立代码文件**：
  - webapp/ta/ta_profile.jsp：TA 创建档案页面
  - webapp/ta/job_hall.jsp：TA 浏览岗位页面
  - webapp/ta/apply_job.jsp：TA 申请岗位页面
- **核心工作**：
  - 开发 TA 端所有操作页面，表单提交对接对应 Servlet
- **GitHub 分支**：dev-web-ta
- **中期产出**：TA 端全套功能页面

### 成员 4：后端控制层・TA 端 Servlet 开发
- **独立负责**：TA 用户所有业务逻辑，纯 Servlet 开发，调用成员 1 的工具类
- **独立代码文件**：
  - servlet/RegisterServlet.java：用户注册处理
  - servlet/LoginServlet.java：用户登录校验
  - servlet/TaProfileServlet.java：TA 档案保存
  - servlet/ApplyJobServlet.java：岗位申请提交
- **核心工作**：
  - 接收前端表单数据，调用FileDBHelper读写文件，实现 TA 端全流程逻辑
- **GitHub 分支**：dev-servlet-ta
- **中期产出**：TA 端全套 Servlet 控制器

### 成员 5：后端控制层・MO 端功能全开发（页面 + Servlet）
- **独立负责**：MO 用户所有功能，前端页面 + 后端 Servlet 一体开发
- **独立代码文件**：
  - webapp/mo/post_job.jsp：MO 发布岗位页面
  - webapp/mo/check_apply.jsp：MO 查看申请页面
  - servlet/MoPostJobServlet.java：MO 发布岗位逻辑
  - servlet/MoCheckApplyServlet.java：MO 查看申请逻辑
- **核心工作**：
  - 开发 MO 端页面 + 业务逻辑，对接文件工具类，实现岗位发布 / 申请查看
- **GitHub 分支**：dev-mo-full
- **中期产出**：MO 端全套页面 + Servlet

### 成员 6：项目整合 + 测试 + 配置管理
- **独立负责**：项目配置、模块整合、功能测试、GitHub 合并（全员代码岗，非纯管理）
- **独立代码文件**：
  - webapp/WEB-INF/web.xml：Servlet / 项目配置
  - test/ProjectTest.java：功能测试代码
  - README.md：项目说明 + 运行指南
- **核心工作**：
  - 配置项目启动环境，整合所有成员代码
  - 编写测试代码，验证登录 / 注册 / 岗位流程
  - 审核分支、合并代码到 main，确保 GitHub 提交记录完整
- **GitHub 分支**：dev-integrate-test
- **中期产出**：可运行的完整 Web 项目、测试报告、项目配置

## 隐私保护与伦理规范

- **密码加密**：用户密码使用SHA-256算法加密存储
- **数据存储**：所有数据仅存储在本地浏览器或服务器本地文件中
- **隐私政策**：详细的隐私保护政策，明确数据使用范围
- **伦理规范**：确保招聘过程公平公正，尊重用户隐私

## 测试指南

### 功能测试

1. **用户注册**：测试TA和MO角色的注册功能
2. **用户登录**：测试不同角色的登录功能
3. **TA端功能**：测试个人档案管理、岗位浏览、岗位申请
4. **MO端功能**：测试岗位发布、申请管理
5. **管理员功能**：测试TA工作负荷分析

### 性能测试

- 测试系统在多用户同时操作时的响应速度
- 测试文件读写操作的性能

## 未来改进方向

1. **数据库集成**：使用MySQL等关系型数据库替代文件存储
2. **用户界面优化**：使用现代前端框架提升用户体验
3. **移动端适配**：开发移动端友好的界面
4. **消息通知**：实现申请状态变更的通知功能
5. **数据分析**：提供更详细的TA工作负荷分析报告

## 许可证

本项目仅供教育目的使用，基于MIT许可证。

## 联系方式

如有问题，请联系项目组：
- 邮箱：ta-recruitment@bupt.edu.cn
- 地址：北京市海淀区西土城路10号北京邮电大学
