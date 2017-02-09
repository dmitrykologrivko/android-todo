package com.dmitrykologrivkogmail.todolist.ui;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SignInView extends MvpView {
    String getEditUsername();

    String getEditPassword();

    void startMainActivity();

    void showProgress();

    void dismissProgress();

    void showError(int messageResource);

    void showError(String message);

    void finish();
}
