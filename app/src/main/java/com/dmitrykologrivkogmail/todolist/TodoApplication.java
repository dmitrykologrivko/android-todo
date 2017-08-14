package com.dmitrykologrivkogmail.todolist;

import android.app.Application;

import com.dmitrykologrivkogmail.todolist.common.constant.Injection;
import com.dmitrykologrivkogmail.todolist.di.component.ApplicationComponent;
import com.dmitrykologrivkogmail.todolist.di.component.DaggerApplicationComponent;
import com.dmitrykologrivkogmail.todolist.di.module.ApplicationModule;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;
import javax.inject.Named;

public class TodoApplication extends Application {

    private static ApplicationComponent sApplicationComponent;

    @Inject
    @Named(Injection.IS_ENABLE_STETHO)
    boolean isEnableStetho;

    @Override
    public void onCreate() {
        super.onCreate();

        sApplicationComponent = buildComponent();
        sApplicationComponent.inject(this);

        if (isEnableStetho) {
            initializeStetho();
        }
    }

    public static ApplicationComponent getApplicationComponent() {
        return sApplicationComponent;
    }

    protected ApplicationComponent buildComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    protected void initializeStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
