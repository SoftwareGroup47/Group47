# MO Backend Control Layer

## Project Overview

This component serves as the backend control layer for the Management Office (MO) side of a job management system. It handles core business logic for job posting and application management, enabling MO users to publish job openings and review applications through dedicated interfaces.

## Features

### MO-side Functions

1. **Job Posting**: MO users can publish new job positions by providing job title, requirements, and publisher information
2. **Application Review**: MO users can view all application records for posted jobs

## Project Structure

```
src/
└── main/
    ├── java/
    │   ├── model/          # Entity classes
    │   ├── servlet/        # Servlet controllers
    │   └── util/           # Utility classes
    └── webapp/
        ├── mo/             # MO-side pages
        │   ├── post_job.jsp
        │   └── check_apply.jsp
        └── WEB-INF/
            └── web.xml     # Project configuration
```

## Access URLs

- **Job Posting**: `http://localhost:8080/mo-system/mo/post_job.jsp`
- **Application Review**: `http://localhost:8080/mo-system/mo/check_apply.jsp`

## Environment Requirements

- Java JDK 8 or higher
- Maven 3.6 or higher
- Tomcat 8.5 or Tomcat 9.0

## Deployment Steps

1. **Build the project**
   ```bash
   mvn clean package
   ```
   This will generate `target/mo-system.war` file.

2. **Deploy to Tomcat**
   Copy the `target/mo-system.war` file to Tomcat's `webapps` directory, then start Tomcat.

3. **Access the application**
   Open a browser and navigate to the URLs mentioned above.

## Usage

### Job Posting
1. Fill in the job posting form with job title, requirements, and publisher information
2. Click the "Publish Job" button
3. A success message should appear
4. The job information will be saved to the data store

### Application Review
1. Access the application review page
2. The page will display a list of applications (if any)
3. If there are no applications, a message will indicate "No application records"

## Notes

- Data is stored in JSON files
- Ensure Tomcat has sufficient permissions to read and write these files
- In a production environment, a database should be used instead of file storage
- User authentication and permission control should be implemented for real-world applications