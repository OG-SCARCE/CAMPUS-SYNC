package com.campussync.servlet;

import com.campussync.dao.AdminDAO;
import com.campussync.util.DBConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 10
)
public class FacultyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // --------------------------
        // FACULTY AUTH CHECK
        // --------------------------
        HttpSession s = req.getSession(false);
        if (s == null || !"faculty".equals(s.getAttribute("role"))) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        int facultyId = (int) s.getAttribute("userId");

        try (Connection conn = DBConnection.getConnection()) {

            switch (action) {

                // ===============================
                //  SHOW NOTICES
                // ===============================
                case "notices":
                    AdminDAO ndao = new AdminDAO();
                    req.setAttribute("noticeList", ndao.getAllNotices());
                    req.getRequestDispatcher("faculty/notices.jsp").forward(req, resp);
                    return;

                // ===============================
                //  ATTENDANCE PAGE
                // ===============================
                case "attendance":
                    PreparedStatement ps = conn.prepareStatement(
                            "SELECT student_id, name FROM student"
                    );

                    ResultSet rs = ps.executeQuery();
                    req.setAttribute("students", rs);

                    req.getRequestDispatcher("faculty/attendance.jsp").forward(req, resp);
                    return;

                // ===============================
                //  UPLOAD ASSIGNMENT
                // ===============================
                case "upload":
                    req.getRequestDispatcher("faculty/upload_assignment.jsp").forward(req, resp);
                    return;

                // ===============================
                //  ENTER MARKS
                // ===============================
                case "marks":
                    PreparedStatement ps2 = conn.prepareStatement(
                            "SELECT student_id, name FROM student"
                    );

                    ResultSet rs2 = ps2.executeQuery();
                    req.setAttribute("students", rs2);

                    req.getRequestDispatcher("faculty/marks.jsp").forward(req, resp);
                    return;

                // ===============================
                //  DEFAULT: DASHBOARD
                // ===============================
                default:
                    req.getRequestDispatcher("faculty/dashboard.jsp").forward(req, resp);
                    return;
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    // =======================================================================
    //  POST METHODS
    // =======================================================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try (Connection conn = DBConnection.getConnection()) {

            switch (action) {

                // ===============================
                // SAVE ATTENDANCE
                // ===============================
                case "saveAttendance":

                    int studentId = Integer.parseInt(req.getParameter("student"));
                    int subjectId = Integer.parseInt(req.getParameter("subject_id"));
                    String attendanceDate = req.getParameter("date");
                    String attendanceStatus = req.getParameter("status");  // Get status from form instead of hardcoding "P"

                    PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO attendance(student_id, subject_id, att_date, status) VALUES(?,?,?,?)"
                    );
                    ps.setInt(1, studentId);
                    ps.setInt(2, subjectId);
                    ps.setDate(3, Date.valueOf(attendanceDate));
                    ps.setString(4, attendanceStatus);  // Use the actual status value (P or A)
                    ps.executeUpdate();

                    resp.sendRedirect(req.getContextPath() + "/faculty?action=attendance&msg=Saved");
                    return;

                // ===============================
                // UPLOAD ASSIGNMENT
                // ===============================
                case "uploadAssignment":

                    Part filePart = req.getPart("file");
                    String title = req.getParameter("title");
                    String desc = req.getParameter("description");
                    int subId = Integer.parseInt(req.getParameter("subject_id"));
                    String dueDate = req.getParameter("due_date");

                    String fileName = filePart.getSubmittedFileName();
                    String uploadPath = getServletContext().getRealPath("/") + "uploads";

                    File folder = new File(uploadPath);
                    if (!folder.exists()) folder.mkdirs();

                    filePart.write(uploadPath + File.separator + fileName);

                    PreparedStatement ps2 = conn.prepareStatement(
                            "INSERT INTO assignment(subject_id, title, description, due_date, file_path) VALUES(?,?,?,?,?)"
                    );
                    ps2.setInt(1, subId);
                    ps2.setString(2, title);
                    ps2.setString(3, desc);
                    ps2.setDate(4, Date.valueOf(dueDate));
                    ps2.setString(5, "uploads/" + fileName);
                    ps2.executeUpdate();

                    resp.sendRedirect(req.getContextPath() + "/faculty?action=upload&msg=Uploaded");
                    return;

                // ===============================
                // ENTER MARKS
                // ===============================
                case "enterMarks":

                    int stId = Integer.parseInt(req.getParameter("student_id"));
                    int subjId = Integer.parseInt(req.getParameter("subject_id"));
                    int marks = Integer.parseInt(req.getParameter("marks"));

                    PreparedStatement ps3 = conn.prepareStatement(
                            "INSERT INTO marks(student_id, subject_id, marks) VALUES(?,?,?)"
                    );
                    ps3.setInt(1, stId);
                    ps3.setInt(2, subjId);
                    ps3.setInt(3, marks);
                    ps3.executeUpdate();

                    resp.sendRedirect(req.getContextPath() + "/faculty?action=marks&msg=Added");
                    return;
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
