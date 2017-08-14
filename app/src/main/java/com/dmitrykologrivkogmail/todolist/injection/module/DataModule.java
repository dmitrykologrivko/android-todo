package com.dmitrykologrivkogmail.todolist.injection.module;

import com.dmitrykologrivkogmail.todolist.common.constant.config.ApiConfig;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthAuthenticator;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthInterceptor;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthManager;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.injection.PerApplication;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DataModule {

    // OkHttp settings
    private static final int CONNECT_TIMEOUT = 10;
    private static final int READ_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 120;

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
    OAuthInterceptor provideOAuthInterceptor(OAuthManager manager) {
        return new OAuthInterceptor(manager);
    }

    @Provides
    @PerApplication
    OAuthAuthenticator provideOAuthAuthenticator(OAuthManager manager) {
        return new OAuthAuthenticator(manager);
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
