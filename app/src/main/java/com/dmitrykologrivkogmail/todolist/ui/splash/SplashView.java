package com.dmitrykologrivkogmail.todolist.ui.splash;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface SplashView extends MvpView {
    void startTasksActivity();

    void startSignInActivity();

    void finish();
}
