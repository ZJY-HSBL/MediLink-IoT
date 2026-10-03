package io.medilink.mobile;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.*;

public class HistoryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        LinearLayout list = findViewById(R.id.historyList);
        ApiClient api = new ApiClient(new SessionStore(this));

        api.get("api/history", true, new ApiClient.Callback() {
            @Override
            public void onSuccess(JsonElement json) {
                list.removeAllViews();
                for (JsonElement item : json.getAsJsonArray()) {
                    JsonObject o = item.getAsJsonObject();
                    TextView row = new TextView(HistoryActivity.this);
                    row.setPadding(0, 18, 0, 18);
                    String name = o.has("medicineName") && !o.get("medicineName").isJsonNull()
                            ? o.get("medicineName").getAsString() : "Medication";
                    row.setText(name + "\n" + o.get("scheduledAt").getAsString() +
                            " · " + o.get("status").getAsString());
                    list.addView(row, new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
                }
            }

            @Override
            public void onError(int statusCode, String message) {
                Toast.makeText(HistoryActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
