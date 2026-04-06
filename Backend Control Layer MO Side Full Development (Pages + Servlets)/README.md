# MO端功能项目说明

## 项目结构

```
软工小组作业/
├── src/
│   └── main/
│       ├── java/
│       │   ├── model/          # 实体类
│       │   │   ├── User.java
│       │   │   ├── Job.java
│       │   │   └── Application.java
│       │   ├── servlet/        # Servlet控制器
│       │   │   ├── MoPostJobServlet.java
│       │   │   └── MoCheckApplyServlet.java
│       │   └── util/           # 工具类
│       │       └── FileDBHelper.java
│       └── webapp/
│           ├── mo/             # MO端页面
│           │   ├── post_job.jsp
│           │   └── check_apply.jsp
│           └── WEB-INF/
│               └── web.xml     # 项目配置
├── pom.xml                     # Maven配置
└── README.md                   # 项目说明
```

## 功能说明

### MO端功能

1. **发布岗位**：MO用户可以发布新的岗位，填写岗位名称、要求和发布人信息
2. **查看申请**：MO用户可以查看所有岗位的申请记录

## 技术栈

- **前端**：JSP + HTML + CSS
- **后端**：Java Servlet
- **数据存储**：JSON文件（使用Gson库）
- **构建工具**：Maven

## 环境要求

- Java JDK 8或更高版本
- Maven 3.6或更高版本
- Tomcat 8.5或Tomcat 9.0

## 运行步骤

### 1. 编译项目

在项目根目录执行以下命令：

```bash
mvn clean package
```

这将生成 `target/mo-system.war` 文件。

### 2. 部署到Tomcat

将 `target/mo-system.war` 文件复制到Tomcat的 `webapps` 目录，然后启动Tomcat。

### 3. 访问页面

在浏览器中访问以下地址：

- **发布岗位**：`http://localhost:8080/mo-system/mo/post_job.jsp`
- **查看申请**：`http://localhost:8080/mo-system/mo/check_apply.jsp`

## 测试功能

### 测试发布岗位

1. 在发布岗位页面填写：
   - 岗位名称：例如 "Java开发助教"
   - 岗位要求：例如 "熟悉Java基础，有教学经验"
   - 发布人：例如 "张老师"
2. 点击 "发布岗位" 按钮
3. 页面应显示 "岗位发布成功！" 提示
4. 查看 `jobs.json` 文件，应包含新发布的岗位信息

### 测试查看申请

1. 访问查看申请页面
2. 页面应显示申请列表（如果有申请记录）
3. 若没有申请记录，显示 "暂无申请记录"

## 注意事项

- 数据将保存在项目根目录的 `jobs.json` 和 `applications.json` 文件中
- 确保Tomcat有足够的权限读写这些文件
- 实际项目中需要添加用户认证和权限控制
- 生产环境中应使用数据库而非文件存储

## 与其他成员集成

- **成员1**：提供的FileDBHelper和实体类已集成到项目中
- **成员6**：可将此项目合并到主分支进行整合测试

