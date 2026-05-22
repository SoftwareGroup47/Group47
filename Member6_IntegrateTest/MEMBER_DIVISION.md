# 六人团队分工明细 — BUPT TA Recruitment System

> **分支策略：** 每人独立分支开发，成员6（整合组长）负责Review + Merge到main
> **包名统一：** 所有Java文件保持 `org.example.model / servlet / util` 不动（不然import全炸）

---

## 成员 1：后端基础层 — 数据模型 + 文件工具类 + AI匹配引擎

**分支：** `dev-base-model`
**定位：** 搭建项目底层，所有Servlet都依赖你的代码，别人只调不改

### 代码文件清单

| 文件路径 | 说明 |
|---|---|
| `pom.xml` | Maven项目配置，引入Gson、Jakarta Servlet、JUnit、Jetty插件 |
| `src/main/java/org/example/model/User.java` | 用户实体类：username / password(SHA-256) / role(TA或MO) |
| `src/main/java/org/example/model/Job.java` | 岗位实体类：jobId(UUID) / jobName / requirements / moName / status |
| `src/main/java/org/example/model/Application.java` | 申请实体类：id / jobName / applicant / applyTime / status(Pending/Accepted/Rejected) |
| `src/main/java/org/example/model/TAProfile.java` | TA档案实体：username / skills / grades / cvPath |
| `src/main/java/org/example/util/FileDBHelper.java` | 文件读写工具类（~\\tarecruit_data\\ 目录，管道分隔txt） |
| `src/main/java/org/example/util/AIMatchService.java` | **AI技能匹配引擎（核心），含同义词映射 + 三维度评分算法** |

### 核心工作

1. 实现无数据库持久化：用户→`users.txt`，岗位→`jobs.txt`，申请→`applications.txt`，TA档案→`ta_profiles.txt`
2. SHA-256密码哈希，synchronized写操作保证线程安全
3. **AI匹配引擎**：精确匹配(40%) + 同义词模糊匹配(35%) + 部分包含(25%) → 加权得分
4. 15+组技能同义词映射表（Java↔J2EE、ML↔Machine Learning、JavaScript↔Node.js等）
5. 工作量统计方法（getTAWorkload、getWorkloadAdvice等，供成员4/5/6调用）

### 中期产出
- [x] pom.xml 配置完毕
- [x] 4个Model类完整可用
- [x] FileDBHelper读写工具全部CRUD方法
- [x] AIMatchService匹配引擎 + 同义词库

---

## 成员 2：前端页面组（公共） — 首页 + 登录/注册 + 公共资源

**分支：** `dev-web-common`
**定位：** 系统公共前端页面，纯JSP/HTML/CSS/JS，不写Java逻辑

### 代码文件清单

| 文件路径 | 说明 |
|---|---|
| `src/main/webapp/index.jsp` | 系统首页（BUPT logo + 登录/注册按钮） |
| `src/main/webapp/login.jsp` | 登录页面（用户名 + 密码 + 角色识别） |
| `src/main/webapp/register.jsp` | 注册页面（用户名 + 密码 + TA/MO角色选择） |
| `src/main/webapp/css/style.css` | 全局样式（Glass-morphism设计、动画、响应式） |
| `src/main/webapp/js/storage.js` | 前端存储辅助JS |
| `src/main/webapp/js/fileUpload.js` | 文件上传前端交互JS |
| `src/main/webapp/images/bg.png` | 背景图 |
| `src/main/webapp/images/bupt-logo.png` | BUPT Logo |
| `src/main/webapp/images/bupt-campus.png` | 校园图 |

### 核心工作

1. 按原型图做静态页面，页面风格统一（Glass-morphism卡片 + circuit-board背景）
2. 表单提交action指向对应Servlet（RegisterServlet、LoginServlet）
3. CSS全部集中在style.css，方便全组复用
4. 公共JS保持独立，不耦合业务逻辑

### 中期产出
- [x] 首页可访问，美观大方
- [x] 登录页表单正确提交到LoginServlet
- [x] 注册页含TA/MO角色下拉框，提交到RegisterServlet
- [x] 全局CSS/JS/图片就绪，供成员3/5引用

> **⏳ 注意：** 你自己的页面不需要`<jsp:include>`，因为你的三个页面已经是顶层入口页了。成员3和成员5的页面可以从你的style.css复用样式。

