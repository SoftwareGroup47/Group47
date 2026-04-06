# MO端功能实现说明

## 1. 实现内容

根据成员5的任务要求，我已完成以下文件的开发：

### 前端页面
- `webapp/mo/post_job.jsp` - MO发布岗位页面
- `webapp/mo/check_apply.jsp` - MO查看申请页面

### 后端Servlet
- `servlet/MoPostJobServlet.java` - MO发布岗位逻辑
- `servlet/MoCheckApplyServlet.java` - MO查看申请逻辑

## 2. 功能说明

### 2.1 发布岗位功能
- **页面路径**：`/mo/post_job.jsp`
- **功能**：MO用户可以发布新的岗位，填写岗位名称、要求和发布人信息
- **数据存储**：岗位信息将保存到`jobs.json`文件中
- **验证**：所有字段为必填项，确保数据完整性

### 2.2 查看申请功能
- **页面路径**：`/mo/check_apply.jsp`
- **功能**：MO用户可以查看所有岗位的申请记录
- **数据加载**：从`applications.json`文件中加载申请数据
- **展示**：以表格形式展示申请ID、岗位名称、申请人、申请时间和状态

## 3. 技术实现

### 3.1 前端实现
- 使用JSP + HTML + CSS开发
- 响应式设计，适配不同屏幕尺寸
- 表单验证和错误提示
- 美观的用户界面

### 3.2 后端实现
- 使用Java Servlet技术
- 模拟FileDBHelper类（实际项目中由成员1提供）
- 数据验证和异常处理
- 符合MVC架构设计

## 4. 使用方法

1. 将所有文件复制到对应的目录结构中
2. 确保项目已配置好Servlet环境
3. 启动Web服务器
4. 访问以下路径：
   - 发布岗位：`http://localhost:8080/项目名/mo/post_job.jsp`
   - 查看申请：`http://localhost:8080/项目名/mo/check_apply.jsp`

## 5. 注意事项

- 实际项目中需要移除模拟的FileDBHelper和实体类，使用成员1提供的实现
- 需要在web.xml中配置Servlet映射（如果不使用注解方式）
- 需要确保项目中已引入必要的依赖（如servlet-api）
- 实际部署时需要创建相应的目录结构

## 6. 后续工作

- 与成员1的文件工具类集成
- 与成员6的项目整合
- 进行功能测试和bug修复
- 完善用户界面和用户体验