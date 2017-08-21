package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.di.modules.PresenterTestModule;
import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.module.splash.SplashPresenterTest;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterTestModule.class})
public interface SplashTestComponent {

    void inject(SplashPresenterTest test);

}
