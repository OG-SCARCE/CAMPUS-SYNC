<%@ page import="java.sql.ResultSet" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession s = request.getSession(false);
    if (s == null || !"student".equals(s.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Your Attendance - CampusSync</title>

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

        /* Added modern header styling */
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 30px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            animation: slideDown 0.4s ease-out;
        }

        .header a {
            color: rgba(255, 255, 255, 0.9);
            text-decoration: none;
            font-size: 14px;
            transition: opacity 0.3s;
            display: inline-flex;
            align-items: center;
        }

        .header a:hover {
            opacity: 0.7;
        }

        .header a:hover .back-arrow {
            transform: translateX(-3px);
        }

        .back-arrow {
            display: inline-block;
            margin-right: 6px;
            animation: slideInLeft 0.4s ease-out;
            transition: transform 0.3s;
        }

        .container {
            padding: 40px 30px;
            max-width: 900px;
            margin: 0 auto;
        }

        h2 {
            font-size: 28px;
            margin-bottom: 30px;
            color: #1a202c;
        }

        /* Modern table design */
        .table-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e2e8f0;
            overflow-x: auto;
            animation: fadeInUp 0.5s ease-out;
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

        table tr {
            transition: all 0.2s ease-out;
        }

        table tr:hover {
            background: #f7fafc;
            box-shadow: inset 0 0 10px rgba(102, 126, 234, 0.08);
        }

        .present {
            color: #22863a;
            font-weight: 600;
            padding: 6px 12px;
            background: #f0f9f4;
            border-radius: 4px;
            display: inline-block;
            transition: all 0.3s;
        }

        .absent {
            color: #cb2431;
            font-weight: 600;
            padding: 6px 12px;
            background: #fdeef0;
            border-radius: 4px;
            display: inline-block;
            transition: all 0.3s;
        }

        .present:hover {
            transform: scale(1.05);
            box-shadow: 0 2px 8px rgba(34, 134, 58, 0.2);
        }

        .absent:hover {
            transform: scale(1.05);
            box-shadow: 0 2px 8px rgba(203, 36, 49, 0.2);
        }

        @keyframes slideDown {
            from {
                opacity: 0;
                transform: translateY(-20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes slideInLeft {
            from {
                opacity: 0;
                transform: translateX(-10px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }
    </style>
</head>

<body>

<div class="header">
    <h2>Your Attendance</h2>
    <!-- Added SVG back arrow icon -->
    <a href="<%= request.getContextPath() %>/student">
        <svg class="back-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 18px; height: 18px;">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
        Back to Dashboard
    </a>
</div>

<div class="container">
    <div class="table-section">
        <table border="1" cellpadding="5">
            <tr>
                <th>Subject</th>
                <th>Date</th>
                <th>Status</th>
            </tr>

        <%
            ResultSet rs = (ResultSet) request.getAttribute("attendanceList");

            while (rs != null && rs.next()) {
                String status = rs.getString("status");
                String css = status.equals("P") ? "present" : "absent";
        %>
            <tr>
                <td><%= rs.getString("subject_name") %></td>
                <td><%= rs.getDate("att_date") %></td>
                <td><span class="<%= css %>">
                    <%= status.equals("P") ? "Present" : "Absent" %>
                </span></td>
            </tr>
        <%
            }
        %>

        </table>
    </div>
</div>

</body>
</html>
