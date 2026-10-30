
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("studentId") == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");
            return;
        }

        String question = null;
        List<String> options = new ArrayList<>();
        boolean alreadyVoted = false;

        String pollSql =
                "SELECT POLL_ID, QUESTION FROM CC_POLLS "
              + "WHERE IS_ACTIVE = 'Y' "
              + "ORDER BY POLL_ID FETCH FIRST 1 ROW ONLY";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement pollPs = con.prepareStatement(pollSql);
             ResultSet pollRs = pollPs.executeQuery()) {

            if (!pollRs.next()) {
                response.sendError(404, "No active poll is available.");
                return;
            }

            long pollId = pollRs.getLong("POLL_ID");
            question = pollRs.getString("QUESTION");

            String optionsSql =
                    "SELECT OPTION_TEXT FROM CC_POLL_OPTIONS "
                  + "WHERE POLL_ID = ? ORDER BY OPTION_ID";

            try (PreparedStatement optionPs =
                         con.prepareStatement(optionsSql)) {

                optionPs.setLong(1, pollId);

                try (ResultSet optionRs = optionPs.executeQuery()) {
                    while (optionRs.next()) {
                        options.add(optionRs.getString("OPTION_TEXT"));
                    }
                }
            }

            String voteSql =
                    "SELECT COUNT(*) FROM CC_VOTES "
                  + "WHERE STUDENT_ID = ?";

            try (PreparedStatement votePs =
                         con.prepareStatement(voteSql)) {

                votePs.setLong(1, ((Number)
                        session.getAttribute("studentId")).longValue());

                try (ResultSet voteRs = votePs.executeQuery()) {
                    if (voteRs.next()) {
                        alreadyVoted = voteRs.getInt(1) > 0;
                    }
                }
            }

            request.setAttribute("question", question);
            request.setAttribute("options", options);
            request.setAttribute("alreadyVoted", alreadyVoted);
            request.getRequestDispatcher("/vote.jsp")
                   .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Unable to load poll.", e);
        }
    }

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

        String choice = request.getParameter("choice");

        if (choice == null || choice.isBlank()) {
            response.sendRedirect(
                    request.getContextPath() + "/vote?error=1");
            return;
        }

        long studentId = ((Number)
                session.getAttribute("studentId")).longValue();

        try (Connection con = DBUtil.getConnection()) {

            con.setAutoCommit(false);

            try {
                // Find the active poll.
                long pollId;

                String pollSql =
                        "SELECT POLL_ID FROM CC_POLLS "
                      + "WHERE IS_ACTIVE = 'Y' "
                      + "ORDER BY POLL_ID FETCH FIRST 1 ROW ONLY";

                try (PreparedStatement ps =
                             con.prepareStatement(pollSql);
                     ResultSet rs = ps.executeQuery()) {

                    if (!rs.next()) {
                        con.rollback();
                        response.sendError(404, "No active poll available.");
                        return;
                    }

                    pollId = rs.getLong("POLL_ID");
                }

                // Verify that the submitted option belongs to this poll.
                String optionSql =
                        "SELECT COUNT(*) FROM CC_POLL_OPTIONS "
                      + "WHERE POLL_ID = ? AND OPTION_TEXT = ?";

                try (PreparedStatement ps =
                             con.prepareStatement(optionSql)) {

                    ps.setLong(1, pollId);
                    ps.setString(2, choice);

                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();

                        if (rs.getInt(1) == 0) {
                            con.rollback();
                            response.sendRedirect(
                                    request.getContextPath()
                                    + "/vote?error=1");
                            return;
                        }
                    }
                }

                // Save the vote.
                String insertSql =
                        "INSERT INTO CC_VOTES (STUDENT_ID, CHOICE) "
                      + "VALUES (?, ?)";

                try (PreparedStatement ps =
                             con.prepareStatement(insertSql)) {

                    ps.setLong(1, studentId);
                    ps.setString(2, choice);
                    ps.executeUpdate();
                }

                con.commit();

                response.sendRedirect(
                        request.getContextPath() + "/vote?success=1");

            } catch (SQLException e) {
                con.rollback();

                // Oracle unique-constraint violation.
                if (e.getErrorCode() == 1) {
                    response.sendRedirect(
                            request.getContextPath() + "/vote?duplicate=1");
                } else {
                    throw e;
                }
            } finally {
                con.setAutoCommit(true);
            }

        } catch (SQLException e) {
            throw new ServletException("Unable to save vote.", e);
        }
    }
}
