// 文件上传模块
const FileUpload = {
    // 存储文件对象的内存缓存
    fileCache: {},
    
    // 模拟文件上传
    upload: function(file) {
        // 检查文件类型
        if (file.type !== 'application/pdf' && file.type !== 'application/vnd.openxmlformats-officedocument.wordprocessingml.document') {
            return Promise.resolve({ success: false, message: '只能上传PDF或DOCX格式的文件' });
        }
        
        // 检查文件大小（限制为2MB）
        if (file.size > 2 * 1024 * 1024) {
            return Promise.resolve({ success: false, message: '文件大小不能超过2MB' });
        }
        
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const fileContent = e.target.result;
                
                // 生成文件ID
                const fileId = Date.now().toString();
                const fileName = file.name;
                const fileType = file.type;
                
                // 存储文件到内存缓存
                FileUpload.fileCache[fileId] = file;
                
                // 存储文件信息到本地存储
                try {
                    const files = JSON.parse(localStorage.getItem('files') || '{}');
                    files[fileId] = {
                        id: fileId,
                        name: fileName,
                        type: fileType,
                        content: fileContent
                    };
                    localStorage.setItem('files', JSON.stringify(files));
                    
                    resolve({ success: true, fileId: fileId, fileName: fileName });
                } catch (error) {
                    console.error('文件存储失败:', error);
                    reject({ success: false, message: '文件存储失败，可能是文件太大' });
                }
            };
            reader.onerror = function() {
                reject({ success: false, message: '文件读取失败' });
            };
            reader.readAsDataURL(file);
        });
    },
    
    // 获取文件
    getFile: function(fileId) {
        try {
            // 先从内存缓存中获取
            if (this.fileCache[fileId]) {
                return this.fileCache[fileId];
            }
            
            // 从本地存储中获取文件信息
            const files = JSON.parse(localStorage.getItem('files') || '{}');
            const fileInfo = files[fileId];
            
            if (fileInfo && fileInfo.content) {
                // 从Data URL创建Blob对象
                const blob = this.dataURLToBlob(fileInfo.content);
                if (blob) {
                    // 创建File对象
                    const file = new File([blob], fileInfo.name, { type: fileInfo.type });
                    // 存储到内存缓存
                    this.fileCache[fileId] = file;
                    return file;
                }
            }
            
            return fileInfo;
        } catch (error) {
            console.error('获取文件失败:', error);
            return null;
        }
    },
    
    // 获取文件URL
    getFileUrl: function(fileId) {
        const file = this.getFile(fileId);
        if (file) {
            // 使用Blob URL代替Data URL
            if (file instanceof File) {
                return URL.createObjectURL(file);
            } else if (file.content) {
                // 兼容旧的Data URL格式
                return file.content;
            }
        }
        return null;
    },
    
    // Data URL转换为Blob对象
    dataURLToBlob: function(dataURL) {
        try {
            const arr = dataURL.split(',');
            const mime = arr[0].match(/:(.*?);/)[1];
            const bstr = atob(arr[1]);
            let n = bstr.length;
            const u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new Blob([u8arr], { type: mime });
        } catch (error) {
            console.error('Data URL转换失败:', error);
            return null;
        }
    }
};