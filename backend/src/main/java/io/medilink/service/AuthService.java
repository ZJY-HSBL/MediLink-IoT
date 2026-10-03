package io.medilink.service;

import io.medilink.dao.UserDao;
import io.medilink.model.AuthUser;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Optional;

public class AuthService {
    private final UserDao users = new UserDao();

    public Optional<AuthUser> currentUser(HttpServletRequest request) throws SQLException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return Optional.empty();
        }
        String token = header.substring("Bearer ".length()).trim();
        if (token.isEmpty()) return Optional.empty();
        return users.findByToken(token);
    }
}
