package com.campussync.servlet;

import com.campussync.dao.*;
import com.campussync.model.Subject;
import com.campussync.util.Logger;
import com.campussync.util.TransactionManager;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/**
 * AdminServlet (Controller)
 * 
 * Handles all admin panel requests following strict MVC pattern:
 * - Controller: This servlet (routes requests, calls DAOs, prepares data)
 * - Model: DAO objects + POJOs (Student, Course, Subject, etc.)
 * - View: JSP pages (no business logic, only data display)
 * 
 * Features:
 * - Transaction support for multi-table operations
 * - List-based DAO returns for clean JSP scriptlet elimination
 * - Comprehensive error logging for debugging
 * - Proper error handling and user feedback
 * - Session validation for security
 */
public class AdminServlet extends HttpServlet {

    AdminDAO dao = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession s = req.getSession(false);

        if (s == null || !"admin".equals(s.getAttribute("role"))) {
            Logger.warn("Unauthorized access attempt to admin panel");
            resp.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        Logger.info("Admin action: " + action);

        try {

            switch (action) {

                case "notices":
                    AdminDAO ndao = new AdminDAO();
                    req.setAttribute("noticeList", ndao.getAllNotices());
                    req.getRequestDispatcher("admin/notices.jsp").forward(req, resp);
                    return;

                case "students":
                    req.setAttribute("students", dao.listStudentsAsList());
                    req.getRequestDispatcher("admin/manage_students.jsp").forward(req, resp);
                    return;

                case "faculty":
                    req.setAttribute("facultyData", dao.listFaculty());
                    req.getRequestDispatcher("admin/manage_faculty.jsp").forward(req, resp);
                    return;

                case "courses":
                    CourseDAO cdao = new CourseDAO();
                    req.setAttribute("courseData", cdao.getAllCourses());
                    req.getRequestDispatcher("admin/manage_courses.jsp").forward(req, resp);
                    return;

                case "subjects":
                    SubjectDAO sdao = new SubjectDAO();
                    CourseDAO crDao = new CourseDAO();
                    FacultyDAO fDao = new FacultyDAO();
                    
                    req.setAttribute("subjectData", sdao.getAllSubjectsJoined());
                    req.setAttribute("courses", crDao.getAllCourses());     // Populated courses list for dropdown
                    req.setAttribute("faculty", fDao.getAllFaculty());       // Populated faculty list for dropdown
                    
                    req.getRequestDispatcher("admin/manage_subjects.jsp").forward(req, resp);
                    return;

                case "addSubject":
                    CourseDAO crDao2 = new CourseDAO();
                    FacultyDAO fDao2 = new FacultyDAO();

                    req.setAttribute("courses", crDao2.getAllCourses());
                    req.setAttribute("faculty", fDao2.getAllFaculty());

                    req.getRequestDispatcher("admin/add_subject.jsp").forward(req, resp);
                    return;

                case "addCourse":
                    req.getRequestDispatcher("admin/add_course.jsp").forward(req, resp);
                    return;

                case "addNotice":
                    req.getRequestDispatcher("admin/add_notice.jsp").forward(req, resp);
                    return;

                default:
                    req.getRequestDispatcher("admin/dashboard.jsp").forward(req, resp);
                    return;
            }

        } catch (SQLException e) {
            Logger.error("Database error in doGet: " + action, e);
            throw new ServletException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        Logger.info("Admin POST action: " + action);

        try {

            switch (action) {

                case "addStudent":
                    try {
                        dao.addStudent(
                                req.getParameter("name"),
                                req.getParameter("email"),
                                req.getParameter("password"),
                                req.getParameter("course"),
                                Integer.parseInt(req.getParameter("semester"))
                        );
                        Logger.info("Student added successfully");
                        resp.sendRedirect("adminPanel?action=students");
                    } catch (SQLException e) {
                        Logger.error("Failed to add student", e);
                        req.setAttribute("error", "Failed to add student: " + e.getMessage());
                        req.getRequestDispatcher("admin/manage_students.jsp").forward(req, resp);
                    }
                    return;

                case "addFaculty":
                    try {
                        dao.addFaculty(
                                req.getParameter("name"),
                                req.getParameter("email"),
                                req.getParameter("password"),
                                req.getParameter("department")
                        );
                        Logger.info("Faculty added successfully");
                        resp.sendRedirect("adminPanel?action=faculty");
                    } catch (SQLException e) {
                        Logger.error("Failed to add faculty", e);
                        req.setAttribute("error", "Failed to add faculty: " + e.getMessage());
                        req.getRequestDispatcher("admin/manage_faculty.jsp").forward(req, resp);
                    }
                    return;

                case "addCourse":
                    try {
                        CourseDAO cdao = new CourseDAO();
                        cdao.addCourse(req.getParameter("course_name"));
                        Logger.info("Course added successfully");
                        resp.sendRedirect("adminPanel?action=courses");
                    } catch (Exception e) {
                        Logger.error("Failed to add course", e);
                        req.setAttribute("error", "Failed to add course");
                        req.getRequestDispatcher("admin/manage_courses.jsp").forward(req, resp);
                    }
                    return;

                case "addSubject":

                    String subName = req.getParameter("subject_name");
                    int courseId = Integer.parseInt(req.getParameter("course_id"));
                    int facultyId = Integer.parseInt(req.getParameter("faculty_id"));

                    Subject s = new Subject();
                    s.setSubjectName(subName);
                    s.setCourseId(courseId);
                    s.setFacultyId(facultyId);

                    SubjectDAO sdao = new SubjectDAO();
                    TransactionManager txn = new TransactionManager();
                    
                    try {
                        Connection conn = txn.beginTransaction();
                        boolean success = sdao.addSubject(conn, s);
                        
                        if (success) {
                            txn.commit();
                            Logger.info("Subject '" + subName + "' added successfully with course_id=" + courseId + ", faculty_id=" + facultyId);
                            resp.sendRedirect("adminPanel?action=subjects&msg=SubjectAdded");
                        } else {
                            txn.rollback();
                            Logger.warn("Subject insert returned false for: " + subName);
                            resp.sendRedirect("adminPanel?action=subjects&error=SubjectAddFailed");
                        }
                    } catch (SQLException e) {
                        txn.rollback();
                        Logger.error("Transaction failed while adding subject: " + subName, e);
                        req.setAttribute("error", "Database error: " + e.getMessage());
                        req.getRequestDispatcher("admin/add_subject.jsp").forward(req, resp);
                    } finally {
                        txn.closeConnection();
                    }
                    return;

                case "saveNotice":
                    try {
                        dao.addNotice(
                                req.getParameter("title"),
                                req.getParameter("message")
                        );
                        Logger.info("Notice published successfully");
                        resp.sendRedirect("adminPanel?action=dashboard&msg=NoticeAdded");
                    } catch (SQLException e) {
                        Logger.error("Failed to add notice", e);
                        req.setAttribute("error", "Failed to add notice");
                        req.getRequestDispatcher("admin/add_notice.jsp").forward(req, resp);
                    }
                    return;
            }

        } catch (SQLException e) {
            Logger.error("SQL error in doPost", e);
            throw new ServletException(e);
        }
    }
}
