package com.dmitrykologrivkogmail.todolist;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class TodoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            // Facebook debug tools
            Stetho.initializeWithDefaults(this);
        }
    }
}
