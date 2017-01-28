package com.dmitrykologrivkogmail.todolist.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.presenters.SplashPresenter;
import com.dmitrykologrivkogmail.todolist.presenters.SplashPresenterImpl;
import com.dmitrykologrivkogmail.todolist.ui.SplashView;

public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> implements SplashView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getPresenter().startMainActivity(this);
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenterImpl();
    }
}
