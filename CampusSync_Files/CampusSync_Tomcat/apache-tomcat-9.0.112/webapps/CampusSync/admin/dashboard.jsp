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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <title>Admin Dashboard - CampusSync</title>
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

        /* Added modern header with gradient background */
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 20px 30px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
            animation: slideDown 0.4s ease-out;
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

        .header h2 {
            font-size: 24px;
            font-weight: 700;
        }

        .header-user {
            display: flex;
            align-items: center;
            gap: 20px;
        }

        .logout-btn {
            background: rgba(255, 255, 255, 0.2);
            color: white;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            transition: all 0.3s;
            border: 1px solid rgba(255, 255, 255, 0.3);
        }

        .logout-btn:hover {
            background: rgba(255, 255, 255, 0.3);
            animation: buttonPulse 0.4s ease-out;
        }

        @keyframes buttonPulse {
            0%, 100% {
                transform: scale(1);
            }
            50% {
                transform: scale(1.05);
            }
        }

        .container {
            padding: 40px 30px;
            max-width: 1200px;
            margin: 0 auto;
        }

        /* Added modern card-based layout for options */
        .options-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }

        .option-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
            transition: all 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
            border: 1px solid #e2e8f0;
            cursor: pointer;
            animation: cardFadeIn 0.5s ease-out forwards;
            opacity: 0;
        }

        @keyframes cardFadeIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        .options-grid .option-card:nth-child(1) { animation-delay: 0.1s; }
        .options-grid .option-card:nth-child(2) { animation-delay: 0.2s; }
        .options-grid .option-card:nth-child(3) { animation-delay: 0.3s; }

        .option-card:hover {
            transform: translateY(-8px) scale(1.02);
            box-shadow: 0 12px 28px rgba(102, 126, 234, 0.25);
            border-color: #667eea;
        }

        .icon {
            font-size: 40px;
            margin-bottom: 10px;
            color: #333;
            display: inline-block;
            transition: transform 0.3s cubic-bezier(0.34, 1.56, 0.64, 1);
        }

        .option-card:hover .icon {
            transform: scale(1.3) rotate(5deg);
        }

        .option-card a {
            display: block;
            color: #667eea;
            text-decoration: none;
            font-weight: 600;
            font-size: 18px;
            transition: color 0.3s;
        }

        .option-card a:hover {
            color: #764ba2;
        }

        .option-card p {
            color: #718096;
            font-size: 14px;
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div class="header">
    <h2>Admin Dashboard</h2>
    <div class="header-user">
        <span>Welcome, <%= s.getAttribute("name") %></span>
        <a href="<%= request.getContextPath() %>/auth?action=logout" class="logout-btn">Logout</a>
    </div>
</div>

<div class="container">
    <h3 style="color: #2d3748; font-size: 20px; margin-bottom: 10px;">Admin Options</h3>
    <p style="color: #718096; margin-bottom: 30px;">Manage students, faculty, and system notices</p>

    <div class="options-grid">

        <!-- Manage Students -->
        <div class="option-card">
            <div class="icon"><i class="fas fa-user-graduate"></i></div>
            <a href="<%= request.getContextPath() %>/adminPanel?action=students">Manage Students</a>
            <p>Add, view, and manage student records and enrollment</p>
        </div>

        <div class="option-card">
            <div class="icon"><i class="fas fa-clipboard-list"></i></div>
            <a href="<%= request.getContextPath() %>/adminPanel?action=notices">View Notices</a>
            <p>View Latest Updates and Notices</p>
        </div>

        <!-- Manage Faculty -->
        <div class="option-card">
            <div class="icon"><i class="fas fa-chalkboard-teacher"></i></div>
            <a href="<%= request.getContextPath() %>/adminPanel?action=faculty">Manage Faculty</a>
            <p>Add, view, and manage faculty member information</p>
        </div>

        <!-- Manage Courses -->
        <div class="option-card">
            <div class="icon"><i class="fas fa-book"></i></div>
            <a href="<%= request.getContextPath() %>/adminPanel?action=courses">Manage Courses</a>
            <p>Add, update and organize all academic courses offered</p>
        </div>

        <!-- Manage Subjects -->
        <div class="option-card">
            <div class="icon"><i class="fas fa-book-open"></i></div>
            <a href="<%= request.getContextPath() %>/adminPanel?action=subjects">Manage Subjects</a>
            <p>Define course subjects, assign faculty and update subjects</p>
        </div>

        <!-- Publish Notice -->
        <div class="option-card">
            <div class="icon"><i class="fas fa-bullhorn"></i></div>
            <a href="<%= request.getContextPath() %>/adminPanel?action=addNotice">Publish Notice</a>
            <p>Create and broadcast important announcements</p>
        </div>

    </div>
</div>

</body>
</html>
