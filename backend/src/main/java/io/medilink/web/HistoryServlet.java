package io.medilink.web;

import io.medilink.service.AuthService;
import io.medilink.util.Db;
import io.medilink.util.Json;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/api/history")
public class HistoryServlet extends HttpServlet {
    private final AuthService auth = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var user = auth.currentUser(req);
            if (user.isEmpty()) {
                Json.write(resp, 401, Map.of("error", "unauthorized"));
                return;
            }

            String sql = """
                    SELECT r.id, r.medicine_id, m.name AS medicine_name,
                           r.scheduled_at, r.taken_at, r.status
                    FROM medication_records r
                    LEFT JOIN medicines m ON m.id = r.medicine_id
                    WHERE r.user_id = ?
                    ORDER BY r.scheduled_at DESC
                    LIMIT 100
                    """;

            List<Map<String, Object>> rows = new ArrayList<>();
            try (Connection c = Db.getConnection();
                 PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setLong(1, user.get().id());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> row = new LinkedHashMap<>();
                        row.put("id", rs.getLong("id"));
                        row.put("medicineId", rs.getObject("medicine_id"));
                        row.put("medicineName", rs.getString("medicine_name"));
                        row.put("scheduledAt", String.valueOf(rs.getTimestamp("scheduled_at")));
                        Timestamp takenAt = rs.getTimestamp("taken_at");
                        row.put("takenAt", takenAt == null ? null : takenAt.toString());
                        row.put("status", rs.getString("status"));
                        rows.add(row);
                    }
                }
            }
            Json.write(resp, 200, rows);
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        }
    }
}
