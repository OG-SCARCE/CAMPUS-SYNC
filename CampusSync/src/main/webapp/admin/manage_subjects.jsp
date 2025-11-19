<%@ page import="java.sql.ResultSet" %>

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
    <title>Manage Subjects - CampusSync</title>

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f7fafc;
            color: #2d3748;
        }

        .header {
            background: linear-gradient(135deg,#667eea 0%,#764ba2 100%);
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
            margin-top: 4px;
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

        h3 {
            margin-bottom: 18px;
            font-size: 20px;
            font-weight: 700;
        }

        .form-group { margin-bottom: 18px; }

        .form-group label {
            font-size: 14px;
            font-weight: 600;
            margin-bottom: 6px;
            display: block;
            color: #2d3748;
        }

        input[type="text"], select {
            width: 100%;
            max-width: 350px;
            padding: 10px 14px;
            border: 2px solid #e2e8f0;
            border-radius: 6px;
            font-size: 14px;
            transition: .3s;
        }

        input:focus, select:focus {
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

        button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 16px rgba(102,126,234,.35);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 14px;
        }

        table th {
            background: #f7fafc;
            padding: 14px;
            font-weight: 600;
            color: #2d3748;
            border-bottom: 2px solid #e2e8f0;
            text-align: left;
        }

        table td {
            padding: 14px;
            border-bottom: 1px solid #e2e8f0;
            color: #4a5568;
        }

        table tr:hover {
            background: #f7fafc;
        }

        .actions a {
            font-weight: 600;
            text-decoration: none;
            margin-right: 10px;
        }

        .actions a.edit { color: #667eea; }
        .actions a.delete { color: #e53e3e; }
    </style>
</head>

<body>

<div class="header">
    <h2>Manage Subjects</h2>
    <a href="<%= request.getContextPath() %>/adminPanel">
        <svg class="back-arrow" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" style="width:18px;height:18px;">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
        Back to Dashboard
    </a>
</div>

<div class="container">

    <!-- Add Subject Form -->
    <div class="form-section">
        <h3>Add New Subject</h3>

        <!-- Updated form to submit through AdminServlet instead of SubjectServlet -->
        <form action="<%= request.getContextPath() %>/adminPanel" method="post">
            <input type="hidden" name="action" value="addSubject">

            <div class="form-group">
                <label>Subject Name</label>
                <input type="text" name="subject_name" required>
            </div>

            <div class="form-group">
                <label>Select Course</label>
                <select name="course_id" required>
                    <%
                        ResultSet crs = (ResultSet) request.getAttribute("courses");
                        while (crs != null && crs.next()) {
                    %>
                    <option value="<%= crs.getInt("course_id") %>">
                        <%= crs.getString("course_name") %>
                    </option>
                    <% } %>
                </select>
            </div>

            <div class="form-group">
                <label>Select Faculty</label>
                <select name="faculty_id" required>
                    <%
                        ResultSet frs = (ResultSet) request.getAttribute("faculty");
                        while (frs != null && frs.next()) {
                    %>
                    <option value="<%= frs.getInt("faculty_id") %>">
                        <%= frs.getString("name") %>
                    </option>
                    <% } %>
                </select>
            </div>

            <button type="submit">Add Subject</button>
        </form>
    </div>

    <!-- Subject List -->
    <div class="table-section">
        <h3>Subject List</h3>

        <table>
            <tr>
                <th>ID</th>
                <th>Subject</th>
                <th>Course</th>
                <th>Faculty</th>
                <th>Actions</th>
            </tr>

            <%
                ResultSet rs = (ResultSet) request.getAttribute("subjectData");

                while (rs != null && rs.next()) {
            %>
            <tr>
                <td><%= rs.getInt("subject_id") %></td>
                <td><%= rs.getString("subject_name") %></td>
                <td><%= rs.getString("course_name") %></td>
                <td><%= rs.getString("faculty_name") %></td>

                <td class="actions">
                    <a class="edit" href="edit_subject.jsp?id=<%=rs.getInt("subject_id")%>">Edit</a>
                    <a class="delete" href="delete_subject.jsp?id=<%=rs.getInt("subject_id")%>">Delete</a>
                </td>
            </tr>
            <% } %>

        </table>
    </div>

</div>

</body>
</html>
