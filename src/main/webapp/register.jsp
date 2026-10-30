
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Connect - Register</title>
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
        a { color: #2563eb; }
        .center { text-align: center; }
    </style>
</head>
<body>
<div class="card">
    <h2>Campus Connect</h2>
    <h3>Create Student Account</h3>

    <form action="${pageContext.request.contextPath}/register"
          method="post">

        <label>Full Name</label>
        <input type="text" name="fullName" required>

        <label>Email</label>
        <input type="email" name="email" required>

        <label>Password (minimum 8 characters)</label>
        <input type="password" name="password"
               minlength="8" required>

        <button type="submit">Register</button>
    </form>

    <p class="center">
        Already registered?
        <a href="login.jsp">Login here</a>
    </p>
</div>
</body>
</html>
