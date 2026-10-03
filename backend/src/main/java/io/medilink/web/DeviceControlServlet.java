package io.medilink.web;

import io.medilink.service.AuthService;
import io.medilink.service.OneNetService;
import io.medilink.util.Json;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/api/device/control")
public class DeviceControlServlet extends HttpServlet {
    private final AuthService auth = new AuthService();
    private final OneNetService oneNet = new OneNetService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (auth.currentUser(req).isEmpty()) {
                Json.write(resp, 401, Map.of("error", "unauthorized"));
                return;
            }

            ControlRequest body = Json.read(req, ControlRequest.class);
            if (body == null || body.command == null) {
                Json.write(resp, 400, Map.of("error", "command is required"));
                return;
            }

            OneNetService.DeviceCommand command;
            try {
                command = OneNetService.DeviceCommand.valueOf(body.command.trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                Json.write(resp, 400, Map.of("error", "unsupported command"));
                return;
            }

            oneNet.send(command);
            Json.write(resp, 200, Map.of("ok", true, "command", command.name()));
        } catch (SQLException e) {
            Json.write(resp, 500, Map.of("error", "database error"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Json.write(resp, 503, Map.of("error", "device service interrupted"));
        } catch (Exception e) {
            Json.write(resp, 502, Map.of("error", "unable to send device command"));
        }
    }

    private static class ControlRequest {
        String command;
    }
}
