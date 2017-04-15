package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class OAuthAuthenticator implements Authenticator {

    private static final String HEADER_AUTHORIZATION = "Authorization";

    private final OAuthManager mManager;

    public OAuthAuthenticator(OAuthManager oauthManager) {
        mManager = oauthManager;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        OAuthResponse o = mManager.getRefreshTokenSync();

        if (o != null) {
            String tokenType = o.getTokenType();
            String accessToken = o.getAccessToken();

            accessToken = String.format("%s:%s", tokenType, accessToken);

            // Repeat failed request
            return response.request().newBuilder()
                    .header(HEADER_AUTHORIZATION, accessToken)
                    .build();
        }

        return null;
    }
}
