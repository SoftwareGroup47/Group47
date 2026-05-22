# User Manual — BUPT TA Recruitment System

**Group 47 — EBU6304 Software Engineering**

---

## Table of Contents

1. [Getting Started](#getting-started)
2. [Home Page](#home-page)
3. [Registration](#registration)
4. [Login](#login)
5. [TA Workflow](#ta-workflow)
   - [My Profile](#my-profile)
   - [Job Hall](#job-hall)
   - [My Applications](#my-applications)
6. [MO Workflow](#mo-workflow)
   - [Post a Job](#post-a-job)
   - [Review Applications](#review-applications)
   - [Workload Dashboard](#workload-dashboard)
7. [AI Matching Explained](#ai-matching-explained)
8. [Troubleshooting](#troubleshooting)

---

## Getting Started

### Accessing the System

Open a web browser and go to:

```
http://localhost:8080/index.jsp
```

If your Tomcat is running on a different port, just replace `8080` with whatever port you're using.

### System Requirements

- A modern browser (Chrome, Firefox, Edge, or Safari — basically anything updated in the last few years)
- JavaScript enabled
- Screen at least 1280×720

---

## Home Page

**URL:** `/index.jsp`
**Who can access:** Anyone

The landing page has the BUPT logo, a title, and two buttons: **Login** and **Register**. Nothing fancy — just pick what you need.

![Home Page](images/index.png)

---

## Registration

**URL:** `/register.jsp`
**Who can access:** Anyone

![Register Page](images/register.png)

### How to register:

1. Click **Register** on the home page
2. Pick a username (pick something unique — if someone already took it, you'll get an error)
3. Set a password
4. Choose your role:
   - **Teaching Assistant (TA)** — you want to apply for TA jobs
   - **Module Organizer (MO)** — you want to post jobs and hire TAs
5. Click **Register**

A couple things to know:
- Username needs to be unique, obvious but worth mentioning
- Passwords are hashed with SHA-256, not stored as plain text

---

## Login

**URL:** `/login.jsp`
**Who can access:** Anyone

![Login Page](images/login.png)

### How to log in:

1. Type your username
2. Type your password
3. Click **Login**

### What happens next:
- **TA users** go straight to the **Job Hall**
- **MO users** go to the **Post Job** page

---

## TA Workflow

### My Profile

**URL:** `/ta_profile.jsp` or `/TaProfileServlet`
**Who can access:** TA only

![TA Profile Page](images/ta_profile.png)

This is the page where you fill in your skills, grades, and optionally upload a CV. The AI matching system uses what you put here to figure out how well you fit each job posting.

### Steps:

1. Go to **My Profile** (you'll find it in the Job Hall navigation bar)
2. List your **skills** — just separate them with commas, something like: `Java, Python, Machine Learning, SQL`
3. Add your **grades** — however you want to format it, e.g. `GPA 3.8` or `Top 10%`
4. Upload your **CV** if you want (PDF, DOC, or DOCX works)
5. Hit **Save Profile**

> The skills you type in here are what the AI reads when it calculates your match score for each job. So be honest and thorough — if you know it, list it.

### Workload Panel

On the profile page there's also a **Workload Analysis** section that shows:
- **Total Applied** — how many applications you've sent (not counting rejected ones)
- **Accepted** — positions you got
- **Pending** — ones still waiting for the MO to decide
- **AI advice** — a short tip based on how heavy your current workload looks

---

### Job Hall

**URL:** `/job_hall.jsp`
**Who can access:** TA only

![Job Hall Page 1](images/job_hall1.png)

![Job Hall Page 2](images/job_hall2.png)

This is where you browse open TA positions. Each job card shows how well you match based on your profile.

### What's on this page:

1. **Search bar + status filter** at the top — useful when there are lots of postings
2. **AI Match panel** (only shows up after you've filled in your profile):
   - A **percentage score** and star rating
   - **Breakdown** of exact, fuzzy, and partial matches
   - Number of skills you **matched** vs. **missed**
   - A list of **missing skills** so you know what to work on
   - A **recommendation** on whether to apply
   - Your **current workload** count
3. **Apply button** — submits your application (the system checks for duplicates)

### How to use it:

1. Fill in your profile first (see [My Profile](#my-profile) — without it the matching won't work)
2. Browse through the job listings and check your match scores
3. Click **Apply** on jobs that look like a good fit
4. If you already applied to one, you'll see a warning instead

---

### My Applications

**URL:** `/StatusServlet`
**Who can access:** TA only

![My Applications Page](images/TAApplication.png)

Check the status of all the jobs you've applied to.

### What you'll see:

- **Summary numbers** at the top — total, accepted, and pending
- **Color-coded badges:**
  - **Yellow** — Pending (MO hasn't decided yet)
  - **Green** — Accepted
  - **Red** — Rejected
- **Quick links** back to Job Hall and Profile

---

## MO Workflow

### Post a Job

**URL:** `/mo/post_job.jsp`
**Who can access:** MO only

![Post a Job Page](images/postjob.png)

Create new TA position listings.

### How to post:

1. Type the **Job Title** — e.g., `Algorithms Teaching Assistant`
2. List the **skills you're looking for** — separated by commas:  
   `Java, algorithms, data structures, communication`
3. Click **Publish Position**

### A few tips:
- Be specific about what technical skills matter (languages, frameworks, tools)
- Soft skills help too — things like communication or teamwork
- The AI uses these keywords to match candidates, so pick terms that make sense
- Check the [synonym table](#ai-matching-explained) below — the system is smart enough to handle related terms

---

### Review Applications

**URL:** `/MoCheckApplyServlet`
**Who can access:** MO only

![Review Applications Page 1](images/MoCheckApply1.png)

![Review Applications Page 2](images/MoCheckApply2.png)

Look through applications and decide who to accept or reject. The AI gives you extra info to help with the decision.

### What's on this page:

1. **Application table** — every application with its current status
2. **Accept / Reject buttons** — for pending applications
3. **AI Analysis panel** (click to expand, per applicant):
   - Overall **match score** for this candidate vs. the position
   - **Breakdown** of exact / fuzzy / partial matches
   - **Missing skills** — what the applicant doesn't have
   - **Workload summary** — how many things this applicant has going on
   - **Workload advice** — whether this person might be stretched too thin

### How to review:

1. Scan through the list of applicants
2. Click **AI Analysis** on anyone you want more detail on
3. Use the match score and workload info to guide your choice
4. Click **Accept** or **Reject** for pending ones

---

### Workload Dashboard

**URL:** `/AdminWorkloadServlet`
**Who can access:** MO only

![Admin Workload Dashboard](images/AdminWorkload.png)

A bird's-eye view of all TAs and how busy they are.

### What's on this page:

- Every registered TA is listed
- **Workload bars** with colors:
  - **Green** — light load
  - **Orange** — moderate
  - **Red** — heavy (5+ applications)
- Each TA's **skills** shown at a glance
- **AI-generated advice** per TA
- **Profile warnings** if a TA hasn't filled in their profile yet

---

## AI Matching Explained

The AI in this system isn't machine learning — it's a **rule-based engine** that compares keywords between your profile and the job requirements.

### How it works:

When you look at a job in the Job Hall, here's what happens behind the scenes:

1. The job's required skills get split into individual keywords
2. Your profile skills also get split into keywords
3. The system runs **three passes** comparing them:
   - **Exact match (40%):** same keyword, ignoring case. E.g. `Java` = `java`
   - **Fuzzy match (35%):** the system knows some terms are related. E.g. `ML` counts for `machine learning`
   - **Partial match (25%):** if one keyword is part of another. E.g. `Java` is inside `Java development`
4. The three scores get combined into one weighted number
5. Missing skills and a suggestion are generated

### Some examples of what the synonym system knows:

| Job Requires | Your Skill | Match Type |
|---|---|---|
| `machine learning` | `ML` | Fuzzy |
| `JavaScript` | `Node.js` | Fuzzy |
| `database` | `MySQL` | Fuzzy |
| `teaching` | `tutoring` | Fuzzy |
| `version control` | `Git` | Fuzzy |
| `Java development` | `Java` | Partial |

So you don't need to worry about getting the exact same wording — the system is reasonably flexible.

### Reading your score:

| Score | Stars | What it means |
|---|---|---|
| 80–100% | 5 | Great match — definitely apply |
| 60–79% | 4 | Good match — you cover most of the requirements |
| 40–59% | 3 | Decent, but you're missing some things |
| 20–39% | 2 | Low match — probably better options out there |
| 0–19% | 1 | Barely any overlap |

---

## Troubleshooting

### Common problems:

| Problem | Fix |
|---|---|
| **Page won't load** | Make sure Tomcat is running on the right port |
| **Can't log in** | Double-check your username and password |
| **"Please complete your profile first"** | Go to My Profile and save your skills |
| **AI match not showing up** | You probably haven't filled in your profile skills yet |
| **CV upload won't work** | Check file type (PDF, DOC, DOCX only) and that it's not too big |
| **Duplicate application warning** | You already applied to that one — one per position |
| **"Only TA/MO can access"** | Your role doesn't have permission for that page |
| **Data not saved after restart** | Check write permissions on `~/tarecruit_data/` |

### Error handling in general:

All the backend servlets handle things like:
- Bad input — shows an alert instead of crashing
- Wrong role — blocked before anything happens
- Expired session — kicks you back to login
- Missing data — doesn't break, just shows what's available

---

## Navigation Reference

```
Home Page (index.jsp)
├── Login (login.jsp)
│   ├── [TA] Job Hall (job_hall.jsp)
│   │   ├── Apply for Job
│   │   ├── My Profile (ta_profile.jsp)
│   │   │   └── Save Profile / Upload CV
│   │   └── My Applications (TAApplicationStatusServlet)
│   │       └── View Status: Pending / Accepted / Rejected
│   │
│   └── [MO] Post Job (mo/post_job.jsp)
│       ├── Review Applications (MoCheckApplyServlet)
│       │   ├── Accept Application
│       │   ├── Reject Application
│       │   └── AI Analysis (per applicant)
│       └── Workload Dashboard (AdminWorkloadServlet)
│           └── View All TA Workloads
│
└── Register (register.jsp)
    └── Create TA or MO Account
```

---

## Need Help?

For setup and config stuff, check the [README.md](README.md). If something's really broken, reach out through QMPlus.
