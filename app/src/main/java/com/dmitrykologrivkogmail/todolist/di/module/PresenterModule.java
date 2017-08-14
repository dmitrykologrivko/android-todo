package com.dmitrykologrivkogmail.todolist.di.module;

import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class PresenterModule {

    @Provides
    @PerActivity
    CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
