package com.dmitrykologrivkogmail.todolist.ui.splash;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.TodoApplication;
import com.dmitrykologrivkogmail.todolist.injection.component.SplashComponent;
import com.dmitrykologrivkogmail.todolist.ui.base.BaseActivity;
import com.dmitrykologrivkogmail.todolist.ui.modules.signin.SignInActivity;
import com.dmitrykologrivkogmail.todolist.ui.tasks.TasksActivity;

public class SplashActivity extends BaseActivity<SplashView, SplashPresenter> implements SplashView {

    private static final int LAYOUT = R.layout.activity_splash;

    private final SplashComponent mSplashComponent =
            TodoApplication.getApplicationComponent().plusSplashComponent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        getPresenter().isAuthenticated();
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
