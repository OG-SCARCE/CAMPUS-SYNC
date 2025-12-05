package com.campussync.dao;

import com.campussync.model.Subject;
import com.campussync.util.DBConnection;
import com.campussync.util.TransactionManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SubjectDAO (Data Access Object)
 * 
 * Handles all database operations related to the 'subject' table.
 * Supports both single-operation and transactional multi-table operations.
 * 
 * Responsibilities:
 * - Insert new subjects with proper transaction handling
 * - Retrieve subject data efficiently (List instead of ResultSet)
 * - Support external transaction management for complex operations
 */
public class SubjectDAO {

    /**
     * Adds a new subject using an external connection (for transaction support).
     * Used when adding a subject as part of a larger multi-table operation.
     * 
     * @param conn Connection object (typically from TransactionManager)
     * @param s Subject object containing: subjectName, courseId, facultyId
     * @return true if subject was successfully inserted, false otherwise
     * @throws SQLException if database operation fails
     */
    public boolean addSubject(Connection conn, Subject s) throws SQLException {
        String sql = "INSERT INTO subject(subject_name, course_id, faculty_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getSubjectName());
            ps.setInt(2, s.getCourseId());
            ps.setInt(3, s.getFacultyId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Adds a new subject with automatic transaction handling (single operation).
     * Uses autocommit, suitable for standalone subject creation.
     * 
     * @param s Subject object containing: subjectName, courseId, facultyId
     * @return true if subject was successfully inserted, false otherwise
     */
    public boolean addSubject(Subject s) {
        String sql = "INSERT INTO subject(subject_name, course_id, faculty_id) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getSubjectName());
            ps.setInt(2, s.getCourseId());
            ps.setInt(3, s.getFacultyId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all subjects as a List<Subject> with course and faculty information.
     * Replaced ResultSet return type with List for better MVC separation
     * and automatic resource management.
     * 
     * @return List<Subject> containing subject data with course and faculty details
     * @throws SQLException if database operation fails
     */
    public List<Subject> getAllSubjectsJoined() throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT s.subject_id, s.subject_name, c.course_name, f.name AS faculty_name " +
                "FROM subject s " +
                "LEFT JOIN course c ON s.course_id = c.course_id " +
                "LEFT JOIN faculty f ON s.faculty_id = f.faculty_id " +
                "ORDER BY s.subject_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Subject subject = new Subject();
                subject.setSubjectId(rs.getInt("subject_id"));
                subject.setSubjectName(rs.getString("subject_name"));
                subject.setCourseName(rs.getString("course_name"));
                subject.setFacultyName(rs.getString("faculty_name"));
                subjects.add(subject);
            }
        }
        return subjects;
    }

    /**
     * Retrieves all subjects as a ResultSet (for backward compatibility).
     * Kept for compatibility with existing JSPs; prefer getAllSubjectsJoined() for new code.
     * 
     * @return ResultSet containing joined subject data
     * @throws SQLException if database operation fails
     */
    public ResultSet getAllSubjectsJoinedResultSet() throws SQLException {
        Connection con = DBConnection.getConnection();
        
        String sql = "SELECT s.subject_id, s.subject_name, c.course_name, f.name AS faculty_name " +
                "FROM subject s " +
                "LEFT JOIN course c ON s.course_id = c.course_id " +
                "LEFT JOIN faculty f ON s.faculty_id = f.faculty_id " +
                "ORDER BY s.subject_id";

        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }
}
