package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.di.modules.ApplicationTestModule;
import com.dmitrykologrivkogmail.todolist.di.modules.DataTestModule;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationTestModule.class, DataTestModule.class})
public interface TestComponent extends ApplicationComponent {

    SignInTestComponent plusSignInTestComponent();

    SplashTestComponent plusSplashTestComponent();

    TasksTestComponent plusTasksTestComponent();

}
