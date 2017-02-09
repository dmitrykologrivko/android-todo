package com.dmitrykologrivkogmail.todolist.presenters;

import android.content.Context;

import com.dmitrykologrivkogmail.todolist.ui.SplashView;

import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;

public class SplashPresenter extends BasePresenter<SplashView> {

    public SplashPresenter(Context context) {
        super(context);
    }

    public void isAuthenticated() {
        checkViewAttached();

        Subscription subscription = mDataManager.isAuthenticated()
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean isAuthenticated) {
                        if (isAuthenticated) {
                            getView().startMainActivity();
                        } else {
                            getView().startSignInActivity();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
