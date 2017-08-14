package com.dmitrykologrivkogmail.todolist.di.modules;

import android.content.SharedPreferences;

import com.dmitrykologrivkogmail.todolist.common.constant.Injection;
import com.dmitrykologrivkogmail.todolist.common.constant.config.ApiConfig;
import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthClient;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.data.mappers.TaskMapper;
import com.dmitrykologrivkogmail.todolist.data.mappers.TasksListMapper;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.di.IoScheduler;
import com.dmitrykologrivkogmail.todolist.di.UiScheduler;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;
import com.google.gson.Gson;
import com.google.mockwebserver.MockWebServer;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

@Module
public class DataTestModule {

    @Provides
    @PerApplication
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Provides
    @PerApplication
    MockWebServer provideMockWebServer() {
        return new MockWebServer();
    }

    @Provides
    @PerApplication
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @PerApplication
    @UiScheduler
    Scheduler provideUiScheduler() {
        return Schedulers.immediate();
    }

    @Provides
    @PerApplication
    @IoScheduler
    Scheduler provideIoScheduler() {
        return Schedulers.immediate();
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
    SharedPreferences provideSharedPreferences() {
        return mock(SharedPreferences.class);
    }

    @Provides
    @PerApplication
    SharedPreferences.Editor provideSharedPreferencesEditor() {
        return mock(SharedPreferences.Editor.class);
    }

    @Provides
    @PerApplication
    CredentialsHelper provideCredentialsHelper() {
        return mock(CredentialsHelper.class);
    }

    @Provides
    @PerApplication
    OAuthClient provideOAuthClient() {
        return mock(OAuthClient.class);
    }

    @Provides
    @PerApplication
    AuthorizationManager provideAuthorizationManager() {
        return mock(AuthorizationManager.class);
    }

    @Provides
    @PerApplication
    DataManager provideDataManager() {
        return mock(DataManager.class);
    }

    @Provides
    @PerApplication
    TaskMapper provideTaskMapper() {
        return mock(TaskMapper.class);
    }

    @Provides
    @PerApplication
    TasksListMapper provideTasksListMapper() {
        return mock(TasksListMapper.class);
    }

    @Provides
    @PerApplication
    TasksService provideTasksService() {
        return mock(TasksService.class);
    }
}
