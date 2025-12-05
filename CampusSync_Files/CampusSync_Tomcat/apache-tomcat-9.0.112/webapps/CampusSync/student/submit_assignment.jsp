<%@ page import="java.util.List" %>
<%@ page import="com.campussync.model.Assignment" %>
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <title>Submit Assignment - CampusSync</title>

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

        .back-arrow {
            display: inline-block;
            margin-right: 6px;
            transition: transform 0.3s;
        }

        .header a:hover .back-arrow {
            transform: translateX(-3px);
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

        /* Grid layout matching other cards */
        .assignment-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            animation: fadeInUp 0.5s ease-out;
        }

        .assignment-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e2e8f0;
            transition: all 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
        }

        .assignment-card:hover {
            transform: translateY(-8px);
            box-shadow: 0 12px 28px rgba(102, 126, 234, 0.25);
            border-color: #667eea;
        }

        .assignment-title {
            font-size: 18px;
            font-weight: 600;
            color: #2d3748;
            margin-bottom: 8px;
        }

        .assignment-subject {
            font-size: 14px;
            color: #667eea;
            font-weight: 600;
            margin-bottom: 10px;
        }

        .assignment-description {
            font-size: 14px;
            color: #718096;
            margin-bottom: 15px;
            line-height: 1.5;
        }

        .assignment-due {
            font-size: 13px;
            color: #cb2431;
            font-weight: 600;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .submit-form {
            display: flex;
            flex-direction: column;
            gap: 10px;
        }

        .file-input-wrapper {
            position: relative;
            overflow: hidden;
            display: inline-block;
            width: 100%;
        }

        .file-input-wrapper input[type=file] {
            position: absolute;
            left: -9999px;
        }

        .file-input-label {
            display: block;
            padding: 8px 12px;
            background: #f0f4ff;
            color: #667eea;
            border: 2px dashed #667eea;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
            font-size: 13px;
            text-align: center;
            transition: all 0.3s;
        }

        .file-input-label:hover {
            background: #667eea;
            color: white;
        }

        .file-name {
            font-size: 12px;
            color: #22863a;
            margin-top: 5px;
            font-weight: 500;
        }

        .submit-btn {
            padding: 10px 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 6px;
            transition: all 0.3s;
        }

        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 14px rgba(102, 126, 234, 0.3);
        }

        .no-assignments {
            text-align: center;
            padding: 40px;
            color: #718096;
            font-size: 16px;
        }

        @keyframes slideDown {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes fadeInUp {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
    </style>

    <script>
        function updateFileName(input, cardId) {
            const fileName = input.files[0]?.name || "No file chosen";
            document.getElementById('file-name-' + cardId).textContent = "Selected: " + fileName;
        }
    </script>
</head>

<body>

<div class="header">
    <h2>Submit Assignment</h2>
    <a href="<%= request.getContextPath() %>/student">
        <svg class="back-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 18px; height: 18px;">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
        Back to Dashboard
    </a>
</div>

<div class="container">
    <div class="assignment-grid">
        <%
            List<Assignment> assignmentList = (List<Assignment>) request.getAttribute("assignmentList");
            int cardCounter = 0;
            
            if (assignmentList != null && !assignmentList.isEmpty()) {
                for (Assignment assignment : assignmentList) {
                    cardCounter++;
        %>
                    <div class="assignment-card">
                        <div class="assignment-subject"><%= assignment.getSubjectName() %></div>
                        <div class="assignment-title"><%= assignment.getTitle() %></div>
                        <div class="assignment-description"><%= assignment.getDescription() %></div>
                        <div class="assignment-due">
                            <i class="fas fa-calendar-alt"></i>
                            Due: <%= assignment.getDueDate() %>
                        </div>

                        <form class="submit-form" action="<%= request.getContextPath() %>/student" method="post" enctype="multipart/form-data">
                            <input type="hidden" name="action" value="submitAssignmentFile">
                            <input type="hidden" name="assignment_id" value="<%= assignment.getAssignmentId() %>">

                            <div class="file-input-wrapper">
                                <input type="file" id="file-<%= cardCounter %>" name="file" required onchange="updateFileName(this, <%= cardCounter %>)">
                                <label for="file-<%= cardCounter %>" class="file-input-label">
                                    <i class="fas fa-cloud-upload-alt"></i> Choose File
                                </label>
                            </div>
                            <div id="file-name-<%= cardCounter %>" class="file-name">No file chosen</div>

                            <button type="submit" class="submit-btn">Submit</button>
                        </form>
                    </div>
        <%
                }
            }
            
            if (cardCounter == 0) {
        %>
                <div class="no-assignments">
                    <i class="fas fa-inbox" style="font-size: 48px; margin-bottom: 15px; color: #cbd5e0;"></i>
                    <p>No pending assignments at this time</p>
                </div>
        <%
            }
        %>
    </div>
</div>

</body>
</html>
