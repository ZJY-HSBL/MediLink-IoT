package io.medilink.mobile;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

public class FeedbackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        EditText message = findViewById(R.id.message);
        Button submit = findViewById(R.id.submitButton);
        ApiClient api = new ApiClient(new SessionStore(this));

        submit.setOnClickListener(v -> {
            JsonObject body = new JsonObject();
            body.addProperty("message", message.getText().toString().trim());
            submit.setEnabled(false);
            api.post("api/feedback", body, true, new ApiClient.Callback() {
                @Override
                public void onSuccess(com.google.gson.JsonElement json) {
                    submit.setEnabled(true);
                    message.setText("");
                    Toast.makeText(FeedbackActivity.this, "反馈已提交", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(int statusCode, String error) {
                    submit.setEnabled(true);
                    Toast.makeText(FeedbackActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
