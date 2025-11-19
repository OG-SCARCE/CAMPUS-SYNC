package com.campussync.model;

/**
 * Faculty Model (POJO - Plain Old Java Object)
 * 
 * This class represents the Faculty entity in the CampusSync system.
 * It stores faculty member information retrieved from the 'faculty' table.
 * 
 * Purpose:
 * - Encapsulates faculty data in an object-oriented manner
 * - Used for data transfer between DAO layer and Servlet/JSP layers
 * - Provides getters and setters for controlled access to fields
 * 
 * Database Mapping:
 * - facultyId  → faculty_id (Primary Key)
 * - name       → name (Faculty member's full name)
 */
public class Faculty {
    private int facultyId;      // Unique identifier for faculty (Primary Key)
    private String name;         // Faculty member's full name

    /**
     * Getter for Faculty ID
     * @return The unique faculty identifier
     */
    public int getFacultyId() { 
        return facultyId; 
    }

    /**
     * Setter for Faculty ID
     * @param id The faculty identifier to set
     */
    public void setFacultyId(int id) { 
        this.facultyId = id; 
    }

    /**
     * Getter for Faculty Name
     * @return The name of the faculty member
     */
    public String getName() { 
        return name; 
    }

    /**
     * Setter for Faculty Name
     * @param n The faculty member's name to set
     */
    public void setName(String n) { 
        this.name = n; 
    }
}
