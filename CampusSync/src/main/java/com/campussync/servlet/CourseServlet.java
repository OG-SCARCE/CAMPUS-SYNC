package com.campussync.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import com.campussync.dao.CourseDAO;

/**
 * CourseServlet
 * 
 * Handles HTTP requests for course management operations.
 * 
 * URL Mapping: /CourseServlet
 * Supported Methods: POST
 * 
 * Purpose:
 * - Receive course form submissions from admin panel
 * - Validate and process course creation
 * - Redirect to course management page after success
 * 
 * Form Parameters Expected:
 * - course_name: Name of the new course (e.g., "B.Tech CSE")
 * 
 * Workflow:
 * 1. User submits course creation form
 * 2. Servlet receives POST request
 * 3. Extract course_name from request
 * 4. Call CourseDAO.addCourse() to save to database
 * 5. Redirect to /adminPanel?action=courses
 */
public class CourseServlet extends HttpServlet {

    /**
     * Handles POST request for course creation.
     * 
     * @param req HttpServletRequest containing form data
     * @param resp HttpServletResponse to send redirect response
     * @throws ServletException If servlet processing fails
     * @throws IOException If I/O error occurs
     * 
     * Process:
     * 1. Extract "course_name" parameter from request
     * 2. Create CourseDAO instance
     * 3. Call addCourse() method with course name
     * 4. Redirect to course management page (with context path)
     * 
     * NOTE: Context path is important for deployment in subdirectories
     * Example: if app is at /campussync, redirect will be /campussync/adminPanel
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("course_name");

        CourseDAO cdao = new CourseDAO();
        
        cdao.addCourse(name);

        // Using req.getContextPath() ensures correct URL even if app is deployed in a subdirectory
        resp.sendRedirect(req.getContextPath() + "/adminPanel?action=courses");
    }
}
