package com.dmitrykologrivkogmail.todolist.ui.module.signin;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SignInView extends MvpView {

    String getEditUsername();

    String getEditPassword();

    void startTasksActivity();

    void showProgress();

    void dismissProgress();

    void showError(Throwable e);

    void showError(int messageResource);

    void showError(String message);

    void finish();

}
