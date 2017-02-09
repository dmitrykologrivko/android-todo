package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import com.google.gson.annotations.SerializedName;

public class OAuthResponse {

    private static final String ACCESS_TOKEN = "access_token";
    private static final String SCOPE = "scope";
    private static final String EXPIRES_IN = "expires_in";
    private static final String TOKEN_TYPE = "token_type";
    private static final String REFRESH_TOKEN = "refresh_token";

    @SerializedName(ACCESS_TOKEN)
    private String mAccessToken;
    @SerializedName(SCOPE)
    private String mScope;
    @SerializedName(EXPIRES_IN)
    private int mExpiresIn;
    @SerializedName(TOKEN_TYPE)
    private String mTokenType;
    @SerializedName(REFRESH_TOKEN)
    private String mRefreshToken;

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public String getScope() {
        return mScope;
    }

    public void setScope(String scope) {
        mScope = scope;
    }

    public int getExpiresIn() {
        return mExpiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        mExpiresIn = expiresIn;
    }

    public String getTokenType() {
        return mTokenType;
    }

    public void setTokenType(String tokenType) {
        mTokenType = tokenType;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }
}
