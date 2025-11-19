package com.campussync.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import com.campussync.dao.SubjectDAO;
import com.campussync.model.Subject;

/**
 * SubjectServlet
 * 
 * Handles HTTP requests for subject creation and management.
 * 
 * URL Mapping: /SubjectServlet
 * Supported Methods: POST
 * 
 * Purpose:
 * - Process subject creation forms from admin dashboard
 * - Save new subjects to database with course and faculty associations
 * - Provide success/error feedback via redirects
 * 
 * Form Parameters Expected:
 * - subject_name: Name of the subject (e.g., "Data Structures")
 * - course_id: ID of the course this subject belongs to
 * - faculty_id: ID of the faculty member teaching this subject
 * 
 * Workflow:
 * 1. Admin submits subject creation form
 * 2. Servlet receives POST request with form data
 * 3. Extract and validate parameters
 * 4. Create Subject object and populate fields
 * 5. Call SubjectDAO.addSubject() to save to database
 * 6. Redirect to success or error page based on result
 */
public class SubjectServlet extends HttpServlet {

    /**
     * Handles POST request for subject creation.
     * 
     * @param req HttpServletRequest containing form data
     * @param resp HttpServletResponse to send redirect response
     * @throws IOException If I/O error occurs during redirect
     * 
     * Process Flow:
     * 1. Create new Subject object
     * 2. Extract "subject_name" from request and set on object
     * 3. Extract "course_id" as integer and set on object
     * 4. Extract "faculty_id" as integer and set on object
     * 5. Call SubjectDAO to insert Subject into database
     * 6. Check success/failure and redirect accordingly
     * 
     * Success Case:
     * - Redirects to /adminPanel?action=subjects to show updated list
     * 
     * Failure Case:
     * - Redirects to add_subject.jsp?error=1 to show error page
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Subject s = new Subject();
        
        s.setSubjectName(req.getParameter("subject_name"));                    // Subject name
        s.setCourseId(Integer.parseInt(req.getParameter("course_id")));       // Convert course_id to int
        s.setFacultyId(Integer.parseInt(req.getParameter("faculty_id")));     // Convert faculty_id to int

        boolean success = new SubjectDAO().addSubject(s);

        if (success)
            // Success: redirect to course list to see newly added subject
            resp.sendRedirect(req.getContextPath() + "/adminPanel?action=subjects");
        else
            // Failure: redirect back to form with error code
            resp.sendRedirect(req.getContextPath() + "/admin/add_subject.jsp?error=1");
    }
}
