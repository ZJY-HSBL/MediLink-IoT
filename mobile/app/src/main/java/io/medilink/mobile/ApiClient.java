package io.medilink.mobile;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.*;

public final class ApiClient {
    private static final MediaType JSON_MEDIA = MediaType.get("application/json; charset=utf-8");
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(8, TimeUnit.SECONDS)
            .readTimeout(12, TimeUnit.SECONDS)
            .writeTimeout(12, TimeUnit.SECONDS)
            .build();

    private final SessionStore session;

    public ApiClient(SessionStore session) {
        this.session = session;
    }

    public void get(String path, boolean authenticated, Callback callback) {
        Request.Builder builder = new Request.Builder().url(url(path)).get();
        authorize(builder, authenticated);
        execute(builder.build(), callback);
    }

    public void post(String path, JsonObject body, boolean authenticated, Callback callback) {
        RequestBody requestBody = RequestBody.create(body.toString(), JSON_MEDIA);
        Request.Builder builder = new Request.Builder().url(url(path)).post(requestBody);
        authorize(builder, authenticated);
        execute(builder.build(), callback);
    }

    private String url(String path) {
        String base = BuildConfig.API_BASE_URL;
        if (!base.endsWith("/")) base += "/";
        return base + (path.startsWith("/") ? path.substring(1) : path);
    }

    private void authorize(Request.Builder builder, boolean authenticated) {
        if (authenticated && session.isLoggedIn()) {
            builder.header("Authorization", "Bearer " + session.token());
        }
    }

    private void execute(Request request, Callback callback) {
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MAIN.post(() -> callback.onError(-1, e.getMessage() == null ? "network error" : e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) {
                try (ResponseBody body = response.body()) {
                    String raw = body == null ? "" : body.string();
                    if (!response.isSuccessful()) {
                        String message = raw;
                        try {
                            JsonObject error = JsonParser.parseString(raw).getAsJsonObject();
                            if (error.has("error")) message = error.get("error").getAsString();
                        } catch (Exception ignored) {}
                        String finalMessage = message.isBlank() ? "HTTP " + response.code() : message;
                        MAIN.post(() -> callback.onError(response.code(), finalMessage));
                        return;
                    }

                    JsonElement json = raw.isBlank() ? JsonNull.INSTANCE : JsonParser.parseString(raw);
                    MAIN.post(() -> callback.onSuccess(json));
                } catch (Exception e) {
                    MAIN.post(() -> callback.onError(-2, "invalid server response"));
                }
            }
        });
    }

    public interface Callback {
        void onSuccess(JsonElement json);
        void onError(int statusCode, String message);
    }
}
