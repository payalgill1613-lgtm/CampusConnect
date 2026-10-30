
package com.campusconnect.servlet;

import com.campusconnect.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/feedback")
public class FeedbackServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("studentId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        String subject = request.getParameter("subject");
        String message = request.getParameter("message");

        if (subject == null || message == null
                || subject.isBlank() || message.isBlank()) {
            response.sendRedirect(
                    request.getContextPath() + "/feedback.jsp?error=1");
            return;
        }

        long studentId = ((Number) session.getAttribute("studentId"))
                .longValue();

        String sql = "INSERT INTO CC_FEEDBACK "
                   + "(STUDENT_ID, SUBJECT, MESSAGE) VALUES (?, ?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, studentId);
            ps.setString(2, subject.trim());
            ps.setString(3, message.trim());

            ps.executeUpdate();

            response.sendRedirect(
                    request.getContextPath()
                    + "/feedback.jsp?success=1");

        } catch (SQLException e) {
            throw new ServletException("Unable to save feedback.", e);
        }
    }
}
