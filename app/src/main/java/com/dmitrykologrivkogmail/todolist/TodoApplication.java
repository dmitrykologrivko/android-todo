package com.dmitrykologrivkogmail.todolist;

import android.app.Application;

import com.dmitrykologrivkogmail.todolist.di.component.ApplicationComponent;
import com.dmitrykologrivkogmail.todolist.di.component.DaggerApplicationComponent;
import com.dmitrykologrivkogmail.todolist.di.module.ApplicationModule;
import com.facebook.stetho.Stetho;

public class TodoApplication extends Application {

    private static ApplicationComponent sApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create dagger application component
        sApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        if (BuildConfig.DEBUG) {
            // Facebook debug tools
            Stetho.initializeWithDefaults(this);
        }
    }

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }
}