---

## 成员 3：前端页面组（TA端） — TA功能页面 + AI匹配前端

**分支：** `dev-web-ta`
**定位：** TA用户所有前端界面，纯JSP页面开发，调用成员4的Servlet

### 代码文件清单

| 文件路径 | 说明 |
|---|---|
| `src/main/webapp/job_hall.jsp` | 岗位大厅（浏览所有Open岗位 + **AI匹配分析面板**） |
| `src/main/webapp/ta_profile.jsp` | TA个人档案（填技能/成绩/上传CV + **工作量分析面板**） |
| `src/main/webapp/my_applications.jsp` | 我的申请（查看申请状态：Pending/Accepted/Rejected） |

### AI匹配相关（前台展示）

| 页面 | AI功能 |
|---|---|
| `job_hall.jsp` | 每个岗位卡片内嵌AI匹配面板：百分比得分、星级、Exact/Fuzzy/Partial细分、缺失技能列表、推荐建议、当前工作量 |
| `ta_profile.jsp` | Workload Analysis面板：Total Applied / Accepted / Pending 统计 + AI工作量建议 |

### 核心工作

1. job_hall.jsp 通过AJAX调 `/AIMatchServlet?action=matchJob&jobId=xxx` 获取匹配结果JSON并渲染
2. ta_profile.jsp 通过AJAX调 `/AIMatchServlet?action=workload` 获取工作量数据
3. 申请按钮调 `/ApplyJobServlet`
4. 查看状态从 `/TAApplicationStatusServlet` 获取数据

### 中期产出
- [x] 岗位大厅含AI匹配展示
- [x] 个人档案含工作量面板
- [x] 我的申请含状态表格

---

## 成员 4：后端控制层（TA端） — TA全流程Servlet + AI匹配API

**分支：** `dev-servlet-ta`
**定位：** TA用户所有业务逻辑 + 提供AI匹配REST API，调用成员1的FileDBHelper和AIMatchService

### 代码文件清单

| 文件路径 | 说明 |
|---|---|
| `src/main/java/org/example/servlet/RegisterServlet.java` | 用户注册处理（验重 + 调FileDBHelper.registerUser） |
| `src/main/java/org/example/servlet/LoginServlet.java` | 登录校验 + Session创建 + 角色路由（TA→job_hall, MO→post_job） |
| `src/main/java/org/example/servlet/TaProfileServlet.java` | TA档案保存/更新 + CV上传 |
| `src/main/java/org/example/servlet/ApplyJobServlet.java` | 岗位申请提交（防重复） |
| `src/main/java/org/example/servlet/TAApplicationStatusServlet.java` | TA查看自己的申请列表 |
| `src/main/java/org/example/servlet/AIMatchServlet.java` | **AI匹配REST API（3个action：matchJob/workload/analyzeApplication）** |
| `src/main/java/org/example/util/FileUploadHelper.java` | CV文件上传工具（~\\tarecruit_uploads\\） |

### AI匹配相关（后端）

| 文件 | 说明 |
|---|---|
| `AIMatchServlet.java` | 核心REST API，返回JSON，被成员3/5的前端AJAX调用 |
| `action=matchJob` | TA查岗位匹配度：score + missingSkills + suggestion + workload |
| `action=workload` | TA查自己的工作量：workload + accepted + pending + advice |
| `action=analyzeApplication` | MO查某个申请人的匹配分析（被成员5的check_apply.jsp调用） |

### 核心工作

1. 接收前端表单，调FileDBHelper读写，实现TA端全流程
2. **AIMatchServlet** 是桥梁：从成员1的AIMatchService拿数据，封装JSON返回前端
3. 所有Servlet做Session校验 + 角色校验 + 输入校验

### 中期产出
- [x] 注册/登录全流程可用
- [x] TA档案保存含CV上传
- [x] 岗位申请含防重复
- [x] AI匹配API三接口可返回正确JSON

---

## 成员 5：后端控制层（MO端） — MO全栈（页面 + Servlet + AI分析）

**分支：** `dev-mo-full`
**定位：** MO用户所有功能，前端+后端一体，调用成员1的FileDBHelper和成员4的AIMatchServlet

### 代码文件清单

