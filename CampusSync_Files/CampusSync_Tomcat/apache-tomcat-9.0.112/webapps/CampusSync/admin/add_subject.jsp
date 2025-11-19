<%@ page import="java.util.List" %>
<%@ page import="com.campussync.model.Course" %>
<%@ page import="com.campussync.model.Faculty" %>

<%
    javax.servlet.http.HttpSession s = request.getSession(false);
    if (s == null || !"admin".equals(s.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add Subject</title>

    <style>
        body {
            font-family: Arial;
            background: #f7fafc;
        }
        .container {
            max-width: 700px;
            margin: 30px auto;
            background: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
        }
        h2 { margin-bottom: 20px; }
        label { font-weight: 600; }
        select, input {
            width: 100%;
            padding: 10px;
            margin-top: 8px;
            margin-bottom: 18px;
            border: 2px solid #d7dce2;
            border-radius: 6px;
        }
        button {
            background: linear-gradient(135deg,#667eea,#764ba2);
            border: none;
            padding: 12px 22px;
            color: white;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
        }
    </style>
</head>

<body>
<div class="container">
    <h2>Add New Subject</h2>

    <form action="adminPanel?action=addSubject" method="post">

        <label>Subject Name</label>
        <input type="text" name="subject_name" required>

        <label>Select Course</label>
        <select name="course_id" required>
            <%
                List<Course> courses = (List<Course>) request.getAttribute("courses");
                for (Course c : courses) {
            %>
                <option value="<%= c.getCourseId() %>"><%= c.getCourseName() %></option>
            <% } %>
        </select>

        <label>Select Faculty</label>
        <select name="faculty_id" required>
            <%
                List<Faculty> facultyList = (List<Faculty>) request.getAttribute("facultyList");
                for (Faculty f : facultyList) {
            %>
                <option value="<%= f.getFacultyId() %>"><%= f.getName() %></option>
            <% } %>
        </select>

        <button type="submit">Add Subject</button>

    </form>
</div>
</body>
</html>
