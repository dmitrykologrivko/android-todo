package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import android.content.Context;

import com.dmitrykologrivkogmail.todolist.BuildConfig;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

public class OAuthManager {

    private static final String OAUTH_URL = "o/token/";

    // Header consts
    private static final String HEADER_ACCEPT = "Accept";
    private static final String HEADER_APPLICATION_JSON = "application/json";

    // Form consts
    private static final String FORM_FIELD_CLIENT_ID = "client_id";
    private static final String FORM_FIELD_CLIENT_SECRET = "client_secret";
    private static final String FORM_FIELD_GRANT_TYPE = "grant_type";
    private static final String FORM_FIELD_USERNAME = "username";
    private static final String FORM_FIELD_PASSWORD = "password";
    private static final String FORM_FIELD_REFRESH_TOKEN = "refresh_token";

    // Grant consts
    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";

    private final OkHttpClient mClient = new OkHttpClient();
    private final Gson mGson = new Gson();

    private final OAuthStore mStore;

    public OAuthManager(Context context) {
        mStore = new OAuthStore(context);
    }

    public OAuthResponse getAccessTokenSync(final String username,
                                            final String password) throws OAuthError {
        Request request = new Request.Builder()
                .url(BuildConfig.BACKEND_URL + OAUTH_URL)
                .header(HEADER_ACCEPT, HEADER_APPLICATION_JSON)
                .post(new FormBody.Builder()
                        .add(FORM_FIELD_CLIENT_ID, BuildConfig.CLIENT_ID)
                        .add(FORM_FIELD_CLIENT_SECRET, BuildConfig.CLIENT_SECRET)
                        .add(FORM_FIELD_GRANT_TYPE, GRANT_TYPE_PASSWORD)
                        .add(FORM_FIELD_USERNAME, username)
                        .add(FORM_FIELD_PASSWORD, password)
                        .build())
                .build();

        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                OAuthResponse o = mGson.fromJson(body, OAuthResponse.class);

                if (o != null) {
                    // Save response
                    mStore.save(o);
                    return o;
                }
            }
        } catch (IOException e) {
            throw new OAuthError(e.getMessage(), e);
        }

        throw new OAuthError("Can not authenticate");
    }

    public OAuthResponse getRefreshTokenSync() throws OAuthError {
        // Get refresh token from store
        String refreshToken = mStore.getRefreshToken();

        Request request = new Request.Builder()
                .url(BuildConfig.BACKEND_URL + OAUTH_URL)
                .header(HEADER_ACCEPT, HEADER_APPLICATION_JSON)
                .post(new FormBody.Builder()
                        .add(FORM_FIELD_CLIENT_ID, BuildConfig.CLIENT_ID)
                        .add(FORM_FIELD_CLIENT_SECRET, BuildConfig.CLIENT_SECRET)
                        .add(FORM_FIELD_GRANT_TYPE, GRANT_TYPE_REFRESH_TOKEN)
                        .add(FORM_FIELD_REFRESH_TOKEN, refreshToken)
                        .build())
                .build();

        try {
            Response response = mClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String body = response.body().string();
                OAuthResponse o = mGson.fromJson(body, OAuthResponse.class);

                if (o != null) {
                    // Save response
                    mStore.save(o);
                    return o;
                }
            }
        } catch (IOException e) {
            throw new OAuthError(e.getMessage(), e);
        }

        throw new OAuthError("Can not authenticate");
    }

    public boolean isAuthenticatedSync() {
        String accessToken = mStore.getAccessToken();
        String refreshToken = mStore.getRefreshToken();

        if (accessToken == null || accessToken.isEmpty()) {
            return false;
        }

        if (refreshToken == null || refreshToken.isEmpty()) {
            return false;
        }

        return true;
    }

    public void clearTokensSync() {
        mStore.clear();
    }

    public Observable<OAuthResponse> getAccessToken(final String username,
                                                    final String password) {
        return Observable.fromCallable(new Callable<OAuthResponse>() {
            @Override
            public OAuthResponse call() throws Exception {
                return getAccessTokenSync(username, password);
            }
        });
    }

    public Observable<OAuthResponse> getRefreshToken() {
        return Observable.fromCallable(new Callable<OAuthResponse>() {
            @Override
            public OAuthResponse call() throws Exception {
                return getRefreshTokenSync();
            }
        });
    }

    public Observable<Boolean> isAuthenticated() {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return isAuthenticatedSync();
            }
        });
    }

    public Observable<Void> clearTokens() {
        return Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                clearTokensSync();
                return null;
            }
        });
    }

    /**
     * Using only for package private components.
     *
     * @return OAuth store
     */
    OAuthStore getStore() {
        return mStore;
    }
}
