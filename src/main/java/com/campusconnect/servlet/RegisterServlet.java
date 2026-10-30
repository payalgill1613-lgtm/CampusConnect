
package com.campusconnect.servlet;

import com.campusconnect.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (fullName == null || email == null || password == null
                || fullName.isBlank() || email.isBlank()
                || password.isBlank()) {
            response.sendError(400, "All fields are required.");
            return;
        }

        fullName = fullName.trim();
        email = email.trim().toLowerCase();

        if (password.length() < 8) {
            response.sendError(400,
                    "Password must contain at least 8 characters.");
            return;
        }

        String sql = "INSERT INTO CC_STUDENTS "
                   + "(FULL_NAME, EMAIL, PASSWORD_HASH) "
                   + "VALUES (?, ?, ?)";

        String passwordHash = BCrypt.hashpw(
                password, BCrypt.gensalt(12));

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fullName);
            ps.setString(2, email);
            ps.setString(3, passwordHash);

            ps.executeUpdate();

            response.sendRedirect(
                    request.getContextPath() + "/login.jsp?registered=1");

        } catch (SQLException e) {
            if (e.getErrorCode() == 1) {
                response.sendError(409,
                        "This email is already registered.");
            } else {
                throw new ServletException(
                        "Unable to register student.", e);
            }
        }
    }
}
