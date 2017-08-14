package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import android.support.annotation.NonNull;

import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@PerApplication
public class OAuthInterceptor implements Interceptor {

    static final String HEADER_AUTHORIZATION = "Authorization";

    private final CredentialsHelper mHelper;

    @Inject
    public OAuthInterceptor(CredentialsHelper helper) {
        mHelper = helper;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        String tokenType = mHelper.getTokenType()
                .toBlocking()
                .first();
        String accessToken = mHelper.getAccessToken()
                .toBlocking()
                .first();

        accessToken = String.format("%s:%s", tokenType, accessToken);

        request = request.newBuilder()
                .addHeader(HEADER_AUTHORIZATION, accessToken)
                .build();

        return chain.proceed(request);
    }
}
