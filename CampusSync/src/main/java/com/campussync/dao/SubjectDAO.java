package com.campussync.dao;

import com.campussync.model.Subject;
import com.campussync.util.DBConnection;
import java.sql.*;

/**
 * SubjectDAO (Data Access Object)
 * 
 * Handles all database operations related to the 'subject' table.
 * This class encapsulates SQL queries and database logic for subjects.
 * 
 * Responsibilities:
 * - Insert new subjects into the database
 * - Retrieve subject data with joined information (course name, faculty name)
 * - Provide clean, reusable database operations
 * 
 * DAO Pattern Benefits:
 * - Separates database logic from business logic
 * - Makes queries testable and reusable
 * - Centralizes SQL code in one place (easy to maintain)
 */
public class SubjectDAO {

    /**
     * Adds a new subject to the database.
     * 
     * @param s Subject object containing: subjectName, courseId, facultyId
     * @return true if subject was successfully inserted, false if operation failed
     * 
     * SQL Operation:
     * - Inserts new record into 'subject' table
     * - Uses PreparedStatement to prevent SQL injection
     * - Commits changes immediately
     * 
     * Exception Handling:
     * - Catches and prints any SQL exceptions
     * - Returns false on failure (allows graceful error handling)
     */
    public boolean addSubject(Subject s) {
        String sql = "INSERT INTO subject(subject_name, course_id, faculty_id) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getSubjectName());  // Bind subject name to first ?
            ps.setInt(2, s.getCourseId());        // Bind course ID to second ?
            ps.setInt(3, s.getFacultyId());       // Bind faculty ID to third ?

            return ps.executeUpdate() > 0;  // Returns true if at least 1 row inserted

        } catch (Exception e) {
            // Log error and return false (graceful failure)
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all subjects with their associated course and faculty information.
     * 
     * @return ResultSet containing joined data from subject, course, and faculty tables
     * @throws SQLException If database operation fails
     * 
     * SQL Query Details:
     * - Joins 'subject' with 'course' table on course_id
     * - Joins 'subject' with 'faculty' table on faculty_id
     * - Uses LEFT JOIN to include subjects even if course/faculty is null
     * - Returns aliased columns: subject_id, subject_name, course_name, faculty_name
     * 
     * Usage:
     * - Called by AdminServlet to populate manage_subjects.jsp
     * - Displays complete subject information with related course and faculty names
     * 
     * NOTE: Returns ResultSet (not List) - caller must close resources after use
     */
    public ResultSet getAllSubjectsJoinedResultSet() throws SQLException {
        Connection con = DBConnection.getConnection();
        
        String sql = "SELECT s.subject_id, s.subject_name, c.course_name, f.name AS faculty_name " +
                "FROM subject s " +                          // Get data from subject table (aliased as 's')
                "LEFT JOIN course c ON s.course_id = c.course_id " +  // Join with course to get course names
                "LEFT JOIN faculty f ON s.faculty_id = f.faculty_id " + // Join with faculty to get faculty names
                "ORDER BY s.subject_id";                     // Sort results by subject ID

        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();  // Execute query and return ResultSet
    }
}
