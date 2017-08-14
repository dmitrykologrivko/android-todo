package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.di.module.ApplicationTestModule;
import com.dmitrykologrivkogmail.todolist.di.module.DataTestModule;
import com.dmitrykologrivkogmail.todolist.di.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationTestModule.class,
        DataTestModule.class,
        PresenterModule.class})
public interface TestComponent extends ApplicationComponent {
}
