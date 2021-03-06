package com.dmitrykologrivkogmail.todolist.ui.module.splash;

import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@PerActivity
public class SplashPresenter extends BasePresenter<SplashView> {

    private final AuthorizationManager mAuthorizationManager;

    @Inject
    public SplashPresenter(CompositeSubscription cs, AuthorizationManager am) {
        super(cs);
        mAuthorizationManager = am;
    }

    public void isAuthenticated() {
        checkViewAttached();

        Subscription subscription = mAuthorizationManager.isAuthenticated()
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
                            getView().startTasksActivity();
                        } else {
                            getView().startSignInActivity();
                        }
                    }
                });

        addSubscription(subscription);
    }
}
