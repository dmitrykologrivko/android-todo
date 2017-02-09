package com.dmitrykologrivkogmail.todolist.data;

import android.content.Context;

import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthManager;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthResponse;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DataManager {

    private final Observable.Transformer mSchedulersTransformer;

    private final OAuthManager mOAuthManager;

    public DataManager(Context context) {
        mOAuthManager = new OAuthManager(context);
        mSchedulersTransformer = null;
//        mSchedulersTransformer = o -> ((Observable) o).subscribeOn(mIoThread)
//                .observeOn(mUiThread)
//                .unsubscribeOn(mIoThread);
    }

    public Observable<OAuthResponse> signIn(String username, String password) {
        return mOAuthManager.getAccessToken(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> isAuthenticated() {
        return mOAuthManager.isAuthenticated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }
}
