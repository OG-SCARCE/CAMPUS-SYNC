# <p align="center"><img src="https://readme-typing-svg.herokuapp.com?font=Black+Ops+One&size=32&color=9A00FF&center=true&vCenter=true&width=900&lines=CAMPUS+SYNC;Unified+Campus+Management+System;JSP+%7C+SERVLETS+%7C+MYSQL;Modern+%26+Structured" /></p>

<p align="center">
  <img src="https://lms.galgotiasuniversity.org/pluginfile.php/3/core_admin/logo/0x150/1763024608/galgotais-logo.png" width="220" alt="Galgotias University Logo">
</p>

<p align="center">
  <img src="https://img.shields.io/badge/PLATFORM-Campus%20Management-6f00ff?style=for-the-badge" />
  <img src="https://img.shields.io/badge/TECH-JSP%20%7C%20SERVLETS%20%7C%20MYSQL-3500ff?style=for-the-badge" />
  <img src="https://img.shields.io/badge/ARCHITECTURE-MVC-ff0095?style=for-the-badge" />
  <img src="https://img.shields.io/badge/STATUS-ACTIVE-00ffbb?style=for-the-badge" />
</p>

---

<!-- Fixed navigation section - corrected href links to match proper markdown anchor IDs and improved icon alignment to single row format -->
<div align="center" style="display:flex; flex-direction:row; justify-content:center; gap:10px; align-items:center; flex-wrap:wrap; padding:15px;">
  <a href="#-campus-sync--a-modern-campus-management-system" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Overview</a>
  <a href="#-key-highlights" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Highlights</a>
  <a href="#-core-modules" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Core Modules</a>
  <a href="#-administrator-portal" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Admin</a>
  <a href="#-faculty-portal" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Faculty</a>
  <a href="#-student-portal" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Students</a>
  <a href="#-system-architecture" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Architecture</a>
  <a href="#-project-structure" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Structure</a>
  <a href="#-database-schema-overview" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">DB Schema</a>
  <a href="#-deployment--setup" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Setup</a>
  <a href="#-contributors" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Contributors</a>
  <a href="#-course-criteria-breakdown" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Criteria</a>
  <a href="#-default-login-sample" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Login</a>
  <a href="#-contribute" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Contribute</a>
  <a href="#-final-note" style="padding:8px 16px; background-color:#9A00FF; color:white; text-decoration:none; border-radius:5px; font-weight:bold; font-size:14px; white-space:nowrap;">Final Note</a>
</div>

---

# ⚡ Campus Sync — A Modern Campus Management System

**Campus Sync** is a structured, multi-role campus management system designed to streamline core academic operations within an institution.
Built using **JSP, Servlets, JDBC, and MySQL**, the system follows a clear **MVC architecture**, ensuring clean separation of logic, scalability, and maintainability.

The platform provides dedicated portals for **Administrators**, **Faculty**, and **Students**, each tailored to their respective responsibilities within the academic environment.

---

# 📘 Key Highlights

* Multi-role authentication system (Admin, Faculty, Student)
* Unified academic management features
* Clean MVC design with modular separation
* DAO-driven database communication
* JSP-based frontend with dynamic server responses
* Secure session handling across all portals
* Organized and scalable project directory

---

# 🔥 Core Modules

## 🏛️ Administrator Portal

The Administrator holds full control over the system and can manage:

* Courses
* Subjects
* Faculty
* Students
* Notices
* Overall dashboard summary

**Relevant Files**

* `AdminServlet.java`
* DAO classes: `AdminDAO.java`, `CourseDAO.java`, `StudentDAO.java`, etc.
* JSP views inside `/webapp/admin/`

---

## 🎓 Faculty Portal

Faculty members can efficiently manage their academic duties:

* Upload assignments
* Mark or update attendance
* View notices
* Manage and record marks

**Relevant Files**

* `FacultyServlet.java`
* `AssignmentServlet.java`
* `AttendanceServlet.java`
* JSP views inside `/webapp/faculty/`

---

## 👨‍🎓 Student Portal

Students can access:

* Personal dashboard
* Attendance records
* Marks
* Notices from administration and faculty

**Relevant Files**

* `StudentServlet.java`
* JSP views inside `/webapp/student/`

---

# 🧩 System Architecture

Campus Sync follows an MVC-inspired layered structure:

### **1️⃣ Model Layer**

Object-oriented representations of system entities:

* `Student.java`
* `Faculty.java`
* `Course.java`
* `Subject.java`

### **2️⃣ DAO Layer**

Responsible for all database communication:

* CRUD operations
* Authentication queries
* Course/Subject/Attendance handlers

DAO Files include:

* `AdminDAO.java`
* `FacultyDAO.java`
* `StudentDAO.java`
* `CourseDAO.java`
* `SubjectDAO.java`

### **3️⃣ Servlet (Controller) Layer**

Handles routing, form submission, validation, and business logic:

* `AuthServlet.java`
* `AdminServlet.java`
* `FacultyServlet.java`
* `StudentServlet.java`
* `CourseServlet.java`
* `SubjectServlet.java`
* `AssignmentServlet.java`
* `AttendanceServlet.java`

