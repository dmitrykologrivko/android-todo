package com.dmitrykologrivkogmail.todolist.injection.component;

import com.dmitrykologrivkogmail.todolist.injection.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.injection.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.ui.module.signin.SignInPresenter;
import com.dmitrykologrivkogmail.todolist.ui.module.signin.SignInActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface SignInComponent {

    void inject(SignInActivity activity);

    SignInPresenter signInPresenter();

}
