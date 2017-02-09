package com.dmitrykologrivkogmail.todolist.presenters;

import android.content.Context;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthResponse;
import com.dmitrykologrivkogmail.todolist.ui.SignInView;

import rx.Observer;
import rx.Subscription;

public class SignInPresenter extends BasePresenter<SignInView> {

    public SignInPresenter(Context context) {
        super(context);
    }

    public void signIn() {
        checkViewAttached();

        String username = getView().getEditUsername();
        String password = getView().getEditPassword();

        if (username == null || username.isEmpty()) {
            getView().showError(R.string.sign_in_empty_email_error);
            return;
        }
        if (password == null || password.isEmpty()) {
            getView().showError(R.string.sign_in_empty_password_error);
            return;
        }

        Subscription subscription = mDataManager.signIn(username, password)
                .subscribe(new Observer<OAuthResponse>() {
                    @Override
                    public void onCompleted() {
                        getView().dismissProgress();
                        getView().startMainActivity();
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissProgress();
                        getView().showError(e.toString());
                    }

                    @Override
                    public void onNext(OAuthResponse response) {

                    }
                });

        getView().showProgress();

        addSubscription(subscription);
    }

    public void onSubmitButtonClick() {
        signIn();
    }
}
