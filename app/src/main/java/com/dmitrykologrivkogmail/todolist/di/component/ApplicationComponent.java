package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;
import com.dmitrykologrivkogmail.todolist.di.module.ApplicationModule;
import com.dmitrykologrivkogmail.todolist.di.module.DataModule;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    DataManager dataManager();

    TasksService tasksService();

    SignInComponent plusSignInComponent();

    SplashComponent plusSplashComponent();

    TasksComponent plusTasksComponent();

}
