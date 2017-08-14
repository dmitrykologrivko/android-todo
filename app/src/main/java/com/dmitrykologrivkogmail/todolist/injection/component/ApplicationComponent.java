package com.dmitrykologrivkogmail.todolist.injection.component;

import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.injection.scope.PerApplication;
import com.dmitrykologrivkogmail.todolist.injection.module.ApplicationModule;
import com.dmitrykologrivkogmail.todolist.injection.module.DataModule;

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
