package com.campussync.model;

import java.time.LocalDate;

/**
 * Assignment Model (POJO)
 *
 * This class represents the Assignment entity in the CampusSync system.
 * It stores assignment details such as title, description, due date, and
 * associated subject information.
 *
 * Purpose of Model Layer:
 * - Holds data transferred between DAO, Servlets, and JSP pages
 * - Provides clean object-oriented structure for database rows
 * - Encapsulates fields using private access + getters & setters
 */
public class Assignment {

    // =====================================================================
    // Fields representing columns in the "assignment" table
    // =====================================================================

    private int assignmentId;          // Unique assignment identifier (PK)
    private int subjectId;             // Foreign key to subject table
    private String title;              // Assignment title
    private String description;        // Assignment description/details
    private LocalDate dueDate;         // Due date for submission
    private String subjectName;        // Display name of subject (from JOIN)

    // =====================================================================
    // Default Constructor
    // Required for DAO mapping and object creation
    // =====================================================================
    public Assignment() {}

    // =====================================================================
    // Parameterized Constructor
    // Allows creation of full Assignment object when data is known
    // =====================================================================
    public Assignment(int assignmentId, int subjectId, String title,
                      String description, LocalDate dueDate, String subjectName) {
        this.assignmentId = assignmentId;
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.subjectName = subjectName;
    }

    // =====================================================================
    // Getter & Setter Methods (Encapsulation)
    // =====================================================================

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    // =====================================================================
    // toString() Override
    // Useful for debugging + logging assignment objects
    // =====================================================================
    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId=" + assignmentId +
                ", subjectId=" + subjectId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
