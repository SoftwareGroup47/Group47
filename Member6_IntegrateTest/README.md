# BUPT TA Recruitment System — Group 47

A lightweight **Java Servlet/JSP web application** for BUPT International School's Teaching Assistant recruitment, built using **Agile methodologies** with **AI-powered skill matching**.

---

## Table of Contents

1. [Features](#features)
2. [Architecture](#architecture)
3. [Project Structure](#project-structure)
4. [Prerequisites](#prerequisites)
5. [Setup & Configuration](#setup--configuration)
6. [Running the Software](#running-the-software)
7. [User Roles & Workflows](#user-roles--workflows)
8. [AI Matching System](#ai-matching-system)
9. [Data Storage](#data-storage)
10. [Testing](#testing)
11. [Technologies Used](#technologies-used)

---

## Features

### Core Features
| Feature | Description |
|---|---|
| **User Registration / Login** | Role-based (TA / MO) with SHA-256 password hashing |
| **TA Profile Management** | Create and edit skill profiles, upload CVs |
| **Job Posting** | MO can post TA positions with skill requirements |
| **Job Hall** | TA can browse all open positions |
| **Job Application** | TA can apply for positions with duplicate-check |
| **Application Review** | MO can accept/reject applications |
| **TA Application Status** | TA can view all submitted applications and their statuses |
| **Workload Dashboard** | MO/Admin can view all TA workloads |

### AI-Powered Features
| Feature | Description |
|---|---|
| **Skill Matching** | Multi-dimensional matching (exact, fuzzy/synonym, partial) |
| **Missing Skills Detection** | Identifies which required skills the applicant lacks |
| **Workload Balancing** | Tracks each TA's total applications, accepted positions, and pending status |
| **Match Scoring** | Weighted composite score with star ratings and suggestions |
| **Best-Match Ranking** | Ranks jobs by suitability for each TA |

### Technical Features
- No database required — **text file storage** (pipe-delimited)
- **In-memory caching** for fast read operations
- **SHA-256 password hashing**
- **CV file upload** support
- **JavaDoc documentation** on all classes and methods
- **JUnit 5 test suite** with 30+ test cases

---

## Architecture

```
┌─────────────┐     ┌──────────────┐     ┌───────────────┐
│   Browser   │────▶│   Servlets   │────▶│  FileDBHelper │
│   (JSP)     │◀────│  (Java EE)   │◀────│  (Data Layer) │
└─────────────┘     └──────────────┘     └───────────────┘
       │                    │                      │
       │                    ▼                      ▼
       │           ┌──────────────┐     ┌──────────────────┐
       │           │ AIMatchService│    │  ~/tarecruit_data/│
       │           │  (AI engine)  │    │  (Text files)     │
       │           └──────────────┘     └──────────────────┘
       │
       ▼
┌──────────────────────────────────────────────────────────┐
│ Model Layer: User, Job, Application, TAProfile            │
└──────────────────────────────────────────────────────────┘
```

---

## Project Structure

```
SE_group47/
├── pom.xml                          # Maven build config
├── README.md                        # This file
├── USER_MANUAL.md                   # User guide with screenshots
│
├── src/main/java/org/example/
│   ├── model/
│   │   ├── User.java                # User entity (username, password, role)
│   │   ├── Job.java                 # Job posting entity
│   │   ├── Application.java         # Application entity
│   │   └── TAProfile.java           # TA skill profile entity
│   │
│   ├── servlet/
│   │   ├── RegisterServlet.java     # User registration
│   │   ├── LoginServlet.java        # User authentication
│   │   ├── TaProfileServlet.java    # TA profile CRUD + CV upload
│   │   ├── ApplyJobServlet.java     # Job application submission
│   │   ├── MoPostJobServlet.java    # MO job posting
│   │   ├── MoCheckApplyServlet.java # MO application review (accept/reject)
│   │   ├── TAApplicationStatusServlet.java # TA views own application status
│   │   ├── AIMatchServlet.java      # AI matching JSON API
│   │   └── AdminWorkloadServlet.java # Workload dashboard
│   │
│   └── util/
│       ├── FileDBHelper.java        # File-based data CRUD operations
│       ├── FileUploadHelper.java    # CV file upload handler
│       └── AIMatchService.java      # AI rule-based skill matching engine
│
├── src/main/webapp/
│   ├── index.jsp                    # Home page
│   ├── login.jsp                    # Login page
│   ├── register.jsp                 # Registration page
│   ├── job_hall.jsp                 # TA job browser with AI analysis
│   ├── ta_profile.jsp               # TA profile editor with workload stats
│   ├── my_applications.jsp          # TA application status tracker
│   ├── admin_workload.jsp           # Admin/MO workload dashboard
│   ├── mo/
│   │   ├── post_job.jsp             # MO job posting form
│   │   └── check_apply.jsp          # MO application review with AI analysis
│   ├── images/                      # Logo and background images
│   └── WEB-INF/
│       └── web.xml                  # Servlet mappings
│
└── src/test/java/org/example/
    ├── model/
    │   └── ModelTest.java           # Model class unit tests
    └── util/
        ├── AIMatchServiceTest.java  # AI matching algorithm tests (17 test cases)
        └── FileDBHelperTest.java    # Data persistence tests (17 test cases)
```

---

## Prerequisites

| Software | Minimum Version | Purpose |
|---|---|---|
| **Java JDK** | 17+ (recommended 21+) | Compilation and runtime |
| **Apache Maven** | 3.8+ | Build management |
| **Apache Tomcat** | 9.0+ or 10.1+ | Servlet container |
| **Git** | 2.0+ | Version control (optional) |

---

## Setup & Configuration

### 1. Clone the repository

```bash
git clone <repository-url>
cd Group47-main/SE_group47
```

### 2. Build the project

```bash
mvn clean package
```

This produces `target/SE_group47-1.0-SNAPSHOT.war`.

### 3. Deploy to Tomcat

**Option A: Copy WAR file**
```bash
copy target\SE_group47-1.0-SNAPSHOT.war %CATALINA_HOME%\webapps\ROOT.war
```

**Option B: Deploy expanded directory**
```bash
copy -r src\main\webapp\* %CATALINA_HOME%\webapps\ROOT\
copy -r target\classes\* %CATALINA_HOME%\webapps\ROOT\WEB-INF\classes\
```

**Option C: Run with Jetty (development only)**
```bash
mvn jetty:run
```
Then visit: `http://localhost:8888/SE_group47`

### 4. Data Directory

All data is automatically created in:
- **Windows:** `C:\Users\{YourUsername}\tarecruit_data\`
- **Unix/Mac:** `~/tarecruit_data/`
- **CV Uploads:** `~/tarecruit_uploads/`

No manual database setup is required.

---

## Running the Software

### Starting Tomcat

```bash
# Navigate to Tomcat bin directory
cd %CATALINA_HOME%\bin

# Start Tomcat
catalina.bat run      # Windows (with console output)
./catalina.sh run     # Unix/Mac (with console output)
```

### Accessing the Application

Open a web browser and navigate to:

```
http://localhost:8080/index.jsp
```

**Note:** If deployed as a subdirectory (e.g., `SE_group47`), the URL will be:
```
http://localhost:8080/SE_group47/index.jsp
```

### Default Pages

| Page | URL | Access |
|---|---|---|
| Home | `/index.jsp` | Public |
| Login | `/login.jsp` | Public |
| Register | `/register.jsp` | Public |
| Job Hall | `/job_hall.jsp` | TA only |
| My Profile | `/ta_profile.jsp` | TA only |
| My Applications | `/TAApplicationStatusServlet` | TA only |
| Post Job | `/mo/post_job.jsp` | MO only |
| Review Applications | `/MoCheckApplyServlet` | MO only |
| Workload Dashboard | `/AdminWorkloadServlet` | MO only |

---

## User Roles & Workflows

### Teaching Assistant (TA)
1. Register an account with role "TA"
2. Login and navigate to **My Profile**
3. Fill in skills (comma-separated), grades, and upload a CV
4. Browse the **Job Hall** to see AI match scores for each position
5. Apply for suitable positions
6. Check application status in **My Applications**

### Module Organizer (MO)
1. Register an account with role "MO"
2. Login and post TA positions via **Post Job**
3. View applications via **Review Applications**
4. Use **AI Analysis** to evaluate each applicant's skill match
5. Accept or reject applications
6. Monitor all TAs' workload via **Workload Dashboard**

---

## AI Matching System

The AI matching engine (`AIMatchService`) is a **rule-based system** that evaluates skill compatibility between a job posting and an applicant's profile using three scoring dimensions:

### Matching Dimensions

| Dimension | Weight | Description |
|---|---|---|
| **Exact Match** | 40% | Case-insensitive exact keyword comparison |
| **Fuzzy/Synonym Match** | 35% | Semantic matching via a predefined synonym map (50+ terms) |
| **Partial/Containment Match** | 25% | Substring-level matching for compound skills |

### Synonym Map Coverage

The system maps related terms including:
- Programming languages (Java ↔ Spring, JavaScript ↔ TypeScript/React, etc.)
- Web technologies (HTML ↔ CSS ↔ front-end)
- AI/Data Science (ML ↔ deep learning ↔ neural networks)
- Database systems (SQL ↔ MySQL ↔ PostgreSQL)
- Soft skills (teaching ↔ tutoring ↔ mentoring)
- Tools (Git ↔ GitHub ↔ version control)

### How the Score Is Calculated

```
final_score = exact_matches/total × 40 + fuzzy_matches/total × 35 + partial_matches/total × 25
```

The score is clamped to [0, 100] and rounded to one decimal place.

### Workload Balancing

The system tracks for each TA:
- **Total active applications** (excluding rejected)
- **Accepted positions**
- **Pending applications**

Workload advice is generated based on thresholds:
- ≥2 accepted positions → "Focus on quality"
- ≥5 total applications → "High volume warning"
- ≥3 total applications → "Moderate workload alert"

---

## Data Storage

All data is stored as **pipe-delimited text files** in `~/tarecruit_data/`:

| File | Format | Example |
|---|---|---|
| `users.txt` | `username\|hashedPassword\|role` | `alice\|8d969eef...\|TA` |
| `jobs.txt` | `jobId\|jobName\|requirements\|moName` | `abc123\|Java TA\|Java,Spring\|prof_smith` |
| `applications.txt` | `id\|jobName\|applicant\|applyTime\|status` | `xyz789\|Java TA\|alice\|Mon May...\|Pending` |
| `ta_profiles.txt` | `username\|skills\|grades\|cvPath` | `alice\|Java,Python,SQL\|GPA 3.8\|cv_uuid.pdf` |

CV files are stored in `~/tarecruit_uploads/` with UUID-based filenames.

**Key Design Decisions:**
- In-memory caching for performance (loads once at startup, writes back on mutation)
- Thread-safe writes using `synchronized` methods
- No database dependency (meets the project requirement for text-file-only storage)

---

## Testing

The project includes a comprehensive **JUnit 5** test suite with **30+ test cases** across three test files.

### Running Tests

```bash
mvn test
```

### Test Coverage

| Test File | Test Count | Coverage |
|---|---|---|
| `AIMatchServiceTest.java` | 17 tests | Exact, fuzzy, partial matching; edge cases; workload; scoring |
| `FileDBHelperTest.java` | 17 tests | CRUD: users, jobs, applications, profiles; login; duplicates |
| `ModelTest.java` | 9 tests | Constructors, getters, setters, business logic validation |

### Key Test Scenarios

- **Exact match** with 100% identical skills
- **Fuzzy match** via synonym groups (e.g., "ML" → "machine learning")
- **Partial match** via substring containment
- **Edge cases**: empty requirements, null skills, case insensitivity
- **Duplicate detection** for registrations and applications
- **Password security**: correct login, wrong password, non-existent user
- **Profile CRUD**: save, retrieve, update, delete

---

## Technologies Used

| Technology | Usage |
|---|---|
| **Java 17+** | Core programming language |
| **Jakarta Servlet 6.0** | Web layer (formerly Java EE) |
| **JSP** | Server-side view templates |
| **Gson 2.10** | JSON serialization |
| **JUnit 5 (Jupiter)** | Unit testing framework |
| **Apache Maven** | Build & dependency management |
| **Apache Tomcat** | Servlet container |
| **SHA-256** | Password hashing algorithm |

---

## Authors

Group 47 — BUPT International School — EBU6304 Software Engineering

---

## License

This project is academic coursework and is not intended for production use.
