
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Connect</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #eff6ff, #dbeafe);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }

        .container {
            max-width: 650px;
            width: 100%;
            padding: 45px 30px;
            text-align: center;
            background: white;
            border-radius: 18px;
            box-shadow: 0 8px 25px #1e3a8a20;
        }

        h1 {
            color: #1d4ed8;
            font-size: 36px;
        }

        p {
            color: #475569;
            line-height: 1.7;
        }

        .button {
            display: inline-block;
            margin: 12px 5px;
            padding: 12px 25px;
            background: #2563eb;
            color: white;
            text-decoration: none;
            border-radius: 7px;
        }

        .secondary {
            background: white;
            color: #2563eb;
            border: 1px solid #2563eb;
        }

        footer {
            margin-top: 30px;
            font-size: 13px;
            color: #64748b;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Campus Connect</h1>

    <h2>Your Campus, Your Voice!</h2>

    <p>
        Welcome to Campus Connect, a student portal where you can
        share feedback, participate in campus polls, and make
        your voice heard.
    </p>

    <a class="button"
       href="${pageContext.request.contextPath}/login.jsp">
        Student Login
    </a>

    <a class="button secondary"
       href="${pageContext.request.contextPath}/register.jsp">
        Create Account
    </a>

    <footer>
        Campus Connect | Student Portal
    </footer>
</div>

</body>
</html>
