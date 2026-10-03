package io.medilink.dao;

import io.medilink.model.AuthUser;
import io.medilink.util.Db;

import java.sql.*;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

public class UserDao {
    public Optional<UserRow> findByUsername(String username) throws SQLException {
        String sql = "SELECT id, username, password_hash, display_name FROM users WHERE username = ?";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new UserRow(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password_hash"),
                        rs.getString("display_name")
                ));
            }
        }
    }

    public long create(String username, String passwordHash, String displayName) throws SQLException {
        String sql = "INSERT INTO users(username, password_hash, display_name) VALUES(?,?,?)";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, passwordHash);
            ps.setString(3, displayName);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (!keys.next()) throw new SQLException("No generated user id");
                return keys.getLong(1);
            }
        }
    }

    public String issueToken(long userId) throws SQLException {
        String token = UUID.randomUUID().toString().replace("-", "");
        String sql = "INSERT INTO auth_tokens(token, user_id, expires_at) VALUES(?,?,DATE_ADD(NOW(), INTERVAL 30 DAY))";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, token);
            ps.setLong(2, userId);
            ps.executeUpdate();
        }
        return token;
    }

    public Optional<AuthUser> findByToken(String token) throws SQLException {
        String sql = """
                SELECT u.id, u.username, u.display_name
                FROM auth_tokens t
                JOIN users u ON u.id = t.user_id
                WHERE t.token = ? AND t.expires_at > NOW()
                """;
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new AuthUser(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("display_name")
                ));
            }
        }
    }

    public record UserRow(long id, String username, String passwordHash, String displayName) {}
}
