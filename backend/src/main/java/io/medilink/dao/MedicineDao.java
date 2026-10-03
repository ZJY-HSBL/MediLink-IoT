package io.medilink.dao;

import io.medilink.model.Medicine;
import io.medilink.util.Db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineDao {
    public List<Medicine> list(long userId) throws SQLException {
        String sql = """
                SELECT id, user_id, name, dosage,
                       DATE_FORMAT(schedule_time, '%H:%i') AS schedule_time,
                       notes, enabled
                FROM medicines
                WHERE user_id = ?
                ORDER BY schedule_time, id
                """;
        List<Medicine> result = new ArrayList<>();
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medicine m = new Medicine();
                    m.id = rs.getLong("id");
                    m.userId = rs.getLong("user_id");
                    m.name = rs.getString("name");
                    m.dosage = rs.getString("dosage");
                    m.scheduleTime = rs.getString("schedule_time");
                    m.notes = rs.getString("notes");
                    m.enabled = rs.getBoolean("enabled");
                    result.add(m);
                }
            }
        }
        return result;
    }

    public long add(long userId, Medicine medicine) throws SQLException {
        String sql = "INSERT INTO medicines(user_id,name,dosage,schedule_time,notes,enabled) VALUES(?,?,?,?,?,1)";
        try (Connection c = Db.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, userId);
            ps.setString(2, medicine.name);
            ps.setString(3, medicine.dosage);
            ps.setString(4, medicine.scheduleTime);
            ps.setString(5, medicine.notes);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) throw new SQLException("No generated medicine id");
                return rs.getLong(1);
            }
        }
    }
}
