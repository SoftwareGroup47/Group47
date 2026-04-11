# TA Module - Frontend Pages

This module contains the frontend pages for TA (Teaching Assistant) users in the BUPT TA Recruitment System.

## Files Structure

```
webapp/ta/
├── ta_profile.jsp    # TA Profile Creation Page
├── job_hall.jsp      # Job Browsing Page
└── apply_job.jsp     # Job Application Confirmation Page
```

## Page Descriptions

### 1. ta_profile.jsp
**Purpose**: Allows TA users to create and manage their personal profiles.

**Features**:
- Skills input field (textarea)
- Grades input field
- CV file path input
- Data persistence with pre-filled values on revisit
- Navigation links to Job Hall and Logout

**Form Action**: `/TaProfileServlet` (POST)

### 2. job_hall.jsp
**Purpose**: Displays available TA positions for browsing and application.

**Features**:
- Responsive grid layout for job cards
- Job filtering toolbar (search and status filter)
- Dynamic job list loading from FileDBHelper
- Direct application button for each job
- Empty state handling when no jobs available

**Data Source**: `FileDBHelper.getAllJobs()`

**Form Action**: `/ApplyJobServlet` (POST)

### 3. apply_job.jsp
**Purpose**: Confirmation page before submitting a job application.

**Features**:
- Job information confirmation display
- Confirm/Cancel action buttons
- Hidden fields for job ID and job name
- Navigation links back to Job Hall

**Form Action**: `/ApplyJobServlet` (POST)

## Technical Specifications

### Technology Stack
- JSP (JavaServer Pages)
- HTML5
- CSS3 (Flexbox/Grid layouts)
- Java Expression Language (EL)

### Styling Standards
- Color scheme: #1c6ff1 (primary), #f4f6f8 (background)
- Font: Arial, sans-serif
- Responsive design with `min()` and `auto-fit`
- Card-based UI with shadows and rounded corners

### Dependencies
- Requires `FileDBHelper` class from the util module
- Requires `Job` model class
- Servlet mappings in `web.xml`:
  - `TaProfileServlet`
  - `ApplyJobServlet`

## Usage

1. Include common header/sidebar components using JSP include:
```jsp
<jsp:include page="/common/header.jsp" />
<jsp:include page="/common/sidebar.jsp" />
```

2. Ensure proper session management for role-based access control

3. Link navigation between pages:
- ta_profile.jsp ↔ job_hall.jsp
- job_hall.jsp → apply_job.jsp (via form submission)

## Notes

- All pages are UTF-8 encoded for Chinese character support
- Form validation should be handled by corresponding Servlets
- Session attributes required: `username`, `role`