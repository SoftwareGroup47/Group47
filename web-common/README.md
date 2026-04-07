# BUPT TA System Frontend

## Project Introduction
This is the frontend page for Beijing University of Posts and Telecommunications Teaching Assistant System, including login and registration functions.

## Environment Requirements
- Java JDK 8 or higher
- Apache Tomcat 8.5 or higher

## Deployment Steps
1. **Install Tomcat**: Download and install Apache Tomcat server
2. **Deploy Project**:
   - Copy the `web-common` folder to Tomcat's `webapps` directory
   - Example: `C:\Program Files\Apache Software Foundation\Tomcat 11.0\webapps`
3. **Start Tomcat**:
   - Run Tomcat's `startup.bat` script (Windows)
   - Or start via Tomcat service manager

## Access Method
After deployment, access the following addresses in your browser:

- **Login Page**: `http://localhost:8080/web-common/login.jsp`
- **Register Page**: `http://localhost:8080/web-common/register.jsp`

## Notes
- Default port is 8080, use the corresponding port if you have modified Tomcat's port
- This is a static demo page with no backend login logic, data submission will not be actually verified
- The page includes basic responsive design, supporting different screen sizes

## Project Structure
```
web-common/
├── common/
│   ├── header.jsp      # Page header
│   └── sidebar.jsp     # Sidebar
├── login.jsp           # Login page
├── register.jsp        # Register page
└── README.md           # This README file
```