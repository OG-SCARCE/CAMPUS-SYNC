package com.campussync.servlet;

import com.campussync.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/**
 * AttendanceServlet
 *
 * Handles attendance submission by faculty for a specific subject on a given date.
 * 
 * URL Mapping: /AttendanceServlet
 * Supported Methods: POST
 * 
 * Purpose:
 * - Faculty submits attendance records for their classes
 * - Marks selected students as "Present" on a specific date
 * - Stores attendance data in 'attendance' table with associated subject and date
 * 
 * Form Parameters Expected (from faculty/attendance.jsp):
 * - subject_id: ID of the subject for which attendance is marked
 * - date: Attendance date in yyyy-mm-dd format
 * - present[]: Array of student IDs marked as present (checkboxes)
 *
 * Database Model:
 * - Inserts only present students (status = "P")
 * - Absent students are NOT inserted (business logic assumes absence if not present)
 * 
 * MVC Architecture:
 * - Controller → This Servlet (handles request/response)
 * - Model → attendance table + DBConnection
 * - View → Faculty JSP pages (faculty/attendance.jsp)
 */
public class AttendanceServlet extends HttpServlet {

    /**
     * Handles POST request for attendance submission.
     * 
     * @param req HttpServletRequest containing attendance data
     * @param resp HttpServletResponse to send redirect response
     * @throws ServletException If servlet processing fails
     * @throws IOException If I/O error occurs
     * 
     * Workflow:
     * 1. Extract subject_id, date, and present student list from request
     * 2. Open database connection
     * 3. Prepare batch INSERT statement
     * 4. Loop through each present student and add to batch
     * 5. Execute batch insert (all at once for efficiency)
     * 6. Redirect back to faculty dashboard with success message
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // =====================================================================
        // STEP 1: EXTRACT FORM PARAMETERS
        // =====================================================================
        
        int subjectId = Integer.parseInt(req.getParameter("subject_id"));
        
        String date = req.getParameter("date");
        
        String[] presentStudentIds = req.getParameterValues("present");

        // =====================================================================
        // STEP 2: DATABASE INSERTION
        // =====================================================================
        try (Connection conn = DBConnection.getConnection()) {

            // Prepared statement for inserting attendance records
            // Using batch insert for better performance (multiple inserts in one operation)
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO attendance(student_id, subject_id, att_date, status) VALUES(?,?,?,?)"
            );

            // ---------------------------------------------------------------
            // STEP 3: LOOP THROUGH PRESENT STUDENTS AND INSERT
            // ---------------------------------------------------------------
            if (presentStudentIds != null) {
                for (String sid : presentStudentIds) {

                    ps.setInt(1, Integer.parseInt(sid));   // Parameter 1: student ID
                    ps.setInt(2, subjectId);               // Parameter 2: subject ID
                    ps.setDate(3, Date.valueOf(date));     // Parameter 3: attendance date (String → SQL Date)
                    ps.setString(4, "P");                  // Parameter 4: status = "P" for Present
                    ps.addBatch();                          // Add this row to batch queue
                }

                ps.executeBatch();   // Execute all batched inserts at once (more efficient)
            }

            // ---------------------------------------------------------------
            // STEP 4: REDIRECT TO SUCCESS PAGE
            // ---------------------------------------------------------------
            resp.sendRedirect("faculty/dashboard.jsp?msg=Attendance saved");

        } catch (SQLException e) {
            throw new ServletException(e);  // Convert SQL exception to servlet exception
        }
    }
}
