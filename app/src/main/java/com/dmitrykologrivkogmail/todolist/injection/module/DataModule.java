package com.dmitrykologrivkogmail.todolist.injection.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.dmitrykologrivkogmail.todolist.common.constant.Injection;
import com.dmitrykologrivkogmail.todolist.common.constant.config.ApiConfig;
import com.dmitrykologrivkogmail.todolist.common.util.DeviceUtil;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthAuthenticator;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthInterceptor;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.injection.IoScheduler;
import com.dmitrykologrivkogmail.todolist.injection.scope.PerApplication;
import com.dmitrykologrivkogmail.todolist.injection.UiScheduler;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.securepreferences.SecurePreferences;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class DataModule {

    // OkHttp settings
    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;

    @Provides
    @PerApplication
    @UiScheduler
    Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @PerApplication
    @IoScheduler
    Scheduler provideIoScheduler() {
        return Schedulers.io();
    }

    @Provides
    @PerApplication
    @Named(Injection.OAUTH_URL)
    URL provideOAuthUrl() {
        try {
            return new URL(ApiConfig.OAUTH_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Provides
    @PerApplication
    @Named(Injection.OAUTH_CLIENT_ID)
    String provideOAuthClientId() {
        return ApiConfig.OAUTH_CLIENT_ID;
    }

    @Provides
    @PerApplication
    @Named(Injection.OAUTH_CLIENT_SECRET)
    String provideOAuthClientSecret() {
        return ApiConfig.OAUTH_CLIENT_SECRET;
    }

    @Provides
    @PerApplication
    SharedPreferences provideSharedPreferences(Context ctx) {
        return new SecurePreferences(ctx,
                DeviceUtil.getDeviceId(ctx),
                CredentialsHelper.PREF_FILE_NAME);
    }

    @Provides
    @PerApplication
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient(StethoInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .build();
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @PerApplication
    Retrofit provideRetrofit(OkHttpClient client,
                             Gson gson,
                             OAuthInterceptor interceptor,
                             OAuthAuthenticator authAuthenticator) {

        return new Retrofit.Builder()
                .baseUrl(ApiConfig.BACKEND_URL)
                .client(client.newBuilder()
                        .addInterceptor(interceptor)
                        .authenticator(authAuthenticator)
                        .build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @PerApplication
    TasksService provideTasksService(Retrofit retrofit) {
        return retrofit.create(TasksService.class);
    }
}
