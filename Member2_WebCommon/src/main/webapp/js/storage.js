// Storage Operations Module
const DATA_VERSION = '2'; // Increment to force-reset all cached data
const Storage = {
    // Password hashing function
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
    // Save data to localStorage
    save: function(key, data) {
        localStorage.setItem(key, JSON.stringify(data));
    },
    
    // Get data from localStorage
    get: function(key) {
        const data = localStorage.getItem(key);
        return data ? JSON.parse(data) : null;
    },
    
    // Initialize storage structure
    init: function() {
        // Force reset if data version changed (e.g. after translation)
        if (this.get('dataVersion') !== DATA_VERSION) {
            localStorage.clear();
            this.save('dataVersion', DATA_VERSION);
        }
        
        // Initialize user data
        if (!this.get('users')) {
            this.save('users', []);
        }
        
        // Initialize job data
        if (!this.get('jobs')) {
            this.save('jobs', [
                {
                    id: '1',
                    title: 'Algorithms TA',
                    description: 'Responsible for assisting with the Algorithms course, including grading assignments, tutoring students, etc.',
                    publisher: 'Prof. Zhang',
                    status: 'Open',
                    moduleCode: 'CS101',
                    capacity: 3,
                    requirements: 'Algorithms, Data Structures, Java, Python'
                },
                {
                    id: '2',
                    title: 'Data Structures TA',
                    description: 'Responsible for assisting with the Data Structures course, including grading assignments, tutoring students, etc.',
                    publisher: 'Prof. Li',
                    status: 'Open',
                    moduleCode: 'CS102',
                    capacity: 2,
                    requirements: 'Data Structures, C++, Java, Algorithms'
                },
                {
                    id: '3',
                    title: 'Operating Systems TA',
                    description: 'Responsible for assisting with the Operating Systems course, including grading assignments, tutoring students, etc.',
                    publisher: 'Prof. Wang',
                    status: 'Open',
                    moduleCode: 'CS103',
                    capacity: 4,
                    requirements: 'Operating Systems, C, Linux, Shell'
                }
            ]);
        }
        
        // Initialize application data
        if (!this.get('applications')) {
            this.save('applications', []);
        }
    },
    
    // Add user
    addUser: function(user) {
        const users = this.get('users');
        users.push(user);
        this.save('users', users);
    },
    
    // Find user by username
    findUserByUsername: function(username) {
        const users = this.get('users');
        return users.find(user => user.username === username);
    },
    
    // Get all jobs
    getJobs: function() {
        return this.get('jobs');
    },
    
    // Add job
    addJob: function(job) {
        const jobs = this.get('jobs');
        jobs.push(job);
        this.save('jobs', jobs);
    },
    
    // Add application
    addApplication: function(application) {
        const applications = this.get('applications');
        applications.push(application);
        this.save('applications', applications);
    },
    
    // Get all applications
    getApplications: function() {
        return this.get('applications');
    },
    
    // Update application status
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
    
    // Update user info
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

// Initialize storage
Storage.init();