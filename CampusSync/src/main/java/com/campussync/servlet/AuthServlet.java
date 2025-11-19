package com.campussync.servlet;

import com.campussync.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/**
 * AuthServlet
 *
 * Handles:
 *   1. LOGIN (POST)
 *   2. LOGOUT (GET?action=logout)
 *
 * Login Flow:
 * - User submits email/username, password, and role (student/faculty/admin)
 * - Servlet verifies credentials from DB
 * - If valid → session created with role, userId, and name
 * - User redirected to respective dashboard
 *
 * Logout Flow:
 * - Invalidates session and redirects to index.jsp
 *
 * SECURITY NOTE:
 * - Plain text passwords only for prototype.
 * - In production → ALWAYS use BCrypt hashing + HTTPS.
 */
public class AuthServlet extends HttpServlet {

    // =====================================================================
    //  LOGIN HANDLER (POST)
    // =====================================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Read form parameters from login.jsp
        String emailOrUsername = req.getParameter("email");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        // Validate missing fields
        if (emailOrUsername == null || password == null || role == null) {
            req.setAttribute("error", "Missing credentials.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        // =================================================================
        // DATABASE AUTHENTICATION LOGIC
        // =================================================================
        try (Connection conn = DBConnection.getConnection()) {

            PreparedStatement ps;
            ResultSet rs;

            switch (role) {

                // ----------------------------------------------------------
                // STUDENT LOGIN
                // ----------------------------------------------------------
                case "student":
                    ps = conn.prepareStatement(
                            "SELECT student_id, name FROM student WHERE email=? AND password=?"
                    );
                    ps.setString(1, emailOrUsername);
                    ps.setString(2, password);

                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int id = rs.getInt("student_id");
                        String name = rs.getString("name");

                        HttpSession session = req.getSession(true);
                        session.setAttribute("role", "student");
                        session.setAttribute("userId", id);
                        session.setAttribute("name", name);

                        resp.sendRedirect("student/dashboard.jsp");

                        rs.close();
                        ps.close();
                        return;
                    }
                    rs.close();
                    ps.close();
                    break;

                // ----------------------------------------------------------
                // FACULTY LOGIN
                // ----------------------------------------------------------
                case "faculty":
                    ps = conn.prepareStatement(
                            "SELECT faculty_id, name FROM faculty WHERE email=? AND password=?"
                    );
                    ps.setString(1, emailOrUsername);
                    ps.setString(2, password);

                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int id = rs.getInt("faculty_id");
                        String name = rs.getString("name");

                        HttpSession session = req.getSession(true);
                        session.setAttribute("role", "faculty");
                        session.setAttribute("userId", id);
                        session.setAttribute("name", name);

                        resp.sendRedirect("faculty/dashboard.jsp");

                        rs.close();
                        ps.close();
                        return;
                    }
                    rs.close();
                    ps.close();
                    break;

                // ----------------------------------------------------------
                // ADMIN LOGIN
                // ----------------------------------------------------------
                case "admin":
                    ps = conn.prepareStatement(
                            "SELECT admin_id, username FROM admin WHERE username=? AND password=?"
                    );
                    ps.setString(1, emailOrUsername);
                    ps.setString(2, password);

                    rs = ps.executeQuery();
                    if (rs.next()) {
                        int id = rs.getInt("admin_id");
                        String name = rs.getString("username");

                        HttpSession session = req.getSession(true);
                        session.setAttribute("role", "admin");
                        session.setAttribute("userId", id);
                        session.setAttribute("name", name);

                        resp.sendRedirect("admin/dashboard.jsp");

                        rs.close();
                        ps.close();
                        return;
                    }
                    rs.close();
                    ps.close();
                    break;

                // ----------------------------------------------------------
                // INVALID ROLE SELECTED
                // ----------------------------------------------------------
                default:
                    req.setAttribute("error", "Invalid role selected.");
                    req.getRequestDispatcher("login.jsp").forward(req, resp);
                    return;
            }

            // If control reaches here → invalid credentials
            req.setAttribute("error", "Invalid credentials. Please try again.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Database error during authentication", e);
        }
    }

    // =====================================================================
    //  LOGOUT HANDLER (GET?action=logout)
    // =====================================================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = req.getSession(false);

            if (session != null)
                session.invalidate();   // Destroy session

            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }

        // Default: redirect to login page
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
