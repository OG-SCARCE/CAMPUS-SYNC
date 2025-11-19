<%@ page language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login - Campus Sync</title>

    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            padding: 20px;
        }

        .login-container {
            background: white;
            padding: 40px;
            border-radius: 12px;
            width: 100%;
            max-width: 400px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
            animation: slideIn 0.5s ease-out;
        }

        @keyframes slideIn {
            from {
                opacity: 0;
                transform: translateY(20px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes focusPulse {
            0% {
                box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            }
            50% {
                box-shadow: 0 0 0 6px rgba(102, 126, 234, 0.2);
            }
            100% {
                box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
            }
        }

        h2 {
            text-align: center;
            color: #1a202c;
            margin-bottom: 8px;
            font-size: 28px;
        }

        .subtitle {
            text-align: center;
            color: #718096;
            font-size: 14px;
            margin-bottom: 30px;
        }

        .home-link {
            text-align: center;
            margin-bottom: 20px;
        }

        .home-link a {
            color: #667eea;
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s;
        }

        .home-link a:hover {
            color: #764ba2;
        }

        hr {
            border: none;
            height: 1px;
            background: #e2e8f0;
            margin-bottom: 25px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            color: #2d3748;
            font-weight: 600;
            margin-bottom: 8px;
            font-size: 14px;
        }

        input, select {
            width: 100%;
            padding: 12px 14px;
            border: 2px solid #e2e8f0;
            border-radius: 8px;
            font-size: 14px;
            font-family: inherit;
            transition: all 0.3s;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
            animation: focusPulse 0.6s ease-out;
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
            border-radius: 8px;
            margin-top: 10px;
            transition: all 0.3s;
            box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
        }

        @keyframes buttonBounce {
            0%, 100% {
                transform: translateY(-2px);
            }
            50% {
                transform: translateY(0);
            }
        }

        button:hover {
            animation: buttonBounce 0.6s ease-out;
            box-shadow: 0 6px 20px rgba(102, 126, 234, 0.6);
        }

        button:active {
            transform: translateY(0);
        }

        .error {
            color: #e53e3e;
            font-weight: 600;
            margin-top: 20px;
            padding: 12px;
            background: #fff5f5;
            border-radius: 8px;
            border-left: 4px solid #e53e3e;
            font-size: 14px;
        }

        .back-arrow {
            display: inline-block;
            width: 20px;
            height: 20px;
            margin-right: 8px;
            animation: slideInLeft 0.4s ease-out;
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

        .back-arrow:hover {
            animation: arrowHover 0.3s ease-out forwards;
        }

        @keyframes arrowHover {
            to {
                transform: translateX(-3px);
            }
        }
    </style>
</head>

<body>

<div class="login-container">
    <h2>Campus Sync</h2>
    <p class="subtitle">College Management System</p>

    <div style="display: flex; justify-content: center; margin-top: 12px; margin-bottom: 25px;">
        <a href="index.jsp"
           style="
                display: flex;
                align-items: center;
                gap: 6px;
                color: #667eea;
                text-decoration: none;
                font-size: 15px;
                font-weight: 500;
                transition: 0.25s ease;
           "
           onmouseover="this.style.color='#764ba2'; this.children[0].style.transform='translateX(-3px)';"
           onmouseout="this.style.color='#667eea'; this.children[0].style.transform='translateX(0)';">

            <svg width="16" height="16" viewBox="0 0 24 24" fill="none"
                 stroke="currentColor" stroke-width="2"
                 style="transition: 0.25s ease;">
                <polyline points="15 18 9 12 15 6"></polyline>
            </svg>

            Back to Home
        </a>
    </div>



    <form action="auth" method="post">
        <div class="form-group">
            <label for="email">Email or Username</label>
            <input type="text" id="email" name="email" required>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="role">Select Role</label>
            <select id="role" name="role" required>
                <option value="">Choose your role...</option>
                <option value="student">Student</option>
                <option value="faculty">Faculty</option>
                <option value="admin">Administrator</option>
            </select>
        </div>

        <button type="submit">Sign In</button>
    </form>

    <%
        if (request.getAttribute("error") != null) {
    %>
        <p class="error"><%= request.getAttribute("error") %></p>
    <%
        }
    %>
</div>

</body>
</html>
