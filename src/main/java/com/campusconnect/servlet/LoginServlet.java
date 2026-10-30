
package com.campusconnect.servlet;

import com.campusconnect.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || password == null
                || email.isBlank() || password.isBlank()) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp?error=1");
            return;
        }

        String sql = "SELECT STUDENT_ID, FULL_NAME, PASSWORD_HASH "
                   + "FROM CC_STUDENTS WHERE EMAIL = ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email.trim().toLowerCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHash = rs.getString("PASSWORD_HASH");

                    if (storedHash != null
                            && BCrypt.checkpw(password, storedHash)) {

                        HttpSession session = request.getSession(true);
                        session.setAttribute(
                                "studentId", rs.getLong("STUDENT_ID"));
                        session.setAttribute(
                                "studentName", rs.getString("FULL_NAME"));

                        response.sendRedirect(
                                request.getContextPath() + "/dashboard.jsp");
                        return;
                    }
                }
            }

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp?error=1");

        } catch (SQLException e) {
            throw new ServletException("Unable to log in.", e);
        }
    }
}
