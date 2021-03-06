package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.di.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.ui.module.splash.SplashPresenter;
import com.dmitrykologrivkogmail.todolist.ui.module.splash.SplashActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface SplashComponent {

    void inject(SplashActivity activity);

    SplashPresenter splashPresenter();

}
