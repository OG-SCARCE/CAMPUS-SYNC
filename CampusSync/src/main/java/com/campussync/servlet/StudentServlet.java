package com.campussync.servlet;

import com.campussync.dao.AdminDAO;
import com.campussync.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/**
 * StudentServlet
 *
 * Handles all Student-related actions:
 *   - Dashboard
 *   - Attendance View
 *   - Marks View
 *   - Notices View
 *
 * MVC:
 *   Controller → StudentServlet
 *   Model      → Tables: attendance, marks, subject, notices
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
}
