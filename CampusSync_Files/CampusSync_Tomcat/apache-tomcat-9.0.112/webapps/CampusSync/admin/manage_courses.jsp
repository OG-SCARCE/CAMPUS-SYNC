<%@ page import="java.util.List" %>
<%@ page import="com.campussync.model.Course" %>

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
    <title>Manage Courses - CampusSync</title>

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f7fafc;
            color: #2d3748;
        }

        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 30px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }

        .header a {
            color: rgba(255,255,255,0.9);
            text-decoration: none;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
        }

        .back-arrow { margin-right: 6px; }

        .container {
            max-width: 900px;
            margin: 30px auto;
            padding: 0 20px;
        }

        .form-section, .table-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-bottom: 40px;
            border: 1px solid #e2e8f0;
        }

        h3 { margin-bottom: 18px; font-size: 20px; font-weight: 700; }

        .form-group { margin-bottom: 18px; }
        label { font-size: 14px; font-weight: 600; display: block; margin-bottom: 6px; }

        input[type="text"] {
            width: 100%;
            max-width: 300px;
            padding: 10px 14px;
            border: 2px solid #e2e8f0;
            border-radius: 6px;
            font-size: 14px;
            transition: .3s;
        }

        input:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102,126,234,.15);
            outline: none;
        }

        button {
            padding: 10px 24px;
            background: linear-gradient(135deg,#667eea 0%,#764ba2 100%);
            border: none;
            color: white;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 6px;
            transition: .3s;
        }

        button:hover { transform: translateY(-3px); box-shadow: 0 8px 16px rgba(102,126,234,.35); }

        table { width: 100%; border-collapse: collapse; font-size: 14px; }
        th { background: #f7fafc; padding: 14px; font-weight: 600; border-bottom: 2px solid #e2e8f0; text-align: left; }
        td { padding: 14px; border-bottom: 1px solid #e2e8f0; color: #4a5568; }
        tr:hover { background: #f7fafc; }

        .actions a { color: #667eea; font-weight: 600; margin-right: 12px; text-decoration: none; }
        .actions a.delete { color: #e53e3e; }
    </style>
</head>

<body>

<div class="header">
    <h2>Manage Courses</h2>
    <a href="<%= request.getContextPath() %>/adminPanel">
        <svg class="back-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
             style="width: 18px; height: 18px;">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
        Back to Dashboard
    </a>
</div>

<div class="container">

    <!-- Add Course Form -->
    <div class="form-section">
        <h3>Add New Course</h3>

        <form action="<%= request.getContextPath() %>/CourseServlet" method="post">
            <div class="form-group">
                <label>Course Name</label>
                <input type="text" name="course_name" required>
            </div>

            <button type="submit">Add Course</button>
        </form>
    </div>

    <!-- Course List -->
    <div class="table-section">
        <h3>Course List</h3>

        <table>
            <tr>
                <th>ID</th>
                <th>Course</th>
                <th>Actions</th>
            </tr>

            <%
                List<Course> courses = (List<Course>) request.getAttribute("courseData");

                if (courses != null && !courses.isEmpty()) {
                    for (Course c : courses) {
            %>
            <tr>
                <td><%= c.getCourseId() %></td>
                <td><%= c.getCourseName() %></td>
                <td class="actions">
                    <a href="<%= request.getContextPath() %>/adminPanel?action=editCourse&id=<%= c.getCourseId() %>">Edit</a>
                    <a href="<%= request.getContextPath() %>/adminPanel?action=deleteCourse&id=<%= c.getCourseId() %>" class="delete">Delete</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="3" style="text-align:center; padding:20px;">No courses available</td>
            </tr>
            <% } %>

        </table>
    </div>

</div>

</body>
</html>
