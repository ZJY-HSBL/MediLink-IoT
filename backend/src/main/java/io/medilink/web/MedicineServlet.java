package io.medilink.web;

import io.medilink.dao.MedicineDao;
import io.medilink.model.Medicine;
import io.medilink.service.AuthService;
import io.medilink.util.Json;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/api/medicines")
public class MedicineServlet extends HttpServlet {
    private final AuthService auth = new AuthService();
    private final MedicineDao medicines = new MedicineDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var user = auth.currentUser(req);
            if (user.isEmpty()) {
                Json.write(resp, 401, Map.of("error", "unauthorized"));
                return;
            }
            Json.write(resp, 200, medicines.list(user.get().id()));
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            var user = auth.currentUser(req);
            if (user.isEmpty()) {
                Json.write(resp, 401, Map.of("error", "unauthorized"));
                return;
            }

            Medicine m = Json.read(req, Medicine.class);
            if (m == null || m.name == null || m.name.isBlank() ||
                    m.scheduleTime == null || !m.scheduleTime.matches("^([01]\\d|2[0-3]):[0-5]\\d$")) {
                Json.write(resp, 400, Map.of("error", "invalid medicine payload"));
                return;
            }

            long id = medicines.add(user.get().id(), m);
            Json.write(resp, 201, Map.of("id", id));
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        }
    }
}
