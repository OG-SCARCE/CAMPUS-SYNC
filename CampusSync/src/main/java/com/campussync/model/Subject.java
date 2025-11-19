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

    /**
     * Getter for Subject ID
     * @return The unique subject identifier
     */
    public int getSubjectId() {
        return subjectId;
    }

    /**
     * Setter for Subject ID
     * @param subjectId The subject identifier to set
     */
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    /**
     * Getter for Subject Name
     * @return The name of the subject
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Setter for Subject Name
     * @param subjectName The subject name to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * Getter for Course ID (Foreign Key)
     * @return The ID of the course this subject belongs to
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Setter for Course ID (Foreign Key)
     * @param courseId The course ID to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Getter for Faculty ID (Foreign Key)
     * @return The ID of the faculty member teaching this subject
     */
    public int getFacultyId() {
        return facultyId;
    }

    /**
     * Setter for Faculty ID (Foreign Key)
     * @param facultyId The faculty ID to set
     */
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }
}
