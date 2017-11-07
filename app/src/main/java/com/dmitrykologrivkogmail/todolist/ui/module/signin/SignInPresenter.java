package com.dmitrykologrivkogmail.todolist.ui.module.signin;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.dmitrykologrivkogmail.todolist.common.util.Preconditions.isNullOrEmpty;

@PerActivity
public class SignInPresenter extends BasePresenter<SignInView> {

    private final AuthorizationManager mAuthorizationManager;

    @Inject
    public SignInPresenter(CompositeSubscription cs, AuthorizationManager am) {
        super(cs);
        mAuthorizationManager = am;
    }

    public void signIn() {
        checkViewAttached();

        String username = getView().getEditUsername();
        String password = getView().getEditPassword();

        if (isNullOrEmpty(username)) {
            getView().showError(R.string.sign_in_empty_email_error);
            return;
        }
        if (isNullOrEmpty(password)) {
            getView().showError(R.string.sign_in_empty_password_error);
            return;
        }

        Subscription subscription = mAuthorizationManager.signIn(username, password)
                .subscribe(new Observer<TokenDTO>() {
                    @Override
                    public void onCompleted() {
                        getView().dismissProgress();
                        getView().startTasksActivity();
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissProgress();
                        getView().showError(e);
                    }

                    @Override
                    public void onNext(TokenDTO response) {

                    }
                });

        getView().showProgress();

        addSubscription(subscription);
    }
}
