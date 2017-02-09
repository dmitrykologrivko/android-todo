package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

class OAuthStore {

    private static final String PREF_FILE_NAME = "oauth_pref";

    private static final String PREF_ACCESS_TOKEN = "access_token";
    private static final String PREF_REFRESH_TOKEN = "refresh_token";
    private static final String PREF_SCOPE = "scope";
    private static final String PREF_EXPIRES_IN = "expires_in";
    private static final String PREF_TOKEN_TYPE = "token_type";

    private final SharedPreferences mPref;

    OAuthStore(Context context) {
        mPref = context.getSharedPreferences(encrypt(PREF_FILE_NAME), Context.MODE_PRIVATE);
    }

    String getAccessToken() {
        return decrypt(mPref.getString(encrypt(PREF_ACCESS_TOKEN), ""));
    }

    String getRefreshToken() {
        return decrypt(mPref.getString(encrypt(PREF_REFRESH_TOKEN), ""));
    }

    String getTokenType() {
        return decrypt(mPref.getString(encrypt(PREF_TOKEN_TYPE), ""));
    }

    String getScope() {
        return decrypt(mPref.getString(encrypt(PREF_TOKEN_TYPE), ""));
    }

    int getExpiresIn() {
        return mPref.getInt(encrypt(PREF_TOKEN_TYPE), 0);
    }

    void save(final OAuthResponse o) {
        mPref.edit()
                .putString(encrypt(PREF_ACCESS_TOKEN), encrypt(o.getAccessToken()))
                .putString(encrypt(PREF_REFRESH_TOKEN), encrypt(o.getRefreshToken()))
                .putString(encrypt(PREF_SCOPE), encrypt(o.getScope()))
                .putString(encrypt(PREF_TOKEN_TYPE), encrypt(o.getTokenType()))
                .putInt(encrypt(PREF_EXPIRES_IN), o.getExpiresIn())
                .apply();
    }

    void clear() {
        mPref.edit().clear().apply();
    }

    private String encrypt(String input) {
        // Simple encryption, not very strong!
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    private String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }
}
