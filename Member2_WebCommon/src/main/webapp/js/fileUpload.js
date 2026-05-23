// File Upload Module
const FileUpload = {
    // In-memory cache for file objects
    fileCache: {},
    
    // Simulated file upload
    upload: function(file) {
        // Check file type
        if (file.type !== 'application/pdf' && file.type !== 'application/vnd.openxmlformats-officedocument.wordprocessingml.document') {
            return Promise.resolve({ success: false, message: 'Only PDF or DOCX files are allowed.' });
        }
        
        // Check file size (limit 2MB)
        if (file.size > 2 * 1024 * 1024) {
            return Promise.resolve({ success: false, message: 'File size cannot exceed 2MB.' });
        }
        
        return new Promise((resolve, reject) => {
            const reader = new FileReader();
            reader.onload = function(e) {
                const fileContent = e.target.result;
                
                // Generate file ID
                const fileId = Date.now().toString();
                const fileName = file.name;
                const fileType = file.type;
                
                // Store file in memory cache
                FileUpload.fileCache[fileId] = file;
                
                // Store file info to localStorage
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
                    console.error('File storage failed:', error);
                    reject({ success: false, message: 'File storage failed. The file may be too large.' });
                }
            };
            reader.onerror = function() {
                reject({ success: false, message: 'File read failed.' });
            };
            reader.readAsDataURL(file);
        });
    },
    
    // Get file
    getFile: function(fileId) {
        try {
            // First try from memory cache
            if (this.fileCache[fileId]) {
                return this.fileCache[fileId];
            }
            
            // Get file info from localStorage
            const files = JSON.parse(localStorage.getItem('files') || '{}');
            const fileInfo = files[fileId];
            
            if (fileInfo && fileInfo.content) {
                // Create Blob object from Data URL
                const blob = this.dataURLToBlob(fileInfo.content);
                if (blob) {
                    // Create File object
                    const file = new File([blob], fileInfo.name, { type: fileInfo.type });
                    // Store in memory cache
                    this.fileCache[fileId] = file;
                    return file;
                }
            }
            
            return fileInfo;
        } catch (error) {
            console.error('Failed to get file:', error);
            return null;
        }
    },
    
    // Get file URL
    getFileUrl: function(fileId) {
        const file = this.getFile(fileId);
        if (file) {
            // Use Blob URL instead of Data URL
            if (file instanceof File) {
                return URL.createObjectURL(file);
            } else if (file.content) {
                // Compatible with old Data URL format
                return file.content;
            }
        }
        return null;
    },
    
    // Convert Data URL to Blob object
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
            console.error('Data URL conversion failed:', error);
            return null;
        }
    }
};