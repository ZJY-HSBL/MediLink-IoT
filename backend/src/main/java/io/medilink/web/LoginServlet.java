package io.medilink.web;

import io.medilink.dao.UserDao;
import io.medilink.util.Json;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/api/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserDao users = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginRequest body = Json.read(req, LoginRequest.class);
        if (body == null || body.username == null || body.password == null) {
            Json.write(resp, 400, Map.of("error", "username and password are required"));
            return;
        }

        try {
            var user = users.findByUsername(body.username.trim());
            if (user.isEmpty() || !BCrypt.checkpw(body.password, user.get().passwordHash())) {
                Json.write(resp, 401, Map.of("error", "invalid username or password"));
                return;
            }
            String token = users.issueToken(user.get().id());
            Json.write(resp, 200, Map.of(
                    "token", token,
                    "userId", user.get().id(),
                    "username", user.get().username(),
                    "displayName", user.get().displayName()
            ));
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        }
    }

    private static class LoginRequest {
        String username;
        String password;
    }
}
