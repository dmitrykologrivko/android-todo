package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

@PerApplication
public class OAuthAuthenticator implements Authenticator {

    static final String HEADER_AUTHORIZATION = "Authorization";

    private final OAuthClient mClient;
    private final CredentialsHelper mHelper;

    @Inject
    public OAuthAuthenticator(OAuthClient client, CredentialsHelper helper) {
        mClient = client;
        mHelper = helper;
    }

    @Nullable
    @Override
    public Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
        String refreshToken = mHelper.getRefreshToken()
                .toBlocking()
                .first();

        TokenDTO token = mClient.refreshAccessToken(refreshToken)
                .toBlocking()
                .first();

        mHelper.save(token)
                .toBlocking()
                .first();

        if (token != null) {
            String tokenType = token.getTokenType();
            String accessToken = token.getAccessToken();

            accessToken = String.format("%s:%s", tokenType, accessToken);

            // Repeat failed request
            return response.request().newBuilder()
                    .header(HEADER_AUTHORIZATION, accessToken)
                    .build();
        }

        return null;
    }
}
