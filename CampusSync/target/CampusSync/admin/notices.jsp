<%@ page import="java.sql.ResultSet" %>
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
    <title>All Notices</title>

    <style>
        body {
            margin: 0;
            padding: 0;
            background: #f7fafc;
            font-family: 'Segoe UI', Tahoma, sans-serif;
            color: #2d3748;
        }

        .header {
            background: linear-gradient(135deg,#667eea 0%,#764ba2 100%);
            padding: 20px 30px;
            color: #fff;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header h2 { margin: 0; }

        .back-btn {
            background: rgba(255,255,255,0.2);
            padding: 8px 16px;
            border-radius: 6px;
            color: #fff;
            font-size: 14px;
            text-decoration: none;
            transition: 0.3s;
        }
        .back-btn:hover {
            background: rgba(255,255,255,0.35);
        }

        .container {
            max-width: 1000px;
            margin: 40px auto;
            background: white;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
        }

        h3 {
            margin-bottom: 20px;
            font-size: 22px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            font-size: 15px;
        }

        th {
            background: #f1f5f9;
            padding: 14px;
            text-align: left;
            border-bottom: 2px solid #e2e8f0;
            font-weight: 600;
        }

        td {
            padding: 14px;
            border-bottom: 1px solid #edf2f7;
            vertical-align: top;
        }

        tr:hover {
            background: #f8fafc;
        }

        .date {
            white-space: nowrap; /* 🌟 prevents multi-line date issue */
        }
    </style>
</head>

<body>

<div class="header">
    <h2>All Notices</h2>
    <a href="<%= request.getContextPath() %>/adminPanel" class="back-btn">Back</a>
</div>

<div class="container">

    <h3>Latest Announcements</h3>

    <table>
        <tr>
            <th>Title</th>
            <th>Message</th>
            <th>Date</th>
        </tr>

        <%
            ResultSet rs = (ResultSet) request.getAttribute("noticeList");

            if (rs != null && rs.next()) {
                do {
        %>

        <tr>
            <td><%= rs.getString("title") %></td>
            <td><%= rs.getString("message") %></td>
            <td class="date" style="text-align:center;">
                <%
                    String datetime = rs.getString("posted_at");
                    String[] parts = datetime.split(" ");
                    String date = parts[0];
                    String time = parts.length > 1 ? parts[1] : "";
                %>

                <div style="font-weight:600;"><%= date %></div>
                <div style="font-size:13px; color:#4a5568; margin-top:2px;"><%= time %></div>
            </td>

        </tr>

        <% } while (rs.next()); } else { %>

        <tr>
            <td colspan="3" style="text-align:center; padding: 20px;">No notices available</td>
        </tr>

        <% } %>

    </table>

</div>

</body>
</html>
