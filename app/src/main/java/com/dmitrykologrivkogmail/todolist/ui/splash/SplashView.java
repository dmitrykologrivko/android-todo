package com.dmitrykologrivkogmail.todolist.ui.splash;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SplashView extends MvpView {
    void startMainActivity();

    void startSignInActivity();

    void finish();
}
