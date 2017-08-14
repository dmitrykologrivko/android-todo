package com.dmitrykologrivkogmail.todolist.data.preferences;

import android.content.SharedPreferences;

import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.injection.PerApplication;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;

@PerApplication
public class CredentialsHelper {

    public static final String PREF_FILE_NAME = "cred_pref";

    static final String PREF_ACCESS_TOKEN = "access_token";
    static final String PREF_REFRESH_TOKEN = "refresh_token";
    static final String PREF_SCOPE = "scope";
    static final String PREF_EXPIRES_IN = "expires_in";
    static final String PREF_TOKEN_TYPE = "token_type";

    private final SharedPreferences mPref;

    @Inject
    public CredentialsHelper(SharedPreferences pref) {
        mPref = pref;
    }

    public Observable<String> getAccessToken() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mPref.getString(PREF_ACCESS_TOKEN, "");
            }
        });
    }

    public Observable<String> getRefreshToken() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mPref.getString(PREF_REFRESH_TOKEN, "");
            }
        });
    }

    public Observable<String> getTokenType() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mPref.getString(PREF_TOKEN_TYPE, "");
            }
        });
    }

    public Observable<String> getScope() {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return mPref.getString(PREF_SCOPE, "");
            }
        });
    }

    public Observable<Integer> getExpiresIn() {
        return Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return mPref.getInt(PREF_EXPIRES_IN, 0);
            }
        });
    }

    public Observable<TokenDTO> save(final TokenDTO token) {
        return Observable.fromCallable(new Callable<TokenDTO>() {
            @Override
            public TokenDTO call() throws Exception {
                mPref.edit()
                        .putString(PREF_ACCESS_TOKEN, token.getAccessToken())
                        .putString(PREF_REFRESH_TOKEN, token.getRefreshToken())
                        .putString(PREF_SCOPE, token.getScope())
                        .putString(PREF_TOKEN_TYPE, token.getTokenType())
                        .putInt(PREF_EXPIRES_IN, token.getExpiresIn())
                        .apply();
                return token;
            }
        });
    }

    public Observable<Void> clear() {
        return Observable.fromCallable(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                mPref.edit().clear().apply();
                return null;
            }
        });
    }
}
