package com.campussync.servlet;

import com.campussync.dao.AdminDAO;
import com.campussync.model.Assignment;
import com.campussync.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentServlet
 *
 * Handles all Student-related actions:
 *   - Dashboard
 *   - Attendance View
 *   - Marks View
 *   - Notices View
 *   - Assignment Submission
 *
 * MVC:
 *   Controller → StudentServlet
 *   Model      → Tables: attendance, marks, subject, notices, assignment
 *   View       → /student/*.jsp
 *
 * Session Security:
 *   Only authenticated students can access these pages.
 */
public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ------------------------------------------------------------------
        //  SESSION VALIDATION (Student Only)
        // ------------------------------------------------------------------
        HttpSession session = req.getSession(false);

        if (session == null || !"student".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int studentId = (int) session.getAttribute("userId");

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        try (Connection conn = DBConnection.getConnection()) {

            switch (action) {

                // ----------------------------------------------------------
                // SHOW ATTENDANCE RECORDS
                // ----------------------------------------------------------
                case "attendance": {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT s.subject_name, a.att_date, a.status " +
                                    "FROM attendance a " +
                                    "JOIN subject s ON a.subject_id = s.subject_id " +
                                    "WHERE a.student_id=? " +
                                    "ORDER BY a.att_date DESC"
                    );
                    ps.setInt(1, studentId);

                    ResultSet rs = ps.executeQuery();
                    req.setAttribute("attendanceList", rs);

                    req.getRequestDispatcher("student/attendance.jsp").forward(req, resp);
                    return;
                }

                // ----------------------------------------------------------
                // SHOW MARKS
                // ----------------------------------------------------------
                case "marks": {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT s.subject_name, m.marks " +
                                    "FROM marks m " +
                                    "JOIN subject s ON m.subject_id = s.subject_id " +
                                    "WHERE m.student_id=?"
                    );
                    ps.setInt(1, studentId);

                    ResultSet rs = ps.executeQuery();
                    req.setAttribute("marksList", rs);

                    req.getRequestDispatcher("student/marks.jsp").forward(req, resp);
                    return;
                }

                // ----------------------------------------------------------
                // SHOW NOTICES (from admin)
                // ----------------------------------------------------------
                case "notices": {
                    AdminDAO ndao = new AdminDAO();
                    req.setAttribute("noticeList", ndao.getAllNotices());
                    req.getRequestDispatcher("student/notices.jsp").forward(req, resp);
                    return;
                }

                // ----------------------------------------------------------
                // SHOW ASSIGNMENT SUBMISSION PAGE
                // ----------------------------------------------------------
                case "submitAssignment": {
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT a.assignment_id, a.title, a.description, a.due_date, s.subject_name " +
                                    "FROM assignment a " +
                                    "JOIN subject s ON a.subject_id = s.subject_id " +
                                    "WHERE a.due_date >= CURDATE() " +
                                    "ORDER BY a.due_date ASC"
                    );

                    ResultSet rs = ps.executeQuery();
                    List<Assignment> assignmentList = new ArrayList<>();
                    while (rs.next()) {
                        Assignment assignment = new Assignment();
                        assignment.setAssignmentId(rs.getInt("assignment_id"));
                        assignment.setTitle(rs.getString("title"));
                        assignment.setDescription(rs.getString("description"));
                        assignment.setDueDate(rs.getDate("due_date").toLocalDate());
                        assignment.setSubjectName(rs.getString("subject_name"));
                        assignmentList.add(assignment);
                    }

                    req.setAttribute("assignmentList", assignmentList);

                    req.getRequestDispatcher("student/submit_assignment.jsp").forward(req, resp);
                    return;
                }

                // ----------------------------------------------------------
                // DEFAULT → STUDENT DASHBOARD
                // ----------------------------------------------------------
                default:
                    req.getRequestDispatcher("student/dashboard.jsp").forward(req, resp);
                    return;
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ------------------------------------------------------------------
        //  SESSION VALIDATION (Student Only)
        // ------------------------------------------------------------------
        HttpSession session = req.getSession(false);

        if (session == null || !"student".equals(session.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        int studentId = (int) session.getAttribute("userId");

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        try (Connection conn = DBConnection.getConnection()) {

            switch (action) {

                // ----------------------------------------------------------
                // HANDLE ASSIGNMENT FILE SUBMISSION
                // ----------------------------------------------------------
                case "submitAssignmentFile": {
                    int assignmentId = Integer.parseInt(req.getParameter("assignment_id"));
                    
                    // Get file from multipart form data
                    Part filePart = req.getPart("file");
                    if (filePart == null || filePart.getSize() == 0) {
                        req.setAttribute("error", "Please select a file to submit");
                        req.getRequestDispatcher("student/submit_assignment.jsp").forward(req, resp);
                        return;
                    }

                    // Generate unique file name with timestamp
                    String fileName = System.currentTimeMillis() + "_" + 
                                    filePart.getSubmittedFileName().replaceAll("[^a-zA-Z0-9._-]", "_");
                    String uploadDir = getServletContext().getRealPath("/") + "uploads/assignments/";
                    
                    // Create upload directory if it doesn't exist
                    java.io.File uploadDirFile = new java.io.File(uploadDir);
                    if (!uploadDirFile.exists()) {
                        uploadDirFile.mkdirs();
                    }

                    // Save file to disk
                    String filePath = uploadDir + fileName;
                    filePart.write(filePath);

                    PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO assignment_submission (student_id, assignment_id, submission_date, file_path) " +
                            "VALUES (?, ?, CURDATE(), ?)"
                    );
                    ps.setInt(1, studentId);
                    ps.setInt(2, assignmentId);
                    ps.setString(3, "uploads/assignments/" + fileName);

                    int rowsInserted = ps.executeUpdate();
                    
                    if (rowsInserted > 0) {
                        req.setAttribute("success", "Assignment submitted successfully!");
                    } else {
                        req.setAttribute("error", "Failed to submit assignment. Please try again.");
                    }

                    // Redirect back to assignment list using PRG pattern
                    resp.sendRedirect(req.getContextPath() + "/student?action=submitAssignment");
                    return;
                }

                // ----------------------------------------------------------
                // DEFAULT → REDIRECT TO DASHBOARD
                // ----------------------------------------------------------
                default:
                    resp.sendRedirect(req.getContextPath() + "/student");
                    return;
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (Exception e) {
            throw new ServletException("Error processing file upload: " + e.getMessage(), e);
        }
    }
}
