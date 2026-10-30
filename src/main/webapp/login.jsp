
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Connect - Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f5f9;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        .card {
            background: white;
            padding: 30px;
            border-radius: 12px;
            width: 320px;
            box-shadow: 0 4px 15px #0002;
        }
        h2 { text-align: center; color: #1d4ed8; }
        input, button {
            width: 100%;
            padding: 11px;
            margin: 8px 0;
            box-sizing: border-box;
        }
        button {
            background: #2563eb;
            color: white;
            border: 0;
            border-radius: 6px;
            cursor: pointer;
        }
        .message { color: #b91c1c; text-align: center; }
        .success { color: #15803d; text-align: center; }
        .center { text-align: center; }
    </style>
</head>
<body>
<div class="card">
    <h2>Campus Connect</h2>
    <h3>Student Login</h3>

    <% if ("1".equals(request.getParameter("error"))) { %>
        <p class="message">Invalid email or password.</p>
    <% } %>

    <% if ("1".equals(request.getParameter("registered"))) { %>
        <p class="success">Registration successful! Please log in.</p>
    <% } %>

<form action="${pageContext.request.contextPath}/login"
      method="post">

    <label>Email</label>
    <input type="email" name="email" required>

    <label>Password</label>
    <input type="password" name="password" required>

    <button type="submit">Login</button>

</form>


    <p class="center">
        New student?
        <a href="register.jsp">Register here</a>
    </p>
</div>
</body>
</html>
