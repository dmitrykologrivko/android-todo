package com.dmitrykologrivkogmail.todolist.injection.module;

import com.dmitrykologrivkogmail.todolist.injection.PerActivity;

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
