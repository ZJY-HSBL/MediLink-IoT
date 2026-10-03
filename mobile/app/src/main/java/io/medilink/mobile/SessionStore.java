package io.medilink.mobile;

import android.content.Context;
import android.content.SharedPreferences;

public final class SessionStore {
    private static final String PREFS = "medilink_session";
    private static final String TOKEN = "token";

    private final SharedPreferences prefs;

    public SessionStore(Context context) {
        prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public String token() {
        return prefs.getString(TOKEN, "");
    }

    public boolean isLoggedIn() {
        return !token().isEmpty();
    }

    public void saveToken(String token) {
        prefs.edit().putString(TOKEN, token).apply();
    }

    public void clear() {
        prefs.edit().clear().apply();
    }
}
