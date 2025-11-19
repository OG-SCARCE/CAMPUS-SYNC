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
 * Provides methods to add courses and retrieve course listings.
 * 
 * Responsibilities:
 * - Create new course records in database
 * - Retrieve all courses with proper sorting
 * - Provide multiple return formats (ResultSet or List<Course>)
 * - Use prepared statements to prevent SQL injection
 */
public class CourseDAO {

    /**
     * Retrieves all courses from database as a ResultSet.
     * 
     * @return ResultSet with course_id and course_name columns
     * @throws SQLException If database operation fails
     * 
     * Use Cases:
     * - JSP pages that directly iterate ResultSet
     * - When minimal object overhead is needed
     * 
     * NOTE: Caller must manage Connection and ResultSet closure
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
     * 
     * SQL Operation:
     * - Inserts new row into 'course' table with provided name
     * - Uses PreparedStatement to prevent SQL injection attacks
     * - Auto-generates course_id (PRIMARY KEY)
     * 
     * Error Handling:
     * - Catches any SQL exceptions
     * - Returns false on failure (graceful degradation)
     * - Prints stack trace for debugging
     */
    public boolean addCourse(String name) {
        String sql = "INSERT INTO course(course_name) VALUES(?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);                 // Bind course name to parameter
            return ps.executeUpdate() > 0;         // Check if insert succeeded (rows affected > 0)

        } catch (Exception e) {
            e.printStackTrace();  // Log error to console
            return false;         // Return failure flag
        }
    }

    /**
     * Retrieves all courses from database as a List of Course objects.
     * 
     * @return List<Course> containing all course records
     * 
     * Advantages:
     * - Type-safe access via Course objects
     * - Automatic resource management
     * - Easy to pass to servlets/JSP
     * - Handles errors gracefully
     * 
     * Working:
     * 1. Initialize empty ArrayList
     * 2. Query database for all courses ordered by name
     * 3. Iterate through ResultSet rows
     * 4. Create Course object for each row
     * 5. Populate courseId and courseName
     * 6. Add to list
     * 7. Return list (safe even if errors occurred)
     */
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT course_id, course_name FROM course ORDER BY course_name";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {                    // Process each course row
                Course c = new Course();           // Create new Course object
                c.setCourseId(rs.getInt("course_id"));     // Set ID from database
                c.setCourseName(rs.getString("course_name")); // Set name from database
                list.add(c);                       // Add to result list
            }

        } catch (Exception e) {
            e.printStackTrace();  // Print error details for debugging
        }
        return list;
    }

    /**
     * Alternative method: Retrieves all courses (same as getAllCourses).
     * 
     * @return List<Course> containing all course records
     * 
     * NOTE: This method duplicates getAllCourses() logic.
     *       In production, one of these methods should be removed
     *       to avoid code duplication (DRY principle).
     * 
     * Current Usage: Both methods work identically
     */
    public List<Course> getAll() {
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
}
