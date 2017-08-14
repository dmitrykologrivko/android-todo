package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.di.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.ui.module.signin.SignInPresenter;
import com.dmitrykologrivkogmail.todolist.ui.module.signin.SignInActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface SignInComponent {

    void inject(SignInActivity activity);

    SignInPresenter signInPresenter();

}
