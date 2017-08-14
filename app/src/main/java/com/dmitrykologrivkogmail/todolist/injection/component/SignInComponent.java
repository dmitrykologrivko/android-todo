package com.dmitrykologrivkogmail.todolist.injection.component;

import com.dmitrykologrivkogmail.todolist.injection.PerActivity;
import com.dmitrykologrivkogmail.todolist.injection.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.ui.modules.signin.SignInPresenter;
import com.dmitrykologrivkogmail.todolist.ui.modules.signin.SignInActivity;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface SignInComponent {

    void inject(SignInActivity activity);

    SignInPresenter signInPresenter();

}
