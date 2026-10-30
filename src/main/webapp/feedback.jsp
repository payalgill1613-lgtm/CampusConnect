
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    if (session.getAttribute("studentId") == null) {
        response.sendRedirect(
            request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Connect - Feedback</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f5f9;
            margin: 0;
            padding: 30px 15px;
        }
        .card {
            max-width: 500px;
            margin: 30px auto;
            background: white;
            padding: 28px;
            border-radius: 12px;
            box-shadow: 0 4px 15px #0001;
        }
        h2 { color: #1d4ed8; }
        label { display: block; margin-top: 16px; }
        input, textarea, button {
            width: 100%;
            padding: 11px;
            margin-top: 7px;
            box-sizing: border-box;
            font: inherit;
        }
        textarea { min-height: 130px; resize: vertical; }
        button {
            background: #2563eb;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .success { color: #15803d; }
        .error { color: #b91c1c; }
        a { color: #2563eb; }
    </style>
</head>
<body>

<div class="card">
    <h2>Share Your Feedback</h2>
    <p>Help us improve campus life.</p>

    <% if ("1".equals(request.getParameter("success"))) { %>
        <p class="success">Feedback submitted successfully!</p>
    <% } %>

    <% if ("1".equals(request.getParameter("error"))) { %>
        <p class="error">Please enter both a subject and a message.</p>
    <% } %>

    <form action="${pageContext.request.contextPath}/feedback"
          method="post">

        <label for="subject">Subject</label>
        <input id="subject" type="text" name="subject"
               maxlength="200" required>

        <label for="message">Your Feedback</label>
        <textarea id="message" name="message"
                  maxlength="1000" required></textarea>

        <button type="submit">Submit Feedback</button>
    </form>

    <p><a href="dashboard.jsp">Back to Dashboard</a></p>
</div>

</body>
</html>
