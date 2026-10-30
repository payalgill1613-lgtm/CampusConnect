
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%
    if (session.getAttribute("studentId") == null) {
        response.sendRedirect(
            request.getContextPath() + "/login.jsp");
        return;
    }

    String question = (String) request.getAttribute("question");
    List<String> options =
        (List<String>) request.getAttribute("options");

    Boolean voted = (Boolean) request.getAttribute("alreadyVoted");
    boolean alreadyVoted = Boolean.TRUE.equals(voted);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Campus Connect - Voting</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f1f5f9;
            padding: 25px 15px;
        }
        .card {
            max-width: 500px;
            margin: 30px auto;
            padding: 28px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 4px 15px #0001;
        }
        h2 { color: #1d4ed8; }
        .option {
            display: block;
            padding: 12px;
            margin: 10px 0;
            background: #f8fafc;
            border: 1px solid #e2e8f0;
            border-radius: 7px;
        }
        button {
            width: 100%;
            padding: 12px;
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
    <h2>Campus Connect Poll</h2>

    <% if ("1".equals(request.getParameter("success"))) { %>
        <p class="success">Your vote was submitted successfully!</p>
    <% } %>

    <% if ("1".equals(request.getParameter("duplicate"))) { %>
        <p class="error">You have already voted.</p>
    <% } %>

    <% if ("1".equals(request.getParameter("error"))) { %>
        <p class="error">Please select a valid option.</p>
    <% } %>

    <% if (alreadyVoted) { %>
        <p class="success">You have already submitted your vote.</p>
    <% } else if (question != null && options != null) { %>

        <h3><%= question %></h3>

        <form action="${pageContext.request.contextPath}/vote"
              method="post">

            <% for (String option : options) { %>
                <label class="option">
                    <input type="radio" name="choice"
                           value="<%= option %>" required>
                    <%= option %>
                </label>
            <% } %>

            <button type="submit">Submit Vote</button>
        </form>

    <% } else { %>
        <p>No poll is currently available.</p>
    <% } %>

    <p><a href="dashboard.jsp">Back to Dashboard</a></p>
</div>
</body>
</html>
