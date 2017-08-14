package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.TodoApplication;
import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.di.module.ApplicationModule;
import com.dmitrykologrivkogmail.todolist.di.module.DataModule;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    void inject(TodoApplication todoApplication);

    DataManager dataManager();

    AuthorizationManager authorizationManager();

    SignInComponent plusSignInComponent();

    SplashComponent plusSplashComponent();

    TasksComponent plusTasksComponent();

}
