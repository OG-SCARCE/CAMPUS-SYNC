<%@ page import="javax.servlet.http.HttpSession" %>

<%
    HttpSession s = request.getSession(false);
    if (s == null || !"faculty".equals(s.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Enter Marks - CampusSync</title>

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
            transition: opacity 0.3s, transform 0.3s;
            display: inline-flex;
            align-items: center;
        }

        .header a:hover {
            opacity: 0.7;
            transform: translateX(-3px);
        }

        .header a .back-arrow {
            display: inline-block;
            margin-right: 6px;
            animation: slideInLeft 0.4s ease-out;
        }

        .container {
            padding: 40px 30px;
            max-width: 600px;
            margin: 0 auto;
        }

        h2 {
            font-size: 28px;
            margin-bottom: 30px;
            color: #1a202c;
        }

        /* Modernized form styling */
        .form-section {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            border: 1px solid #e2e8f0;
            animation: fadeInUp 0.5s ease-out;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 8px;
            color: #2d3748;
            font-size: 14px;
        }

        input[type="number"] {
            width: 100%;
            padding: 10px 12px;
            border: 2px solid #e2e8f0;
            border-radius: 6px;
            font-size: 14px;
            transition: all 0.3s;
        }

        input[type="number"]:focus {
            outline: none;
            border-color: #667eea;
            animation: inputFocus 0.3s ease-out;
        }

        button {
            width: 100%;
            padding: 12px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            color: white;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            border-radius: 6px;
            transition: all 0.3s;
            margin-top: 10px;
        }

        button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 16px rgba(102, 126, 234, 0.35);
            animation: buttonShine 0.6s ease-out;
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

        @keyframes inputFocus {
            from {
                box-shadow: 0 0 0 0px rgba(102, 126, 234, 0.2);
            }
            to {
                box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            }
        }

        @keyframes buttonShine {
            0% {
                box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
            }
            50% {
                box-shadow: 0 8px 20px rgba(102, 126, 234, 0.5);
            }
            100% {
                box-shadow: 0 8px 16px rgba(102, 126, 234, 0.35);
            }
        }
    </style>
</head>

<body>

<div class="header">
    <h2>Enter Marks</h2>
    <!-- Added SVG back arrow icon -->
    <a href="<%= request.getContextPath() %>/faculty">
        <svg class="back-arrow" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" style="width: 18px; height: 18px;">
            <polyline points="15 18 9 12 15 6"></polyline>
        </svg>
        Back to Dashboard
    </a>
</div>

<div class="container">
    <div class="form-section">
        <form action="<%= request.getContextPath() %>/faculty" method="post">
            <input type="hidden" name="action" value="enterMarks">

            <div class="form-group">
                <label for="student_id">Student ID</label>
                <input type="number" id="student_id" name="student_id" required>
            </div>

            <div class="form-group">
                <label for="subject_id">Subject ID</label>
                <input type="number" id="subject_id" name="subject_id" required>
            </div>

            <div class="form-group">
                <label for="marks">Marks</label>
                <input type="number" id="marks" name="marks" min="0" required>
            </div>

            <button type="submit">Save Marks</button>
        </form>
    </div>
</div>

</body>
</html>
