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

<p align="center">
  <a href="#campus-sync--a-modern-campus-management-system"><img src="https://img.shields.io/badge/OVERVIEW-Campus%20Sync-9A00FF?style=for-the-badge" /></a>
  <a href="#key-highlights"><img src="https://img.shields.io/badge/HIGHLIGHTS-Key%20Features-9A00FF?style=for-the-badge" /></a>
  <a href="#core-modules"><img src="https://img.shields.io/badge/CORE%20MODULES-System-9A00FF?style=for-the-badge" /></a>
  <a href="#administrator-portal"><img src="https://img.shields.io/badge/ADMIN-Portal-9A00FF?style=for-the-badge" /></a>
  <a href="#faculty-portal"><img src="https://img.shields.io/badge/FACULTY-Portal-9A00FF?style=for-the-badge" /></a>
  <a href="#student-portal"><img src="https://img.shields.io/badge/STUDENTS-Portal-9A00FF?style=for-the-badge" /></a>
  <a href="#system-architecture"><img src="https://img.shields.io/badge/ARCHITECTURE-Design-9A00FF?style=for-the-badge" /></a>
  <a href="#project-structure"><img src="https://img.shields.io/badge/STRUCTURE-Project-9A00FF?style=for-the-badge" /></a>
  <a href="#database-schema-overview"><img src="https://img.shields.io/badge/DB%20SCHEMA-Database-9A00FF?style=for-the-badge" /></a>
  <a href="#deployment--setup"><img src="https://img.shields.io/badge/SETUP-Deployment-9A00FF?style=for-the-badge" /></a>
  <a href="#contributors"><img src="https://img.shields.io/badge/CONTRIBUTORS-Team-9A00FF?style=for-the-badge" /></a>
  <a href="#course-criteria-breakdown"><img src="https://img.shields.io/badge/CRITERIA-Breakdown-9A00FF?style=for-the-badge" /></a>
  <a href="#default-login-sample"><img src="https://img.shields.io/badge/LOGIN-Credentials-9A00FF?style=for-the-badge" /></a>
  <a href="#contribute"><img src="https://img.shields.io/badge/CONTRIBUTE-Join%20Us-9A00FF?style=for-the-badge" /></a>
  <a href="#final-note"><img src="https://img.shields.io/badge/FINAL%20NOTE-Conclusion-9A00FF?style=for-the-badge" /></a>
</p>

# ⚡ Campus Sync — A Modern Campus Management System

**Campus Sync** is a structured, multi-role campus management system designed to streamline core academic operations within an institution.
Built using **JSP, Servlets, JDBC, and MySQL**, the system follows a clear **MVC architecture**, ensuring clean separation of logic, scalability, and maintainability.

The platform provides dedicated portals for **Administrators**, **Faculty**, and **Students**, each tailored to their respective responsibilities within the academic environment.

---

# 📘 Key Highlights

* 🔐 Multi-role authentication system (Admin, Faculty, Student)
* 📚 Unified academic management features
* 🧱 Clean MVC design with modular separation
* 🔄 DAO-driven database communication
* 🖥️ JSP-based frontend with dynamic server responses
* 🛡️ Secure session handling across all portals
* 🗂️ Organized and scalable project directory
---

# 🔥 Core Modules

## 🏛️ Administrator Portal

The Administrator holds full control over the system and can manage:

* 📘 Courses
* 📖 Subjects
* 👨‍🏫 Faculty
* 🎓 Students
* 📢 Notices
* 📊 Dashboard summary

**Relevant Files**

* 🧩 ```AdminServlet.java```
* 🧰 DAO files: ```AdminDAO.java```, ```CourseDAO.java```, ```StudentDAO.java```, etc.
* 🖥️ JSP views inside ```/webapp/admin/```

---

## 🎓 Faculty Portal

Faculty members can efficiently manage their academic duties:

* 📤 Upload assignments
* 📝 Mark or update attendance
* 📃 View notices
* 🎯 Manage & record marks

**Relevant Files**

* 🧩 `FacultyServlet.java`
* 🧩 `AssignmentServlet.java`
* 🧩 `AttendanceServlet.java`
* 🖥️ JSP views inside `/webapp/faculty/`

---

## 👨‍🎓 Student Portal

Students can access:

* 📊 Personal dashboard
* 📝 Attendance records
* 🎯 Marks overview
* 📢 Notices

**Relevant Files**

* 🧩 StudentServlet.java
* 🖥️ JSP views inside /webapp/student/

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

```
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
```

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

# 🛠️ Pre-Requisites (Must-Have Before Running Campus Sync)

Before running **Campus Sync**, ensure that your system is properly configured with the following requirements. Missing any of these may cause server startup failures or database connectivity issues.

