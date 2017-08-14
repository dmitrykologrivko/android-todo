package com.dmitrykologrivkogmail.todolist.common.util;

import android.content.Context;

import com.dmitrykologrivkogmail.todolist.BuildConfig;
import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthError;

import java.net.UnknownHostException;

import retrofit2.adapter.rxjava.HttpException;

public final class ErrorUtil {

    public static String handleError(Context ctx, Throwable e) {
        if (e instanceof UnknownHostException)
            return handleNetworkError(ctx, (UnknownHostException) e);

        if (e instanceof HttpException)
            return handleNetworkError(ctx, (HttpException) e);

        if (e instanceof OAuthError)
            return handleAuthError(ctx, (OAuthError) e);

        return prepareMessage(ctx.getString(R.string.error_unknown), e);
    }

    public static String handleNetworkError(Context ctx, UnknownHostException e) {
        if (!NetworkUtil.isNetworkConnected(ctx))
            return prepareMessage(ctx.getString(R.string.error_offline), e);
        else
            return prepareMessage(ctx.getString(R.string.error_connection), e);
    }

    public static String handleNetworkError(Context ctx, HttpException e) {
        String error = ctx.getString(R.string.error_http_unknown);

        int code = e.code();

        if (code >= 400 && code <= 500) {
            switch (code) {
                case 400:
                    error = ctx.getString(R.string.error_http_syntax);
                    break;
                case 401:
                    error = ctx.getString(R.string.error_http_auth);
                    break;
                case 403:
                    error = ctx.getString(R.string.error_http_forbidden);
                    break;
                case 404:
                    error = ctx.getString(R.string.error_http_resource_not_found);
                    break;
                case 405:
                    error = ctx.getString(R.string.error_http_method_not_allowed);
                    break;
                case 408:
                    error = ctx.getString(R.string.error_http_timeout);
                    break;
                default:
                    error = ctx.getString(R.string.error_http_client_side);
                    break;
            }
        }

        if (code >= 500) {
            switch (code) {
                case 500:
                    error = ctx.getString(R.string.error_http_internal_server);
                    break;
                case 503:
                    error = ctx.getString(R.string.error_http_unavailable);
                    break;
                default:
                    error = ctx.getString(R.string.error_http_server_side);
                    break;
            }
        }

        return prepareMessage(error, e);
    }

    public static String handleAuthError(Context ctx, OAuthError e) {
        return prepareMessage(ctx.getString(R.string.error_http_auth), e);
    }

    private static String prepareMessage(String error, Throwable e) {
        if (BuildConfig.DEBUG) {
            return String.format("%s\n\nDebug information:\n%s", error, e.toString());
        }
        return error;
    }
}
