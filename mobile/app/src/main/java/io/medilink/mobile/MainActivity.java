package io.medilink.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ApiClient api;
    private SessionStore session;
    private TextView temperature;
    private TextView humidity;
    private TextView medicineState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionStore(this);
        api = new ApiClient(session);

        temperature = findViewById(R.id.temperatureText);
        humidity = findViewById(R.id.humidityText);
        medicineState = findViewById(R.id.medicineStateText);

        findViewById(R.id.refreshButton).setOnClickListener(v -> refreshStatus());
        findViewById(R.id.beepOnButton).setOnClickListener(v -> sendCommand("BEEP_ON"));
        findViewById(R.id.beepOffButton).setOnClickListener(v -> sendCommand("BEEP_OFF"));
        findViewById(R.id.fanOnButton).setOnClickListener(v -> sendCommand("FAN_ON"));
        findViewById(R.id.fanOffButton).setOnClickListener(v -> sendCommand("FAN_OFF"));

        findViewById(R.id.medicinesButton).setOnClickListener(v ->
                startActivity(new Intent(this, MedicinesActivity.class)));
        findViewById(R.id.historyButton).setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class)));
        findViewById(R.id.feedbackButton).setOnClickListener(v ->
                startActivity(new Intent(this, FeedbackActivity.class)));

        findViewById(R.id.logoutButton).setOnClickListener(v -> {
            session.clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        refreshStatus();
    }

    private void refreshStatus() {
        api.get("api/device/status", true, new ApiClient.Callback() {
            @Override
            public void onSuccess(com.google.gson.JsonElement json) {
                JsonObject o = json.getAsJsonObject();
                temperature.setText(String.format(Locale.getDefault(), "温度：%.1f °C", o.get("temperature").getAsDouble()));
                humidity.setText(String.format(Locale.getDefault(), "湿度：%.1f %%", o.get("humidity").getAsDouble()));
                int state = o.get("takeMedicine").getAsInt();
                medicineState.setText(state == 1 ? "服药状态：已触发/已服药" : "服药状态：待机");
            }

            @Override
            public void onError(int statusCode, String message) {
                handleError(statusCode, message);
            }
        });
    }

    private void sendCommand(String command) {
        JsonObject body = new JsonObject();
        body.addProperty("command", command);
        api.post("api/device/control", body, true, new ApiClient.Callback() {
            @Override
            public void onSuccess(com.google.gson.JsonElement json) {
                Toast.makeText(MainActivity.this, "指令已发送", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int statusCode, String message) {
                handleError(statusCode, message);
            }
        });
    }

    private void handleError(int statusCode, String message) {
        if (statusCode == 401) {
            session.clear();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
