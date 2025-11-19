package com.campussync.dao;

import com.campussync.model.Faculty;
import com.campussync.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FacultyDAO (Data Access Object)
 * 
 * Manages all database operations related to the 'faculty' table.
 * Provides methods to retrieve faculty information in different formats.
 * 
 * Responsibilities:
 * - Fetch faculty records from database
 * - Return data as ResultSet (for JSP direct access) or List (for servlet processing)
 * - Keep database queries centralized and maintainable
 */
public class FacultyDAO {

    /**
     * Retrieves all faculty members as a ResultSet.
     * 
     * @return ResultSet containing faculty_id and name columns
     * @throws SQLException If database connection or query fails
     * 
     * Usage:
     * - Used when JSP needs to iterate directly over database results
     * - Less overhead than converting to List
     * 
     * NOTE: Caller must close ResultSet and Connection after use
     * CAUTION: Connection is NOT closed here - caller's responsibility
     */
    public ResultSet getAllFacultyResultSet() throws SQLException {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT faculty_id, name FROM faculty ORDER BY name";  // Sort alphabetically
        PreparedStatement ps = con.prepareStatement(sql);
        return ps.executeQuery();  // Return raw database results
    }

    /**
     * Retrieves all faculty members as a List<Faculty>.
     * 
     * @return List of Faculty objects containing all faculty records
     * 
     * Advantages over ResultSet:
     * - Automatic resource management (try-with-resources closes all connections)
     * - Type-safe access via Faculty objects
     * - Can be passed to JSP or processed programmatically
     * - Resources are properly closed even if exception occurs
     * 
     * Working:
     * 1. Create empty ArrayList to store Faculty objects
     * 2. Execute SELECT query on faculty table
     * 3. Loop through ResultSet rows
     * 4. For each row: create Faculty object, populate fields, add to list
     * 5. All resources (Connection, PreparedStatement, ResultSet) auto-closed
     * 6. Return populated list
     */
    public List<Faculty> getAllFaculty() {
        List<Faculty> list = new ArrayList<>();  // Initialize empty list to hold Faculty objects
        String sql = "SELECT faculty_id, name FROM faculty ORDER BY name";  // Query all faculty sorted by name

        try (Connection con = DBConnection.getConnection();                 // Get DB connection
             PreparedStatement ps = con.prepareStatement(sql);              // Prepare SQL statement
             ResultSet rs = ps.executeQuery()) {                            // Execute query and get results

            while (rs.next()) {                          // Iterate through each row in ResultSet
                Faculty f = new Faculty();               // Create new Faculty object
                f.setFacultyId(rs.getInt("faculty_id")); // Set ID from current row
                f.setName(rs.getString("name"));         // Set name from current row
                list.add(f);                             // Add completed object to list
            }

        } catch (Exception e) {
            e.printStackTrace();  // Print error for debugging (could use logging in production)
        }
        return list;  // Return list (empty if error occurred)
    }
}
