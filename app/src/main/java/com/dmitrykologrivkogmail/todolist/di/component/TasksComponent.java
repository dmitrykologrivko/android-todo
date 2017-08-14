package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.di.scope.PerActivity;
import com.dmitrykologrivkogmail.todolist.di.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.ui.module.tasks.TasksActivity;
import com.dmitrykologrivkogmail.todolist.ui.module.tasks.TasksPresenter;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface TasksComponent {

    void inject(TasksActivity activity);

    TasksPresenter tasksPresenter();

}
