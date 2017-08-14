package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import com.dmitrykologrivkogmail.todolist.common.constant.Injection;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.injection.PerApplication;
import com.google.gson.Gson;

import java.net.URL;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

@PerApplication
public class OAuthClient {

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

    private final OkHttpClient mClient;
    private final Gson mGson;

    private URL mUrl;
    private String mClientId;
    private String mClientSecret;

    @Inject
    public OAuthClient(OkHttpClient client,
                       Gson gson,
                       @Named(Injection.OAUTH_URL) URL url,
                       @Named(Injection.OAUTH_CLIENT_ID) String clientId,
                       @Named(Injection.OAUTH_CLIENT_SECRET) String clientSecret) {

        mClient = client;
        mGson = gson;
        mUrl = url;
        mClientId = clientId;
        mClientSecret = clientSecret;
    }

    public Observable<TokenDTO> requestAccessToken(final String username, final String password) {
        return Observable.fromCallable(new Callable<TokenDTO>() {
            @Override
            public TokenDTO call() throws Exception {
                Request request = new Request.Builder()
                        .url(mUrl)
                        .header(HEADER_ACCEPT, HEADER_APPLICATION_JSON)
                        .post(new FormBody.Builder()
                                .add(FORM_FIELD_CLIENT_ID, mClientId)
                                .add(FORM_FIELD_CLIENT_SECRET, mClientSecret)
                                .add(FORM_FIELD_GRANT_TYPE, GRANT_TYPE_PASSWORD)
                                .add(FORM_FIELD_USERNAME, username)
                                .add(FORM_FIELD_PASSWORD, password)
                                .build())
                        .build();

                Response response = mClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    return mGson.fromJson(body, TokenDTO.class);
                }

                throw new OAuthError();
            }
        });
    }

    public Observable<TokenDTO> refreshAccessToken(final String refreshToken) {
        return Observable.fromCallable(new Callable<TokenDTO>() {
            @Override
            public TokenDTO call() throws Exception {
                Request request = new Request.Builder()
                        .url(mUrl)
                        .header(HEADER_ACCEPT, HEADER_APPLICATION_JSON)
                        .post(new FormBody.Builder()
                                .add(FORM_FIELD_CLIENT_ID, mClientId)
                                .add(FORM_FIELD_CLIENT_SECRET, mClientSecret)
                                .add(FORM_FIELD_GRANT_TYPE, GRANT_TYPE_REFRESH_TOKEN)
                                .add(FORM_FIELD_REFRESH_TOKEN, refreshToken)
                                .build())
                        .build();

                Response response = mClient.newCall(request).execute();

                if (response.isSuccessful()) {
                    String body = response.body().string();
                    return mGson.fromJson(body, TokenDTO.class);
                }

                throw new OAuthError();
            }
        });
    }
}
