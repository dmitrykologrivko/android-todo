package com.dmitrykologrivkogmail.todolist.di.module;

import android.app.Application;
import android.content.Context;

import com.dmitrykologrivkogmail.todolist.BuildConfig;
import com.dmitrykologrivkogmail.todolist.common.constant.Injection;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Named(Injection.IS_ENABLE_STETHO)
    boolean provideIsEnableStetho() {
        return BuildConfig.DEBUG; // Enable only for debug
    }

}
