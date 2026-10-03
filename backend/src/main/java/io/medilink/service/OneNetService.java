package io.medilink.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.medilink.config.AppConfig;
import io.medilink.model.DeviceSnapshot;
import io.medilink.util.Json;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class OneNetService {
    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(8))
            .build();

    public DeviceSnapshot fetchSnapshot() throws IOException, InterruptedException {
        String deviceId = AppConfig.required("onenet.deviceId");
        String url = AppConfig.required("onenet.snapshotUrl")
                .replace("{deviceId}", deviceId);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("api-key", AppConfig.required("onenet.apiKey"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new IOException("OneNET snapshot request failed: HTTP " + response.statusCode());
        }

        JsonObject root = Json.GSON.fromJson(response.body(), JsonObject.class);
        Map<String, Double> values = parseDatastreams(root);
        return new DeviceSnapshot(
                values.getOrDefault("temperature", 0.0),
                values.getOrDefault("humidity", 0.0),
                values.getOrDefault("take_medicine", 0.0).intValue()
        );
    }

    public void send(DeviceCommand command) throws IOException, InterruptedException {
        String deviceId = AppConfig.required("onenet.deviceId");
        String url = AppConfig.required("onenet.commandUrl")
                .replace("{deviceId}", deviceId);

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("api-key", AppConfig.required("onenet.apiKey"))
                .header("Content-Type", "text/plain; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(command.payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() / 100 != 2) {
            throw new IOException("OneNET command request failed: HTTP " + response.statusCode());
        }
    }

    private Map<String, Double> parseDatastreams(JsonObject root) throws IOException {
        Map<String, Double> values = new HashMap<>();
        if (!root.has("data")) throw new IOException("OneNET response has no data field");
        JsonObject data = root.getAsJsonObject("data");
        JsonArray streams = data.getAsJsonArray("datastreams");
        if (streams == null) throw new IOException("OneNET response has no datastreams");

        for (JsonElement e : streams) {
            JsonObject stream = e.getAsJsonObject();
            String id = stream.get("id").getAsString();
            JsonArray points = stream.getAsJsonArray("datapoints");
            if (points == null || points.size() == 0) continue;
            JsonElement value = points.get(0).getAsJsonObject().get("value");
            if (value != null && value.isJsonPrimitive()) {
                try {
                    values.put(id, value.getAsDouble());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return values;
    }

    public enum DeviceCommand {
        BEEP_ON("BEEPON"),
        BEEP_OFF("BEEPOFF"),
        FAN_ON("FANON"),
        FAN_OFF("FANOFF");

        public final String payload;
        DeviceCommand(String payload) {
            this.payload = payload;
        }
    }
}
