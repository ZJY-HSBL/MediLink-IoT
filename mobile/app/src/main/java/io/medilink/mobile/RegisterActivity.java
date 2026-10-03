package io.medilink.mobile;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SessionStore session = new SessionStore(this);
        ApiClient api = new ApiClient(session);

        EditText username = findViewById(R.id.username);
        EditText displayName = findViewById(R.id.displayName);
        EditText password = findViewById(R.id.password);
        Button submit = findViewById(R.id.submitButton);

        submit.setOnClickListener(v -> {
            JsonObject body = new JsonObject();
            body.addProperty("username", username.getText().toString().trim());
            body.addProperty("displayName", displayName.getText().toString().trim());
            body.addProperty("password", password.getText().toString());

            submit.setEnabled(false);
            api.post("api/auth/register", body, false, new ApiClient.Callback() {
                @Override
                public void onSuccess(com.google.gson.JsonElement json) {
                    submit.setEnabled(true);
                    session.saveToken(json.getAsJsonObject().get("token").getAsString());
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                }

                @Override
                public void onError(int statusCode, String message) {
                    submit.setEnabled(true);
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