### **4️⃣ View Layer (JSP)**

Role-based visual interfaces stored inside:

* `/admin/`
* `/faculty/`
* `/student/`

---

# 🗂 Project Structure

\`\`\`
CampusSync/
│
└── main
    ├── java
    │   └── com
    │       └── campussync
    │           ├── dao
    │           │   ├── AdminDAO.java
    │           │   ├── CourseDAO.java
    │           │   ├── FacultyDAO.java
    │           │   ├── StudentDAO.java
    │           │   └── SubjectDAO.java
    │           │
    │           ├── db
    │           │   └── campussync.sql
    │           │
    │           ├── model
    │           │   ├── Course.java
    │           │   ├── Faculty.java
    │           │   ├── Student.java
    │           │   └── Subject.java
    │           │
    │           ├── servlet
    │           │   ├── AdminServlet.java
    │           │   ├── AssignmentServlet.java
    │           │   ├── AttendanceServlet.java
    │           │   ├── AuthServlet.java
    │           │   ├── CourseServlet.java
    │           │   ├── FacultyServlet.java
    │           │   ├── StudentServlet.java
    │           │   └── SubjectServlet.java
    │           │
    │           └── util
    │               └── DBConnection.java
    │
    └── webapp
        ├── img.png
        ├── index.jsp
        ├── login.jsp
        ├── package.json
        │
        ├── admin
        │   ├── add_course.jsp
        │   ├── add_notice.jsp
        │   ├── add_subject.jsp
        │   ├── dashboard.jsp
        │   ├── manage_courses.jsp
        │   ├── manage_faculty.jsp
        │   ├── manage_students.jsp
        │   ├── manage_subjects.jsp
        │   └── notices.jsp
        │
        ├── faculty
        │   ├── attendance.jsp
        │   ├── dashboard.jsp
        │   ├── marks.jsp
        │   ├── notices.jsp
        │   └── upload_assignment.jsp
        │
        ├── student
        │   ├── attendance.jsp
        │   ├── dashboard.jsp
        │   ├── marks.jsp
        │   └── notices.jsp
        │
        └── WEB-INF
            └── web.xml
\`\`\`

---

# 🗄 Database Schema (Overview)

### **students**

`id, name, course, semester, contact, email, password`

### **faculty**

`id, name, department, email, password`

### **courses**

`course_id, course_name`

### **subjects**

`subject_id, subject_name, course_id`

### **attendance**

`student_id, subject_id, date, status`

### **notices**

`id, title, description, posted_by, date`

*(Schema may vary depending on your SQL file.)*

---

# 🚀 Deployment & Setup

### **1. Configure MySQL**

\`\`\`
CREATE DATABASE campus_sync;
\`\`\`

Import the `campussync.sql` file from the `/db` folder.

### **2. Update DB Credentials**

Modify:

\`\`\`
DBConnection.java
\`\`\`

with your MySQL username and password.

### **3. Add JDBC Connector**

Place the MySQL Connector JAR inside the project libraries.

### **4. Configure Apache Tomcat**

* Add new Tomcat configuration
* Set application root to:

\`\`\`
/CampusSync
\`\`\`

### **5. Run the Application**

Visit:

\`\`\`
http://localhost:8080/CampusSync/
\`\`\`

---

# 📄 Contributors

| Name           | Role                   | GitHub                                    |
| -------------- | ---------------------- | ----------------------------------------- |
| **Aman Patel** | Lead Developer         | [OG-SCARCE](https://github.com/OG-SCARCE) |
| Shobhit Tiwari | UI / Testing           | —                                         |
| Alok Shaw      | Database & Code Review | —                                         |

---

# 🧪 Course Criteria Breakdown

| Section              | Marks |
| -------------------- | ----- |
| Project Setup        | 2     |
| Folder Structure     | 1     |
| Authentication Logic | 2     |
| Database Integration | 3     |
| Notices Module       | 3     |
| Student Module       | 4     |
| UI / UX              | 2     |
| Session Security     | 2     |

---

# 🔐 Default Login (Sample)

| Role    | Username | Password |
| ------- | -------- | -------- |
| Admin   | admin    | admin123 |
| Faculty | faculty  | faculty123 |
| Student | student  | student123 |

(You can also Adjust credentials based on your updated SQL data.)

---

# 🌟 Conclusion

**Campus Sync** provides a modern, modular, and scalable approach to managing essential academic workflows.
Its layered design, clean architecture, and multi-role structure make it ideal for real-world deployment as well as academic project submission.

---

# 💡 Contribute

- Fork the repo
- Make your changes
- Submit a pull request

---

# 🏁 Final Note

<p align="center"><b><i>"Campus Sync — A structured solution for a structured campus."</i></b></p>

---

## 🌟 Developed with 💻 and ☕ by [OG-SCARCE](https://github.com/OG-SCARCE)

<p align="center">
  <img src="https://www.guvi.in/assets/ClRfE5Qq-guvi-logo.webp" width="280" alt="Guvi Logo"/>
</p>