---

## **1️⃣ Java JDK 17 Installed**

Your system must have **JDK 17** installed and correctly added to **System Environment Variables**.

### **Add `JAVA_HOME` in System Variables**

Navigate to:

**System Properties → Advanced → Environment Variables → System Variables → New**

```
Variable Name: JAVA_HOME
Variable Value: C:\Program Files\Java\jdk-17
```

### **Add JDK bin path to System Variables → Path**

Edit the **Path** variable under **System Variables** and add:

```
C:\Program Files\Java\jdk-17\bin
```

> ⚠️ Note: Adding these values under *User Variables* may cause Tomcat to fail during startup.
> Ensure they are configured under **System Variables** only.

---

## **2️⃣ MySQL Installed & Running**

MySQL Server must be installed and active on your machine.

**Default database credentials used in the project:**

```
username = root
password = 1234
```

If your MySQL credentials differ, update them inside:

```
CampusSync/src/main/java/com/campussync/util/DBConnection.java
```

Modify the connection line:

```java
Connection con = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/campussync", "YOUR_USERNAME", "YOUR_PASSWORD"
);
```

---

## **3️⃣ Import the Database (Mandatory Before Starting Localhost)**

The database **must be imported before you start the server**, otherwise:

* Login will fail
* Dashboards will not load
* Tables will be empty
* JDBC queries will throw errors

Run the SQL file located at:

```
..\..\CAMPUS-SYNC\CampusSync\src\main\java\com\campussync\db\campussync.sql
```

Import it using:

* MySQL Workbench
* phpMyAdmin
* MySQL CLI

This script creates all required tables (students, faculty, courses, subjects, attendance, notices) along with default login credentials.

---

## **4️⃣ Apache Tomcat (For Manual Startup)**

If you choose to run Tomcat manually:

* Ensure Tomcat is configured with **JDK 17**
* Verify that port **8080** is available
* Confirm `JAVA_HOME` is correctly set under System Variables

If using the packaged Tomcat provided in the project, simply run:

```
StartServer.bat
```

---

## **5️⃣ Recommended System Setup**

For smooth execution:

* Keep the project folder in a simple directory (e.g., Desktop)
* Avoid long or deeply nested file paths
* Ensure the MySQL service remains running
* Avoid conflicting applications on port 8080

---

# 🚀 Deployment & Setup

Below are the **actual installation steps** required to run the *Campus Sync* system properly on your machine.


## **1️⃣ Download the Project ZIP**

Go to the **Green Code Button** on this repository and click:

```
Download ZIP
```

Save the ZIP file to your **Desktop** for easy access.

---

## **2️⃣ Extract the ZIP File**

Locate the downloaded file:

```
CampusSync-main.zip
```

Right-click → **Extract All**

This will create a folder:

```
CampusSync/
```

---

## **3️⃣ Run the Project (Easy Method)**

Inside the extracted folder, find:

```
StartServer.bat
```

Double-click it.

This script will:

* Initialize required paths
* Configure the environment
* Start the backend
* Launch the server automatically

---

## **4️⃣ Run the Project (Manual Path Access Method)**

If you want to run it manually:

Then navigate to the folder:

```cmd
...\...\CAMPUS-SYNC\CampusSync_Files\CampusSync_Tomcat\apache-tomcat-9.0.112\bin\startup.bat
```

Now run:

```cmd
startup.bat
```

Same execution will start.

---

## **5️⃣ Access the Application**

After the server starts successfully, open your browser:

```
http://localhost:8080/CampusSync/
```

Your Campus Sync system is now running.

---

## ✅ Optional but Recommended

* Ensure **Java JDK** is installed
* Ensure **Apache Tomcat** is running (if required by your setup)
* Keep the project inside a simple path like Desktop to avoid permission issues


---
# 📄 Contributors

| Name           | Role                   | GitHub                                    |
| -------------- | ---------------------- | ----------------------------------------- |
| **Aman Patel** | Lead Developer         | [OG-SCARCE](https://github.com/OG-SCARCE) |
| Shobhit Tiwari | UI / Testing           | —                                         |
| Alok Shaw      | Database & Code Review | —                                         |
---

# 🔐 Default Login (Sample)

| Role    | Username | Password |
| ------- | -------- | -------- |
| Admin   | admin    | admin123 |
| Faculty | faculty | faculty123  |
| Student | student | student123  |

(You can also Adjust credentials based on your updated SQL data.)

---

# 🌟 Conclusion

**Campus Sync** provides a modern, modular, and scalable approach to managing essential academic workflows.
Its layered design, clean architecture, and multi-role structure make it ideal for real-world deployment as well as academic project submission.

---
## 💡 Contribute

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
