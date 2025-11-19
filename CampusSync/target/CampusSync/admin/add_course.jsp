<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession s = request.getSession(false);
    if (s == null || !"admin".equals(s.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Add New Course - CampusSync</title>

    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f7fafc;
            color: #2d3748;
        }

        /* Header same as other pages */
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

        /* Page container */
        .container {
            max-width: 900px;
            margin: 30px auto;
            padding: 0 20px;
        }

        /* Card form section */
        .form-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            border: 1px solid #e2e8f0;
        }

        .form-section h3 {
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

        input[type="text"] {
            width: 100%;
            max-width: 350px;
            padding: 10px 14px;
            border: 2px solid #e2e8f0;
            border-radius: 6px;
            font-size: 14px;
            transition: .3s;
        }

        input[type="text"]:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102,126,234,.15);
            outline: none;
        }

        /* Buttons */
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
            margin-top: 10px;
        }

        button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 16px rgba(102,126,234,.35);
        }
    </style>
</head>

<body>

<div class="header">
    <h2>Add New Course</h2>

    <a href="<%= request.getContextPath() %>/admin/manage_courses.jsp">
        <svg class="back-arrow" viewBox="0 0 24 24" fill="none"
             stroke="currentColor" stroke-width="2" style="width:18px;height:18px;">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
        Back to Courses
    </a>
</div>

<div class="container">

    <div class="form-section">
        <h3>Create Course</h3>

        <form action="${pageContext.request.contextPath}/CourseServlet" method="post">

            <div class="form-group">
                <label for="course_name">Course Name</label>
                <input type="text" id="course_name" name="course_name" required>
            </div>

            <button type="submit">Add Course</button>
        </form>
    </div>

</div>

</body>
</html>
