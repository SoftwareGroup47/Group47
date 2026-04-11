// 存储操作模块
const Storage = {
    // 密码加密函数
    hashPassword: function(password) {
        const encoder = new TextEncoder();
        const data = encoder.encode(password);
        return crypto.subtle.digest('SHA-256', data)
            .then(hash => {
                const hexString = Array.from(new Uint8Array(hash))
                    .map(b => b.toString(16).padStart(2, '0'))
                    .join('');
                return hexString;
            });
    },
    // 保存数据到本地存储
    save: function(key, data) {
        localStorage.setItem(key, JSON.stringify(data));
    },
    
    // 从本地存储获取数据
    get: function(key) {
        const data = localStorage.getItem(key);
        return data ? JSON.parse(data) : null;
    },
    
    // 初始化存储结构
    init: function() {
        // 初始化用户数据
        if (!this.get('users')) {
            this.save('users', []);
        }
        
        // 初始化岗位数据
        if (!this.get('jobs')) {
            this.save('jobs', [
                {
                    id: '1',
                    title: '算法助教',
                    description: '负责算法课程的助教工作，包括批改作业、辅导学生等。',
                    publisher: '张老师',
                    status: 'Open',
                    moduleCode: 'CS101',
                    capacity: 3
                },
                {
                    id: '2',
                    title: '数据结构助教',
                    description: '负责数据结构课程的助教工作，包括批改作业、辅导学生等。',
                    publisher: '李老师',
                    status: 'Open',
                    moduleCode: 'CS102',
                    capacity: 2
                },
                {
                    id: '3',
                    title: '操作系统助教',
                    description: '负责操作系统课程的助教工作，包括批改作业、辅导学生等。',
                    publisher: '王老师',
                    status: 'Open',
                    moduleCode: 'CS103',
                    capacity: 4
                }
            ]);
        }
        
        // 初始化申请数据
        if (!this.get('applications')) {
            this.save('applications', []);
        }
    },
    
    // 添加用户
    addUser: function(user) {
        const users = this.get('users');
        users.push(user);
        this.save('users', users);
    },
    
    // 根据用户名查找用户
    findUserByUsername: function(username) {
        const users = this.get('users');
        return users.find(user => user.username === username);
    },
    
    // 获取所有岗位
    getJobs: function() {
        return this.get('jobs');
    },
    
    // 添加岗位
    addJob: function(job) {
        const jobs = this.get('jobs');
        jobs.push(job);
        this.save('jobs', jobs);
    },
    
    // 添加申请
    addApplication: function(application) {
        const applications = this.get('applications');
        applications.push(application);
        this.save('applications', applications);
    },
    
    // 获取所有申请
    getApplications: function() {
        return this.get('applications');
    },
    
    // 更新申请状态
    updateApplicationStatus: function(applicationId, status) {
        const applications = this.get('applications');
        const application = applications.find(app => app.id === applicationId);
        if (application) {
            application.status = status;
            this.save('applications', applications);
            return true;
        }
        return false;
    },
    
    // 更新用户信息
    updateUser: function(username, userData) {
        const users = this.get('users');
        const userIndex = users.findIndex(user => user.username === username);
        if (userIndex !== -1) {
            users[userIndex] = { ...users[userIndex], ...userData };
            this.save('users', users);
            return true;
        }
        return false;
    }
};

// 初始化存储
Storage.init();