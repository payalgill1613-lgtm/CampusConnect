
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    if (session.getAttribute("studentId") == null) {
        response.sendRedirect(
            request.getContextPath() + "/login.jsp");
        return;
    }

    String studentName =
        (String) session.getAttribute("studentName");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Connect - Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            background: #f1f5f9;
            color: #1e293b;
        }
        header {
            background: #1d4ed8;
            color: white;
            padding: 22px;
            text-align: center;
        }
        .container {
            max-width: 850px;
            margin: 35px auto;
            padding: 20px;
        }
        .cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 18px;
        }
        .card {
            background: white;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0 3px 12px #0001;
            text-align: center;
        }
        .card a {
            display: inline-block;
            background: #2563eb;
            color: white;
            text-decoration: none;
            padding: 10px 18px;
            border-radius: 6px;
        }
        .logout {
            background: #dc2626 !important;
        }
        footer {
            text-align: center;
            padding: 20px;
            color: #64748b;
        }
    </style>
</head>
<body>

<header>
    <h1>Campus Connect</h1>
    <p>Welcome, <%= studentName == null ? "Student" : studentName %>!</p>
</header>

<div class="container">
    <h2>Student Dashboard</h2>
    <p>Choose what you would like to do.</p>

    <div class="cards">
        <div class="card">
            <h3>📝 Feedback</h3>
            <p>Share your thoughts about campus life.</p>
            <a href="feedback.jsp">Give Feedback</a>
        </div>

        <div class="card">
            <h3>🗳️ Campus Poll</h3>
            <p>Vote for your preferred campus activity.</p>
            <a href="${pageContext.request.contextPath}/vote">Open Poll</a>
        </div>

        <div class="card">
            <h3>🚪 Logout</h3>
            <p>Securely end your session.</p>
            <a class="logout"
               href="${pageContext.request.contextPath}/logout">Logout</a>
        </div>
    </div>
</div>

<footer>
    Campus Connect | Student Portal
</footer>

</body>
</html>
