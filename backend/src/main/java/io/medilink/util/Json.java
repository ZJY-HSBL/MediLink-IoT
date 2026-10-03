package io.medilink.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class Json {
    public static final Gson GSON = new GsonBuilder().serializeNulls().create();

    private Json() {}

    public static <T> T read(HttpServletRequest request, Class<T> type) throws IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        return GSON.fromJson(request.getReader(), type);
    }

    public static void write(HttpServletResponse response, int status, Object body) throws IOException {
        response.setStatus(status);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/json");
        GSON.toJson(body, response.getWriter());
    }
}
