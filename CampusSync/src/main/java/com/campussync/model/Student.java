package com.campussync.model;

import java.time.LocalDateTime;

/**
 * Student Model (POJO)
 *
 * This class represents the Student entity in the CampusSync system.
 * It stores all student-related information such as personal details,
 * academic data, login credentials, and creation timestamp.
 *
 * Purpose of Model Layer:
 * - Holds data transferred between DAO, Servlets, and JSP pages
 * - Provides clean object-oriented structure for database rows
 * - Encapsulates fields using private access + getters & setters
 */
public class Student {

    // --------------------------------------------------------------------
    // Fields representing columns in the "student" table
    // --------------------------------------------------------------------

    private int studentId;                 // Unique student identifier (PK)
    private String name;                   // Full name
    private String email;                  // Unique email used for login
    private String password;               // Password (plain for prototype)
    private String course;                 // Course enrolled (e.g., B.Tech CSE)
    private int semester;                  // Current semester
    private LocalDateTime createdAt;       // Account creation timestamp

    // --------------------------------------------------------------------
    // Default Constructor
    // Required for frameworks, servlets and DAO mapping
    // --------------------------------------------------------------------
    public Student() {}

    // --------------------------------------------------------------------
    // Parameterized Constructor
    // Allows creation of a full Student object when data is known
    // --------------------------------------------------------------------
    public Student(int studentId, String name, String email,
                   String password, String course, int semester) {

        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.course = course;
        this.semester = semester;

        // Timestamp set at object creation
        this.createdAt = LocalDateTime.now();
    }

    // --------------------------------------------------------------------
    // Getter & Setter Methods (Encapsulation)
    // --------------------------------------------------------------------

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // --------------------------------------------------------------------
    // toString() Override
    // Useful for debugging + logging student objects
    // --------------------------------------------------------------------
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", course='" + course + '\'' +
                ", semester=" + semester +
                '}';
    }
}
