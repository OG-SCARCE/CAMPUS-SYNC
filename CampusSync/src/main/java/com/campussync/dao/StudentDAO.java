package com.campussync.dao;

import com.campussync.model.Student;
import com.campussync.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO handles all CRUD (Create, Read, Update, Delete) operations for the
 * 'student' table in the CampusSync database.
 *
 * DAO Layer Responsibilities:
 * - Keep database logic separate from servlets (Clean MVC structure)
 * - Provide secure, reusable, and centralized data access functions
 * - Use PreparedStatements to avoid SQL injection
 * - Ensure proper resource management via try-with-resources
 *
 * Note:
 * Passwords are stored in plain text ONLY because this is a student prototype.
 * In real production, always hash passwords using BCrypt.
 */
public class StudentDAO {

    /**
     * Inserts a new student record into the database.
     *
     * @param student Student object containing all required fields
     * @return true if student was successfully inserted, otherwise false
     *
     * Working:
     * - Establishes DB connection
     * - Inserts student details using prepared INSERT statement
     * - Uses RETURN_GENERATED_KEYS to fetch new student_id
     * - Sets the generated ID back into the Student object
     */
    public boolean createStudent(Student student) throws SQLException {
        String sql = "INSERT INTO student(name, email, password, course, semester) VALUES(?,?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPassword());
            ps.setString(4, student.getCourse());
            ps.setInt(5, student.getSemester());

            int affected = ps.executeUpdate();
            if (affected == 0) return false; // No row inserted → failure

            // Retrieve auto-generated student_id
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    student.setStudentId(rs.getInt(1));
                }
            }

            return true;
        }
    }

    /**
     * Retrieves a student record for login validation.
     *
     * @param email Student email (unique)
     * @param password Student password
     * @return Student object if credentials match, otherwise null
     *
     * Working:
     * - DB lookup based on email + password
     * - If match found → fills Student object with essential fields
     * - Result is used for login session creation
     */
    public Student getByEmailAndPassword(String email, String password) throws SQLException {
        String sql = "SELECT student_id, name, email, course, semester, created_at FROM student WHERE email=? AND password=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setCourse(rs.getString("course"));
                    s.setSemester(rs.getInt("semester"));
                    return s;
                }
            }
        }
        return null; // No matching record
    }

    /**
     * Retrieves student details by ID.
     *
     * @param id student_id
     * @return Student object if record exists, otherwise null
     *
     * Usage:
     * - Student profile page
     * - Admin viewing/editing student details
     */
    public Student getById(int id) throws SQLException {
        String sql = "SELECT student_id, name, email, course, semester, created_at FROM student WHERE student_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setCourse(rs.getString("course"));
                    s.setSemester(rs.getInt("semester"));
                    return s;
                }
            }
        }
        return null;
    }

    /**
     * Updates an existing student's basic information.
     *
     * @param student Student object with updated data
     * @return true if update was successful
     *
     * Working:
     * - Runs UPDATE query using student_id as reference
     * - Typically used by Admin for editing student profile
     */
    public boolean updateStudent(Student student) throws SQLException {
        String sql = "UPDATE student SET name=?, email=?, course=?, semester=? WHERE student_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            ps.setInt(4, student.getSemester());
            ps.setInt(5, student.getStudentId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a student by ID.
     *
     * @param studentId ID of the student to delete
     * @return true if deletion is successful
     *
     * Working:
     * - Uses DELETE statement with student_id
     * - Commonly used from Admin dashboard
     */
    public boolean deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM student WHERE student_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Retrieves all students from the database.
     *
     * @return List<Student> containing all student records
     *
     * Working:
     * - SELECT query fetches all basic fields
     * - Loops through ResultSet and fills each Student object
     * - Adds each Student object to a List
     * - Used for Admin dashboard "View Students" page
     */
    public List<Student> listAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT student_id, name, email, course, semester FROM student ORDER BY student_id";

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
                list.add(s);
            }
        }
        return list;
    }
}
