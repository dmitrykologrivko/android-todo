package com.dmitrykologrivkogmail.todolist.di.module;

import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class DataTestModule {

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

}
