package io.medilink.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

public class LoginActivity extends AppCompatActivity {
    private SessionStore session;
    private ApiClient api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionStore(this);
        api = new ApiClient(session);

        if (session.isLoggedIn()) {
            openMain();
            return;
        }

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button login = findViewById(R.id.loginButton);
        Button register = findViewById(R.id.registerButton);

        login.setOnClickListener(v -> {
            String u = username.getText().toString().trim();
            String p = password.getText().toString();
            if (u.isEmpty() || p.isEmpty()) {
                toast("请输入用户名和密码");
                return;
            }

            login.setEnabled(false);
            JsonObject body = new JsonObject();
            body.addProperty("username", u);
            body.addProperty("password", p);

            api.post("api/auth/login", body, false, new ApiClient.Callback() {
                @Override
                public void onSuccess(com.google.gson.JsonElement json) {
                    login.setEnabled(true);
                    String token = json.getAsJsonObject().get("token").getAsString();
                    session.saveToken(token);
                    openMain();
                }

                @Override
                public void onError(int statusCode, String message) {
                    login.setEnabled(true);
                    toast(message);
                }
            });
        });

        register.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void openMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