| 文件路径 | 说明 |
|---|---|
| `src/main/webapp/mo/post_job.jsp` | MO发布岗位页面 |
| `src/main/webapp/mo/check_apply.jsp` | MO查看/审核申请（含**可折叠AI分析面板**） |
| `src/main/java/org/example/servlet/MoPostJobServlet.java` | MO发布岗位逻辑 |
| `src/main/java/org/example/servlet/MoCheckApplyServlet.java` | MO查看申请列表 + 接受/拒绝操作 |

### AI匹配相关

| 页面/功能 | AI使用方式 |
|---|---|
| `check_apply.jsp` 的AI分析面板 | 通过AJAX调 `/AIMatchServlet?action=analyzeApplication` 获取每个申请人的Match Score + Missing Skills + Workload分析 |
| 面板展示内容 | Score百分比、Exact/Fuzzy/Partial细分、缺失技能、申请人工作量、AI建议 |

### 核心工作

1. post_job.jsp + MoPostJobServlet：输入岗位名+技能要求，调FileDBHelper.addJob持久化
2. check_apply.jsp + MoCheckApplyServlet：列出所有申请，MO点Accept/Reject，调FileDBHelper.updateApplicationStatus
3. 每个申请可展开AI分析面板（调成员4的AIMatchServlet?action=analyzeApplication）

### 中期产出
- [x] 发布岗位可用
- [x] 申请列表 + Accept/Reject可用
- [x] AI分析面板可折叠展示

---

## 成员 6：项目整合 + 测试 + 配置管理 + 管理员面板

**分支：** `dev-integrate-test`
**定位：** 全员代码岗（非纯管理），负责配置、整合、测试、GitHub管理 + 管理员仪表盘

### 代码文件清单

| 文件路径 | 说明 |
|---|---|
| `src/main/webapp/WEB-INF/web.xml` | Servlet映射配置 + Welcome页面 |
| `src/main/webapp/admin_workload.jsp` | 管理员工作负载仪表盘（所有TA概览 + AI建议） |
| `src/main/java/org/example/servlet/AdminWorkloadServlet.java` | 管理员Servlet，聚合所有TA工作量数据，用AIMatchService生成建议 |
| `src/test/java/org/example/model/ModelTest.java` | 模型类单元测试 |
| `src/test/java/org/example/util/AIMatchServiceTest.java` | **AI匹配引擎测试（17个测试用例）** |
| `src/test/java/org/example/util/FileDBHelperTest.java` | **文件数据库测试（17个测试用例，CRUD全覆盖）** |
| `README.md` | 项目说明 + 环境配置 + 运行指南 |
| `USER_MANUAL.md` | 用户操作手册（对应`User_Manual_Group47.docx`） |

### AI匹配相关

| 文件 | AI职责 |
|---|---|
| `AdminWorkloadServlet.java` | 调AIMatchService.getAllTAUsernames + getTAWorkload + getWorkloadAdvice，聚合所有TA数据 |
| `admin_workload.jsp` | 展示所有TA工作量：Green/Orange/Red条形指示 + 每人AI建议 + Profile警告 |
| `AIMatchServiceTest.java` | 验证匹配算法正确性（精确/同义词/部分/边界） |
| `FileDBHelperTest.java` | 验证数据持久化正确性 |

### 核心工作

1. 配置web.xml：8个Servlet映射全部注册
2. AdminWorkloadServlet + admin_workload.jsp：管理员面板，展示所有TA工作量总览
3. 写3个测试类，至少覆盖注册/登录/发布岗位/申请/接受拒绝全流程
4. 审核所有分支代码、解决merge冲突、合并到main
5. 确保GitHub提交记录完整（每人独立分支 → main的merge记录清晰）
6. README写清楚JDK版本、Maven启动命令（`mvn jetty:run`）、访问地址（`localhost:8888/SE_group47`）

### 中期产出
- [x] web.xml完整配置
- [x] 管理员仪表盘可用
- [x] 3个测试类全部通过
- [x] README完整可运行
- [x] 所有分支合并到main

---

## 文件归属总览（快速索引）

