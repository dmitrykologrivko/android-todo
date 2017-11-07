package com.dmitrykologrivkogmail.todolist.data;

import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthClient;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.di.IoScheduler;
import com.dmitrykologrivkogmail.todolist.di.UiScheduler;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

import static com.dmitrykologrivkogmail.todolist.common.util.Preconditions.isNullOrEmpty;

@PerApplication
public class AuthorizationManager {

    private final Scheduler mUiThread;
    private final Scheduler mIoThread;

    private final OAuthClient mAuthClient;
    private final CredentialsHelper mCredentialsHelper;

    @Inject
    public AuthorizationManager(@UiScheduler Scheduler uiThread,
                                @IoScheduler Scheduler ioThread,
                                OAuthClient client,
                                CredentialsHelper helper) {

        mUiThread = uiThread;
        mIoThread = ioThread;
        mAuthClient = client;
        mCredentialsHelper = helper;
    }

    public Observable<TokenDTO> signIn(String username, String password) {
        return mAuthClient.requestAccessToken(username, password)
                .concatMap(new Func1<TokenDTO, Observable<TokenDTO>>() {
                    @Override
                    public Observable<TokenDTO> call(TokenDTO o) {
                        return mCredentialsHelper.save(o);
                    }
                })
                .compose(new AsyncTransformer<TokenDTO>(mUiThread, mIoThread));
    }

    public Observable<Void> signOut() {
        return mCredentialsHelper.clear()
                .compose(new AsyncTransformer<Void>(mUiThread, mIoThread));
    }

    public Observable<Boolean> isAuthenticated() {
        Observable<Boolean> observable = Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                String accessToken = mCredentialsHelper.getAccessToken()
                        .toBlocking()
                        .first();
                String refreshToken = mCredentialsHelper.getRefreshToken()
                        .toBlocking()
                        .first();

                if (isNullOrEmpty(accessToken)) {
                    return false;
                }

                if (isNullOrEmpty(refreshToken)) {
                    return false;
                }

                return true;
            }
        });

        observable.compose(new AsyncTransformer<Boolean>(mUiThread, mIoThread));

        return observable;
    }
}
