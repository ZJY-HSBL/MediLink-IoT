package io.medilink.mobile;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.*;

public class MedicinesActivity extends AppCompatActivity {
    private ApiClient api;
    private LinearLayout list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);

        api = new ApiClient(new SessionStore(this));
        list = findViewById(R.id.medicineList);

        EditText name = findViewById(R.id.name);
        EditText dosage = findViewById(R.id.dosage);
        EditText time = findViewById(R.id.scheduleTime);
        EditText notes = findViewById(R.id.notes);

        findViewById(R.id.addButton).setOnClickListener(v -> {
            JsonObject body = new JsonObject();
            body.addProperty("name", name.getText().toString().trim());
            body.addProperty("dosage", dosage.getText().toString().trim());
            body.addProperty("scheduleTime", time.getText().toString().trim());
            body.addProperty("notes", notes.getText().toString().trim());
            api.post("api/medicines", body, true, new ApiClient.Callback() {
                @Override
                public void onSuccess(JsonElement json) {
                    Toast.makeText(MedicinesActivity.this, "已添加", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    dosage.setText("");
                    notes.setText("");
                    load();
                }

                @Override
                public void onError(int statusCode, String message) {
                    Toast.makeText(MedicinesActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        load();
    }

    private void load() {
        api.get("api/medicines", true, new ApiClient.Callback() {
            @Override
            public void onSuccess(JsonElement json) {
                list.removeAllViews();
                for (JsonElement item : json.getAsJsonArray()) {
                    JsonObject o = item.getAsJsonObject();
                    TextView row = new TextView(MedicinesActivity.this);
                    row.setPadding(0, 20, 0, 20);
                    row.setText(o.get("name").getAsString() + "  ·  " +
                            text(o, "dosage") + "  ·  " + text(o, "scheduleTime"));
                    row.setTextSize(16);
                    list.addView(row, new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }

            @Override
            public void onError(int statusCode, String message) {
                Toast.makeText(MedicinesActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String text(JsonObject o, String key) {
        return o.has(key) && !o.get(key).isJsonNull() ? o.get(key).getAsString() : "";
    }
}
