package io.medilink.web;

import io.medilink.dao.UserDao;
import io.medilink.util.Json;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/api/auth/register")
public class RegisterServlet extends HttpServlet {
    private final UserDao users = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RegisterRequest body = Json.read(req, RegisterRequest.class);
        if (body == null || body.username == null || body.password == null) {
            Json.write(resp, 400, Map.of("error", "username and password are required"));
            return;
        }

        String username = body.username.trim();
        if (username.length() < 3 || body.password.length() < 6) {
            Json.write(resp, 400, Map.of("error", "username >= 3 chars and password >= 6 chars"));
            return;
        }

        try {
            long id = users.create(
                    username,
                    BCrypt.hashpw(body.password, BCrypt.gensalt(12)),
                    body.displayName == null || body.displayName.isBlank() ? username : body.displayName.trim()
            );
            String token = users.issueToken(id);
            Json.write(resp, 201, Map.of("userId", id, "token", token));
        } catch (SQLIntegrityConstraintViolationException e) {
            Json.write(resp, 409, Map.of("error", "username already exists"));
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        }
    }

    private static class RegisterRequest {
        String username;
        String password;
        String displayName;
    }
}
