package io.medilink.mobile;

import android.content.Intent;
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
            String u = username.getText().toString().trim();
            String p = password.getText().toString();

            if (u.length() < 3 || p.length() < 6) {
                Toast.makeText(this, "用户名至少 3 位，密码至少 6 位", Toast.LENGTH_SHORT).show();
                return;
            }

            JsonObject body = new JsonObject();
            body.addProperty("username", u);
            body.addProperty("displayName", displayName.getText().toString().trim());
            body.addProperty("password", p);

            submit.setEnabled(false);
            api.post("api/auth/register", body, false, new ApiClient.Callback() {
                @Override
                public void onSuccess(com.google.gson.JsonElement json) {
                    submit.setEnabled(true);
                    session.saveToken(json.getAsJsonObject().get("token").getAsString());
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
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
