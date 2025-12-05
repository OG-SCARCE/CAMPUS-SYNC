package com.campussync.dao;

import com.campussync.model.Student;
import com.campussync.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AdminDAO handles all database operations required by the Admin module.
 * This includes login validation, inserting new records (Student/Faculty/Notice),
 * and retrieving lists of existing data.
 *
 * Refactored to return List objects instead of ResultSet for better MVC separation
 * and automatic resource management in JSP views.
 *
 * DAO Layer Purpose:
 * - To separate database logic from servlets (clean MVC structure)
 * - To ensure modular, reusable, and maintainable data access code
 * - To use PreparedStatements for security (SQL injection prevention)
 */
public class AdminDAO {

    /**
     * Validates admin credentials by checking username and password in the database.
     * @param username Admin's username
     * @param password Admin's password
     * @return ResultSet containing admin_id and username if credentials match
     *
     * Working:
     * - A DB connection is created
     * - Query checks if username & password exist
     * - Returns a ResultSet so servlet can read login details
     */
    public ResultSet login(String username, String password) throws SQLException {
        Connection conn = DBConnection.getConnection();

        // PreparedStatement prevents SQL injection
        PreparedStatement ps = conn.prepareStatement(
                "SELECT admin_id, username FROM admin WHERE username=? AND password=?"
        );

        ps.setString(1, username);
        ps.setString(2, password);

        // Servlet will read the result and close resources afterward
        return ps.executeQuery();
    }

    /**
     * Inserts a new Student record into the student table.
     * @return true if insertion is successful, else false
     *
     * Working:
     * - Prepared insert query
     * - Binds all student details
     * - executeUpdate() returns number of rows affected (1 if success)
     */
    public boolean addStudent(String name, String email, String password, String course, int semester) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO student(name, email, password, course, semester) VALUES(?,?,?,?,?)"
        );

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, course);
        ps.setInt(5, semester);

        return ps.executeUpdate() > 0; // true if row inserted
    }

    /**
     * Inserts a new Faculty member into the faculty table.
     * @return true if insertion succeeds
     *
     * Working:
     * - Similar to addStudent
     * - Stores faculty name, email, password & department
     */
    public boolean addFaculty(String name, String email, String password, String dept) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO faculty(name, email, password, department) VALUES(?,?,?,?)"
        );

        ps.setString(1, name);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, dept);

        return ps.executeUpdate() > 0;
    }

    /**
     * Adds a new notice to the notice table.
     * @return true if insertion succeeds
     *
     * Working:
     * - Stores notice title and message
     * - Used in admin dashboard to broadcast announcements
     */
    public boolean addNotice(String title, String message) throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO notice(title, message) VALUES(?,?)"
        );

        ps.setString(1, title);
        ps.setString(2, message);

        return ps.executeUpdate() > 0;
    }

    /**
     * Retrieves list of all students as a List<Student> object.
     * Replaced ResultSet with List<Student> for automatic resource management
     * and elimination of scriptlets in JSP
     *
     * @return List<Student> containing all student records
     * @throws SQLException if database operation fails
     */
    public List<Student> listStudentsAsList() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT student_id, name, email, course, semester FROM student";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setCourse(rs.getString("course"));
                s.setSemester(rs.getInt("semester"));
                students.add(s);
            }
        }
        return students;
    }

    /**
     * Retrieves list of all students as ResultSet (backward compatibility).
     * Deprecated in favor of listStudentsAsList(); kept for compatibility
     *
     * @return ResultSet with student details
     * @throws SQLException if database operation fails
     */
    public ResultSet listStudents() throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT student_id, name, email, course, semester FROM student"
        );

        return ps.executeQuery();
    }

    /**
     * Retrieves list of all faculty members.
     * @return ResultSet with faculty details
     *
     * Working:
     * - Same pattern as listStudents()
     * - Used to display faculty table in admin panel
     */
    public ResultSet listFaculty() throws SQLException {
        Connection conn = DBConnection.getConnection();

        PreparedStatement ps = conn.prepareStatement(
                "SELECT faculty_id, name, email, department FROM faculty"
        );

        return ps.executeQuery();
    }

    /**
     * Retrieves all notices ordered by most recent first.
     * @return ResultSet with notice details
     * @throws SQLException if database operation fails
     */
    public ResultSet getAllNotices() throws SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT notice_id, title, message, posted_at FROM notice ORDER BY posted_at DESC";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }
}
