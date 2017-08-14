package com.dmitrykologrivkogmail.todolist.di.modules;

import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.module.signin.SignInView;
import com.dmitrykologrivkogmail.todolist.ui.module.splash.SplashView;
import com.dmitrykologrivkogmail.todolist.ui.module.tasks.TasksView;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Mockito.mock;

@Module
public class PresenterTestModule {

    @Provides
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

    @Provides
    @PerActivity
    SignInView provideSignInView() {
        return mock(SignInView.class);
    }

    @Provides
    @PerActivity
    SplashView provideSplashView() {
        return mock(SplashView.class);
    }

    @Provides
    @PerActivity
    TasksView provideTasksView() {
        return mock(TasksView.class);
    }
}
