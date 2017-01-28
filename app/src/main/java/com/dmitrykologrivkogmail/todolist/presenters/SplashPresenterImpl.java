package com.dmitrykologrivkogmail.todolist.presenters;

import android.app.Activity;
import android.os.Handler;

import com.dmitrykologrivkogmail.todolist.ui.SplashView;
import com.dmitrykologrivkogmail.todolist.ui.activities.MainActivity;

public class SplashPresenterImpl extends BasePresenter<SplashView> implements SplashPresenter {

    @Override
    public void startMainActivity(final Activity splash) {
        final int DELAY = 1000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                splash.startActivity(MainActivity.getStartIntent(splash));
                splash.finish();
            }
        }, DELAY);
    }
}