```
项目根目录/
├── pom.xml                                          → 成员1
├── README.md                                        → 成员6
├── USER_MANUAL.md                                   → 成员6
├── MEMBER_DIVISION.md                               → 本文档
│
├── src/main/java/org/example/
│   ├── Main.java                                    → 成员6（演示/整合用）
│   ├── SimpleHTTPServer.java                        → 成员6（备用）
│   ├── model/
│   │   ├── User.java                                → 成员1
│   │   ├── Job.java                                 → 成员1
│   │   ├── Application.java                         → 成员1
│   │   └── TAProfile.java                           → 成员1
│   ├── servlet/
│   │   ├── RegisterServlet.java                     → 成员4
│   │   ├── LoginServlet.java                        → 成员4
│   │   ├── TaProfileServlet.java                    → 成员4
│   │   ├── ApplyJobServlet.java                     → 成员4
│   │   ├── TAApplicationStatusServlet.java          → 成员4
│   │   ├── AIMatchServlet.java                      → 成员4（AI匹配API）
│   │   ├── MoPostJobServlet.java                    → 成员5
│   │   ├── MoCheckApplyServlet.java                 → 成员5
│   │   └── AdminWorkloadServlet.java                → 成员6（管理员面板）
│   └── util/
│       ├── FileDBHelper.java                        → 成员1
│       ├── AIMatchService.java                      → 成员1（AI匹配引擎核心）
│       └── FileUploadHelper.java                    → 成员4（CV上传）
│
├── src/test/java/org/example/
│   ├── model/ModelTest.java                         → 成员6
│   └── util/
│       ├── AIMatchServiceTest.java                  → 成员6（AI测试）
│       └── FileDBHelperTest.java                    → 成员6
│
└── src/main/webapp/
    ├── WEB-INF/web.xml                              → 成员6
    ├── index.jsp                                    → 成员2
    ├── login.jsp                                    → 成员2
    ├── register.jsp                                 → 成员2
    ├── job_hall.jsp                                 → 成员3（含AI匹配UI）
    ├── ta_profile.jsp                               → 成员3（含工作量面板）
    ├── my_applications.jsp                          → 成员3
    ├── admin_workload.jsp                           → 成员6（管理员AI总览）
    ├── mo/
    │   ├── post_job.jsp                             → 成员5
    │   └── check_apply.jsp                          → 成员5（含AI分析面板）
    ├── css/style.css                                → 成员2
    ├── js/
    │   ├── storage.js                               → 成员2
    │   └── fileUpload.js                            → 成员2
    └── images/
        ├── bg.png                                   → 成员2
        ├── bupt-logo.png                            → 成员2
        └── bupt-campus.png                          → 成员2
```

---

## AI匹配模块分配总览

| AI组件 | 归属成员 | 说明 |
|---|---|---|
| `AIMatchService.java` — 匹配算法引擎 + 同义词库 | **成员1** | 底层工具，所有人依赖 |
| `AIMatchServlet.java` — REST API (JSON返回) | **成员4** | 被成员3和成员5的前端AJAX调用 |
| `job_hall.jsp` 的AI匹配面板 | **成员3** | TA端：每个岗位的匹配分+缺失技能 |
| `ta_profile.jsp` 的工作量面板 | **成员3** | TA端：工作量统计+AI建议 |
| `mo/check_apply.jsp` 的AI分析面板 | **成员5** | MO端：查看申请人的匹配分析 |
| `AdminWorkloadServlet` + `admin_workload.jsp` | **成员6** | 管理员：所有TA的工作量+AI建议总览 |
| `AIMatchServiceTest.java` | **成员6** | 验证匹配算法17个case |

---

## 依赖关系（开发顺序）

```
成员1（基础Model + Util）← 所有人依赖
    ↓
成员2（公共前端）→ 独立开发，无需等别人
成员3（TA前端）  → 等成员4写Servlet后才能调通，但页面可以先写
成员4（TA Servlet）→ 依赖成员1，被成员3和5依赖
成员5（MO全栈）  → 依赖成员1+成员4的AIMatchServlet
    ↓
成员6（整合测试）→ 等所有人完成后合并测试
```

---

## Git 分支规范

```
main                    ← 成员6管理，只接受merge
├── dev-base-model      ← 成员1
├── dev-web-common      ← 成员2
├── dev-web-ta          ← 成员3
├── dev-servlet-ta      ← 成员4
├── dev-mo-full         ← 成员5
└── dev-integrate-test  ← 成员6
```

**工作流：**
1. 每人从main切出自己的分支
2. 在自己的分支上开发、提交
3. 完成后发PR给成员6
4. 成员6 Review → 解决冲突 → Merge到main
