package com.campussync.servlet;

import com.campussync.util.DBConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.*;
import java.sql.*;

/**
 * AssignmentServlet
 *
 * Handles assignment uploads from faculty, including file storage and database records.
 * 
 * URL Mapping: /AssignmentServlet
 * Supported Methods: POST (multipart form data)
 * 
 * Purpose:
 * - Allow faculty to upload assignment documents (PDF, Word, Images, etc.)
 * - Save uploaded files to server's /uploads directory
 * - Store assignment metadata in 'assignment' table
 * - Manage file constraints (max 10MB, 1MB threshold for streaming)
 * 
 * Form Parameters Expected:
 * - file: The uploaded file (required)
 * - title: Assignment title (e.g., "Assignment 1 - Data Structures")
 * - description: Detailed assignment description
 * - subject_id: ID of the subject this assignment belongs to
 * - due_date: Due date in yyyy-mm-dd format
 *
 * MVC Architecture:
 * - Servlet → Controller (handles request/response)
 * - DBConnection + SQL → Model (data persistence)
 * - JSP Pages → View (displays forms/results)
 * 
 * @MultipartConfig Annotation:
 * - Enables handling of multipart form data (file uploads)
 * - fileSizeThreshold: Data larger than 1MB is written to disk
 * - maxFileSize: Maximum individual file size is 10MB
 */
@WebServlet("/AssignmentServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,       // 1 MB - threshold for disk writing
        maxFileSize = 1024 * 1024 * 10         // 10 MB - maximum upload file size
)
public class AssignmentServlet extends HttpServlet {

    /**
     * Handles POST request for assignment upload.
     * 
     * @param req HttpServletRequest containing multipart form data
     * @param resp HttpServletResponse to send redirect response
     * @throws ServletException If servlet processing fails
     * @throws IOException If I/O error occurs during file handling
     * 
     * Workflow:
     * 1. Extract form fields and file from request
     * 2. Determine server upload directory path
     * 3. Create /uploads directory if it doesn't exist
     * 4. Save uploaded file to server
     * 5. Insert assignment metadata into database
     * 6. Redirect to success page
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // =====================================================================
        // STEP 1: EXTRACT FORM DATA
        // =====================================================================

        Part filePart = req.getPart("file");
        
        String title = req.getParameter("title");
        
        String desc = req.getParameter("description");
        
        int subjectId = Integer.parseInt(req.getParameter("subject_id"));
        
        String dueDate = req.getParameter("due_date");

        // =====================================================================
        // STEP 2: FILE HANDLING - GET ORIGINAL FILENAME
        // =====================================================================
        
        String fileName = filePart.getSubmittedFileName();

        // =====================================================================
        // STEP 3: CREATE UPLOAD DIRECTORY PATH
        // =====================================================================
        
        // getRealPath("/") gets the web application root directory
        // Example: /var/www/tomcat/webapps/campussync/
        String uploadDir = getServletContext().getRealPath("/") + "uploads";

        // =====================================================================
        // STEP 4: CREATE /UPLOADS FOLDER IF NOT EXISTS
        // =====================================================================
        
        File uploadFolder = new File(uploadDir);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();  // Create directory hierarchy if needed
        }

        // =====================================================================
        // STEP 5: DEFINE FINAL FILE PATH AND SAVE FILE
        // =====================================================================
        
        String filePath = uploadDir + File.separator + fileName;

        filePart.write(filePath);

        // =====================================================================
        // STEP 6: INSERT ASSIGNMENT METADATA INTO DATABASE
        // =====================================================================
        
        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO assignment(subject_id, title, description, due_date, file_path) VALUES(?,?,?,?,?)"
            );

            ps.setInt(1, subjectId);                    // Parameter 1: subject ID
            ps.setString(2, title);                     // Parameter 2: assignment title
            ps.setString(3, desc);                      // Parameter 3: assignment description
            ps.setDate(4, Date.valueOf(dueDate));       // Parameter 4: due date (String → SQL Date)
            ps.setString(5, "uploads/" + fileName);     // Parameter 5: relative path (used for downloads)

            ps.executeUpdate();                         // Execute INSERT query

            resp.sendRedirect(req.getContextPath() + "/faculty?action=dashboard&msg=Assignment%20uploaded");

        } catch (SQLException e) {
            throw new ServletException(e);  // Convert to servlet exception
        }
    }
}
