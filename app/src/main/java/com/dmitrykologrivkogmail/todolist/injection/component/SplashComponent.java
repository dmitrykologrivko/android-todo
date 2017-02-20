package com.dmitrykologrivkogmail.todolist.injection.component;

import com.dmitrykologrivkogmail.todolist.injection.PerActivity;
import com.dmitrykologrivkogmail.todolist.injection.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.presenters.SplashPresenter;
import com.dmitrykologrivkogmail.todolist.ui.activities.SplashActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface SplashComponent {

    void inject(SplashActivity activity);

    SplashPresenter splashPresenter();

}
