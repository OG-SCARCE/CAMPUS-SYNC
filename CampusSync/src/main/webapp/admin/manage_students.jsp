<%@ page import="java.util.List" %>
<%@ page import="com.campussync.model.Student" %>

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
    <title>Manage Students - CampusSync</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f7fafc;
            color: #2d3748;
        }

        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 30px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            animation: slideDown 0.4s ease-out;
        }

        @keyframes slideDown {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .back-arrow {
            display: inline-block;
            margin-right: 6px;
            transition: transform 0.3s;
        }

        .header a {
            color: rgba(255, 255, 255, 0.9);
            text-decoration: none;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
        }

        .form-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            margin-bottom: 40px;
            border: 1px solid #e2e8f0;
            animation: fadeInUp 0.5s ease-out;
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .table-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e2e8f0;
            overflow-x: auto;
            animation: fadeInUp 0.5s ease-out 0.1s both;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        table th {
            background: #f7fafc;
            padding: 14px;
            text-align: left;
            font-weight: 600;
            color: #2d3748;
            border-bottom: 2px solid #e2e8f0;
            font-size: 14px;
        }

        table td {
            padding: 14px;
            border-bottom: 1px solid #e2e8f0;
            color: #4a5568;
        }

        table tr:hover {
            background: #f7fafc;
            box-shadow: inset 0 0 10px rgba(102, 126, 234, 0.08);
        }

        .form-group {
            margin-bottom: 16px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 6px;
            color: #2d3748;
            font-size: 14px;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="number"] {
            width: 100%;
            max-width: 300px;
            padding: 10px 12px;
            border: 2px solid #e2e8f0;
            border-radius: 6px;
            font-size: 14px;
            transition: all 0.3s;
        }

        input:focus {
            outline: none;
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
        }

        button {
            padding: 10px 24px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 6px;
            transition: all 0.3s;
            margin-top: 10px;
        }

        button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 16px rgba(102, 126, 234, 0.35);
        }

        .container {
            padding: 40px 30px;
            max-width: 1200px;
            margin: 0 auto;
        }
    </style>
</head>

<body>

<div class="header">
    <h2>Manage Students</h2>
    <a href="<%= request.getContextPath() %>/adminPanel">
        <svg class="back-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 18px; height: 18px;">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
        Back to Dashboard
    </a>
</div>

<div class="container">
    <div class="form-section">
        <h3>Add New Student</h3>

        <form action="<%= request.getContextPath() %>/adminPanel" method="post">
            <input type="hidden" name="action" value="addStudent">

            <div class="form-group">
                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" required>
            </div>

            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="course">Course</label>
                <input type="text" id="course" name="course" required>
            </div>

            <div class="form-group">
                <label for="semester">Semester</label>
                <input type="number" id="semester" name="semester" min="1" required>
            </div>

            <button type="submit">Add Student</button>
        </form>
    </div>

    <div class="table-section">
        <h3>Student List</h3>

        <table border="1" cellpadding="5">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Course</th>
                <th>Semester</th>
            </tr>

            <%
                List<Student> students = (List<Student>) request.getAttribute("students");

                if (students != null && !students.isEmpty()) {
                    for (Student student : students) {
            %>
                <tr>
                    <td><%= student.getStudentId() %></td>
                    <td><%= student.getName() %></td>
                    <td><%= student.getEmail() %></td>
                    <td><%= student.getCourse() %></td>
                    <td><%= student.getSemester() %></td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="5" style="text-align:center; padding:20px;">No students available</td>
                </tr>
            <% } %>

        </table>
    </div>
</div>

</body>
</html>
