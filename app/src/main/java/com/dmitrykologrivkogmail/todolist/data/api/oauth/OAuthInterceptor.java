package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OAuthInterceptor implements Interceptor {

    private static final String HEADER_AUTHORIZATION = "Authorization";

    private final OAuthManager mManager;

    public OAuthInterceptor(OAuthManager manager) {
        mManager = manager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        String tokenType =
                mManager.getStore().getTokenType();
        String accessToken =
                mManager.getStore().getAccessToken();

        accessToken = String.format("%s:%s", tokenType, accessToken);

        request = request.newBuilder()
                .addHeader(HEADER_AUTHORIZATION, accessToken)
                .build();

        return chain.proceed(request);
    }
}
