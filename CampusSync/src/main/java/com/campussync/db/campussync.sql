-- -----------------------------------
-- DATABASE CREATION
-- -----------------------------------
CREATE DATABASE IF NOT EXISTS campussync;
USE campussync;

-- -----------------------------------
-- STUDENT TABLE
-- -----------------------------------
CREATE TABLE student (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    course VARCHAR(100),
    semester INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------
-- FACULTY TABLE
-- -----------------------------------
CREATE TABLE faculty (
    faculty_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------
-- ADMIN TABLE
-- -----------------------------------
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- -----------------------------------
-- COURSE TABLE
-- -----------------------------------
CREATE TABLE course (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL
);

-- -----------------------------------
-- SUBJECT TABLE
-- -----------------------------------
CREATE TABLE subject (
    subject_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100),
    course_id INT,
    faculty_id INT,
    FOREIGN KEY (course_id) REFERENCES course(course_id) ON DELETE SET NULL,
    FOREIGN KEY (faculty_id) REFERENCES faculty(faculty_id) ON DELETE SET NULL
);

-- -----------------------------------
-- ATTENDANCE TABLE
-- -----------------------------------
CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    subject_id INT,
    att_date DATE,
    status ENUM('P','A') DEFAULT 'A',
    FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE CASCADE
);

-- -----------------------------------
-- ASSIGNMENT TABLE
-- -----------------------------------
CREATE TABLE assignment (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT,
    title VARCHAR(255),
    description TEXT,
    due_date DATE,
    file_path VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE CASCADE
);

-- -----------------------------------
-- MARKS TABLE
-- -----------------------------------
CREATE TABLE marks (
    marks_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    subject_id INT,
    marks INT,
    FOREIGN KEY (student_id) REFERENCES student(student_id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id) ON DELETE CASCADE
);

-- -----------------------------------
-- NOTICE TABLE
-- -----------------------------------
CREATE TABLE notice (
    notice_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    message TEXT,
    posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- -----------------------------------
-- SAMPLE INSERTS
-- -----------------------------------
INSERT INTO admin(username, password) VALUES ('admin', 'admin123');

INSERT INTO course(course_name) VALUES ('B.Tech Computer Science');

INSERT INTO faculty(name, email, password, department)
VALUES ('Prof. Guvi', 'faculty', 'faculty123', 'CSE');

INSERT INTO subject(subject_name, course_id, faculty_id)
VALUES ('Data Structures', 1, 1);

INSERT INTO student(name, email, password, course, semester)
VALUES ('Scarce', 'student', 'student123', 'B.Tech Computer Science', 3);
