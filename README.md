# 💼 Job Portal System – Backend

Job Portal System built using **Spring Boot**, **Spring Security (JWT)**, **JPA**, and **MySQL**.  
This project supports multiple user roles, job posting, application tracking, and role-based access.


🔧 The backend is fully implemented and functional.  
🧩 A `frontend/` folder is included as a placeholder for a future React-based frontend (currently empty).
## 🧰 Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA + Hibernate
- MySQL / PostgreSQL
- Maven

## 🎯 Features

### 🔐 Authentication (Common)
- User Registration & Login (with role: Admin, Employer, Job Seeker)
- JWT-based Authentication
- Role-based Authorization


### 👨‍💼 Employer Module
- Create & manage employer profile
- Post new jobs
- View applications for jobs
- Shortlist / Reject candidates

### 🙋‍♂️ Job Seeker Module
- Create & update job seeker profile (resume, skills, experience)
- View and search job listings
- Apply to jobs

### 🛠️ Admin Module
- View all users (employers & job seekers)


## 🗃️ Database Design (Core Tables)

- `users` – login info and roles
- `employer_profiles` – company-related info
- `job_seeker_profiles` – skills, resume URL, experience
- `jobs` – job listings posted by employers
- `applications` – job applications by job seekers

## 🔄 System Flow

The Job Portal System supports three roles: **Admin**, **Employer**, and **Job Seeker**.  
Here's how the system works for each:

### 🛡️ Admin Flow

- The **Admin account is not registered via API**
- It is manually inserted into the database using SQL (e.g., via MySQL Workbench)

- Admin logs in using:
- **Email**: `admin@example.com`
- **Password**: `admin123` (stored using BCrypt)


### 👤 Common Flow(Employer & Job Seeker)

Users register via the endpoint:
- Register via `/api/auth/register` with role: `EMPLOYER`, `JOB_SEEKER`
- Login via `/api/auth/login` to get JWT token
- Use the token in headers to access protected APIs



### 👨‍💼 Employer Flow
1. Registers as an `EMPLOYER`
2. Logs in and creates their employer profile
3. Posts job listings with title, description, salary, etc.
4. Views applications received for each job
5. Shortlists or rejects candidates

### 👨‍🔧 Job Seeker Flow
1. Registers as a `JOB_SEEKER`
2. Logs in and creates their job seeker profile (skills, resume link, experience)
3. Browses available jobs
4. Applies for jobs
5.  Views status of each application (e.g., `APPLIED`, `SHORTLISTED`, `REJECTED`)





📁 Project Structure

````
job-portal/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/jobportal/
│   │   │   │       ├── applications/       # Application module (apply to job)
│   │   │   │       ├── authentication/     # Auth controller, service, JWT utils
│   │   │   │       ├── config/             # Spring Security & JWT config
│   │   │   │       ├── employer/           # Employer profile and job management
│   │   │   │       ├── exception/          # Custom exceptions and handlers
│   │   │   │       ├── jobseeker/          # Job seeker profile and features
│   │   │   │       ├── jobs/               # Job CRUD module
│   │   │   │       ├── security/           # JWT filters, entry points
│   │   │   │       └── user/               # User entity, repository, service
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/                               # Unit and integration tests
├── target/                                 # Compiled classes (auto-generated)
├── pom.xml                                 # Maven build file
├── README.md
└── .gitignore

````

🚀 How to Run the Project (Backend Only)

🧰 Prerequisites
Make sure the following are installed:

Java 21 or higher

Maven (for building the project)

MySQL 

1. Clone the repo

git clone https://github.com/your-username/job-portal.git
cd job-portal/backend

2.Configure the database in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/jobportal
spring.datasource.username=root
spring.datasource.password=yourpassword

3. Insert Admin manually using MySQL Workbench or any other tools.

INSERT INTO users (email, password, role)
VALUES ('admin@example.com', '$2a$10$encodedpassword...', 'ADMIN');

4.Run the application
./mvnw spring-boot:run


📡 API Overview

The backend exposes secure REST APIs for user registration, login, job management, profile handling, and job applications. JWT tokens are required for most routes. Here's a quick summary:

🧩 Core APIs

POST /api/auth/register – Register new users (Employer/Job Seeker)

POST /api/auth/login – Login and receive a JWT token

👤 User-Specific

POST /api/employer/company-profiles – Create company profile

GET /api/job-seeker/jobseeker-profile/me – View job seeker profile

POST /api/job-seeker/jobseeker-profiles – Create job seeker profile with resume

GET /api/employer/jobs – Get all jobs posted by employer

GET /api/jobs – Get all public jobs


📄 Job Applications

POST /api/job-seeker/applications – Apply for a job

GET /api/job-seeker/applications – View applied jobs

GET /api/employer/jobs/{jobId}/applications – View applications for a job

PATCH /api/employer/jobs/{jobId}/applications/{applicationId} – Update application status

🛡️ Admin Only

GET /api/admin/users – List all users in the system

After running the project locally, Swagger UI is available at:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)








