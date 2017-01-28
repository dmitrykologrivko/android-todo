package com.dmitrykologrivkogmail.todolist.presenters;

import android.app.Activity;

import com.dmitrykologrivkogmail.todolist.ui.SplashView;
import com.hannesdorfmann.mosby.mvp.MvpPresenter;

public interface SplashPresenter extends MvpPresenter<SplashView> {
    void startMainActivity(final Activity splash);
}
