package io.medilink.web;

import io.medilink.service.AuthService;
import io.medilink.util.Db;
import io.medilink.util.Json;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.Map;

@WebServlet("/api/feedback")
public class FeedbackServlet extends HttpServlet {
    private final AuthService auth = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var user = auth.currentUser(req);
            if (user.isEmpty()) {
                Json.write(resp, 401, Map.of("error", "unauthorized"));
                return;
            }

            FeedbackRequest body = Json.read(req, FeedbackRequest.class);
            if (body == null || body.message == null || body.message.isBlank()) {
                Json.write(resp, 400, Map.of("error", "message is required"));
                return;
            }

            try (Connection c = Db.getConnection();
                 PreparedStatement ps = c.prepareStatement(
                         "INSERT INTO feedback(user_id,message) VALUES(?,?)")) {
                ps.setLong(1, user.get().id());
                ps.setString(2, body.message.trim());
                ps.executeUpdate();
            }
            Json.write(resp, 201, Map.of("ok", true));
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        }
    }

    private static class FeedbackRequest {
        String message;
    }
}
