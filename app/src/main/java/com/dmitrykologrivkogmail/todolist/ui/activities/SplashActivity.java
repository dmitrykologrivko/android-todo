package com.dmitrykologrivkogmail.todolist.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.presenters.SplashPresenter;
import com.dmitrykologrivkogmail.todolist.ui.SplashView;

public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> implements SplashView {

    private static final int LAYOUT = R.layout.activity_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        getPresenter().isAuthenticated();
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return new SplashPresenter(getApplicationContext());
    }

    @Override
    public void startMainActivity() {
        startActivity(MainActivity.getStartIntent(this));
    }

    @Override
    public void startSignInActivity() {
        startActivity(SignInActivity.getStartIntent(this));
    }
}
