package com.campussync.model;

/**
 * Course Model (POJO)
 * 
 * Represents a Course entity from the 'course' table.
 * Contains basic course information: ID and name.
 * 
 * Purpose:
 * - Store course data retrieved from database
 * - Transfer course information between layers (DAO → Servlet → JSP)
 * - Provide type-safe access to course properties
 * 
 * Database Mapping:
 * - courseId    → course_id (Primary Key)
 * - courseName  → course_name (Name of the course, e.g., "B.Tech CSE")
 */
public class Course {

    private int courseId;           // Unique course identifier (Primary Key)
    private String courseName;      // Name/title of the course (e.g., "Bachelor of Technology - Computer Science")

    /**
     * Getter for Course ID
     * @return The unique course identifier
     */
    public int getCourseId() {
        return courseId;
    }

    /**
     * Setter for Course ID
     * @param courseId The course identifier to set
     */
    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    /**
     * Getter for Course Name
     * @return The name of the course
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Setter for Course Name
     * @param courseName The course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
