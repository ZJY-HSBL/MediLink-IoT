package io.medilink.web;

import io.medilink.service.AuthService;
import io.medilink.service.OneNetService;
import io.medilink.util.Json;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/api/device/status")
public class DeviceStatusServlet extends HttpServlet {
    private final AuthService auth = new AuthService();
    private final OneNetService oneNet = new OneNetService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (auth.currentUser(req).isEmpty()) {
                Json.write(resp, 401, Map.of("error", "unauthorized"));
                return;
            }
            Json.write(resp, 200, oneNet.fetchSnapshot());
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Json.write(resp, 503, Map.of("error", "device service interrupted"));
        } catch (Exception e) {
            Json.write(resp, 502, Map.of("error", "unable to read device telemetry"));
        }
    }
}
