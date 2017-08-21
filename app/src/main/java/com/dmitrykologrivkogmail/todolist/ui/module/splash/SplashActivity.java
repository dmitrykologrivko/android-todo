package com.dmitrykologrivkogmail.todolist.ui.module.splash;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.TodoApplication;
import com.dmitrykologrivkogmail.todolist.di.component.SplashComponent;
import com.dmitrykologrivkogmail.todolist.ui.base.BaseActivity;
import com.dmitrykologrivkogmail.todolist.ui.module.signin.SignInActivity;
import com.dmitrykologrivkogmail.todolist.ui.module.tasks.TasksActivity;

public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> implements SplashView {

    private static final int LAYOUT = R.layout.activity_splash;

    private static final int DELAY = 1000;

    private final SplashComponent mSplashComponent =
            TodoApplication.getApplicationComponent().plusSplashComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        // Make 1 second delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getPresenter().isAuthenticated();
            }
        }, DELAY);
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return mSplashComponent.splashPresenter();
    }

    @Override
    public void startTasksActivity() {
        startActivity(TasksActivity.getStartIntent(this));
    }

    @Override
    public void startSignInActivity() {
        startActivity(SignInActivity.getStartIntent(this));
    }
}
