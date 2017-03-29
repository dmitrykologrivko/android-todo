package com.dmitrykologrivkogmail.todolist.ui.splash;

import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.injection.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.base.BasePresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@PerActivity
public class SplashPresenter extends BasePresenter<SplashView> {

    @Inject
    public SplashPresenter(CompositeSubscription cs, DataManager dataManager) {
        super(cs, dataManager);
    }

    public void isAuthenticated() {
        checkViewAttached();

        Subscription subscription = mDataManager.isAuthenticated()
                .delay(1, TimeUnit.SECONDS)
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
