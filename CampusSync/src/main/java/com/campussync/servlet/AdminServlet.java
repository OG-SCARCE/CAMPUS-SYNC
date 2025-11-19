package com.campussync.servlet;

import com.campussync.dao.*;
import com.campussync.model.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

public class AdminServlet extends HttpServlet {

    AdminDAO dao = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession s = req.getSession(false);

        if (s == null || !"admin".equals(s.getAttribute("role"))) {
            resp.sendRedirect("login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "dashboard";

        try {

            switch (action) {

                case "notices":
                    AdminDAO ndao = new AdminDAO();
                    req.setAttribute("noticeList", ndao.getAllNotices());
                    req.getRequestDispatcher("admin/notices.jsp").forward(req, resp);
                    return;


                case "students":
                    req.setAttribute("students", dao.listStudents());
                    req.getRequestDispatcher("admin/manage_students.jsp").forward(req, resp);
                    return;

                case "faculty":
                    req.setAttribute("facultyData", dao.listFaculty());
                    req.getRequestDispatcher("admin/manage_faculty.jsp").forward(req, resp);
                    return;

                case "courses":
                    CourseDAO cdao = new CourseDAO();
                    req.setAttribute("courseData", cdao.getAll());
                    req.getRequestDispatcher("admin/manage_courses.jsp").forward(req, resp);
                    return;

                case "subjects":
                    SubjectDAO sdao = new SubjectDAO();
                    CourseDAO crDao = new CourseDAO();
                    FacultyDAO fDao = new FacultyDAO();
                    
                    req.setAttribute("subjectData", sdao.getAllSubjectsJoinedResultSet());
                    req.setAttribute("courses", crDao.getAllCoursesResultSet());
                    req.setAttribute("faculty", fDao.getAllFacultyResultSet());
                    
                    req.getRequestDispatcher("admin/manage_subjects.jsp").forward(req, resp);
                    return;

                // 🔥 LOAD DROPDOWN DATA HERE
                case "addSubject":
                    CourseDAO crDao2 = new CourseDAO();
                    FacultyDAO fDao2 = new FacultyDAO();

                    req.setAttribute("courses", crDao2.getAllCoursesResultSet());
                    req.setAttribute("facultyList", fDao2.getAllFacultyResultSet());

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
            throw new ServletException(e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        try {

            switch (action) {

                case "addStudent":
                    dao.addStudent(
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            req.getParameter("course"),
                            Integer.parseInt(req.getParameter("semester"))
                    );
                    resp.sendRedirect("adminPanel?action=students");
                    return;

                case "addFaculty":
                    dao.addFaculty(
                            req.getParameter("name"),
                            req.getParameter("email"),
                            req.getParameter("password"),
                            req.getParameter("department")
                    );
                    resp.sendRedirect("adminPanel?action=faculty");
                    return;

                case "addCourse":
                    CourseDAO cdao = new CourseDAO();
                    cdao.addCourse(req.getParameter("course_name"));
                    resp.sendRedirect("adminPanel?action=courses");
                    return;

                // 🔥 SAVE SUBJECT HERE
                case "addSubject":

                    String subName = req.getParameter("subject_name");
                    int courseId = Integer.parseInt(req.getParameter("course_id"));
                    int facultyId = Integer.parseInt(req.getParameter("faculty_id"));

                    Subject s = new Subject();
                    s.setSubjectName(subName);
                    s.setCourseId(courseId);
                    s.setFacultyId(facultyId);

                    SubjectDAO sdao = new SubjectDAO();
                    sdao.addSubject(s);

                    resp.sendRedirect("adminPanel?action=subjects");
                    return;

                case "saveNotice":
                    dao.addNotice(
                            req.getParameter("title"),
                            req.getParameter("message")
                    );
                    resp.sendRedirect("adminPanel?action=dashboard&msg=NoticeAdded");
                    return;
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
