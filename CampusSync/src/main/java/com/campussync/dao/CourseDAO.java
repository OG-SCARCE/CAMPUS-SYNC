package com.campussync.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.campussync.model.Course;
import com.campussync.util.DBConnection;

/**
 * CourseDAO (Data Access Object)
 * 
 * Handles all database operations for the 'course' table.
 * Refactored to return List objects instead of ResultSet where possible
 * for better MVC separation and JSP scriptlet elimination
 * 
 * Responsibilities:
 * - Create new course records in database
 * - Retrieve all courses with proper sorting
 * - Provide multiple return formats for backward compatibility
 * - Use prepared statements to prevent SQL injection
 */
public class CourseDAO {

    /**
     * Retrieves all courses from database as a ResultSet.
     * Kept for backward compatibility with existing code
     * 
     * @return ResultSet with course_id and course_name columns
     * @throws SQLException If database operation fails
     */
    public ResultSet getAllCoursesResultSet() throws SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT course_id, course_name FROM course ORDER BY course_name";
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();
    }

    /**
     * Adds a new course to the database.
     * 
     * @param name Name of the new course (e.g., "B.Tech CSE")
     * @return true if course was successfully added, false otherwise
     */
    public boolean addCourse(String name) {
        String sql = "INSERT INTO course(course_name) VALUES(?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all courses from database as a List of Course objects.
     * Preferred method for retrieving courses; provides automatic resource management
     * 
     * @return List<Course> containing all course records
     * @throws SQLException if database operation fails
     */
    public List<Course> getAllCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT course_id, course_name FROM course ORDER BY course_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCourseName(rs.getString("course_name"));
                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves all courses (alias for getAllCourses).
     * 
     * @return List<Course> containing all course records
     */
    public List<Course> getAll() throws SQLException {
        return getAllCourses();
    }
}
