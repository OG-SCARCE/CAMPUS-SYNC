package com.campussync.model;
import com.campussync.model.Subject;
import com.campussync.dao.SubjectDAO;

/**
 * Subject Model (POJO)
 * 
 * Represents a Subject entity from the 'subject' table.
 * A subject is a course unit taught by a faculty member to students.
 * 
 * Purpose:
 * - Store subject data with its relationships (course + faculty)
 * - Enable data transfer between DAO and Servlet layers
 * - Maintain type-safe subject information
 * 
 * Database Mapping:
 * - subjectId   → subject_id (Primary Key)
 * - subjectName → subject_name (Name of the subject)
 * - courseId    → course_id (Foreign Key to course table)
 * - facultyId   → faculty_id (Foreign Key to faculty table)
 * 
 * Relationships:
 * - Each subject belongs to ONE course
 * - Each subject is taught by ONE faculty member
 */
public class Subject {

    private int subjectId;          // Unique subject identifier (Primary Key)
    private String subjectName;     // Name of the subject (e.g., "Data Structures")
    private int courseId;           // Which course this subject belongs to (Foreign Key)
    private int facultyId;          // Which faculty teaches this subject (Foreign Key)
    
    private String courseName;      // Display: Course name for subject listing
    private String facultyName;     // Display: Faculty name for subject listing

    // Constructors
    public Subject() {}

    public Subject(int subjectId, String subjectName, int courseId, int facultyId) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.courseId = courseId;
        this.facultyId = facultyId;
    }

    // Getters & Setters
    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
