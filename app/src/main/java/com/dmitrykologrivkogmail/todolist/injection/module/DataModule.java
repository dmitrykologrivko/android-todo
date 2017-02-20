package com.dmitrykologrivkogmail.todolist.injection.module;

import com.dmitrykologrivkogmail.todolist.injection.PerApplication;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class DataModule {

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new Gson();
    }

}
