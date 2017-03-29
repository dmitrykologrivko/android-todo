package com.dmitrykologrivkogmail.todolist.injection.component;

import com.dmitrykologrivkogmail.todolist.injection.PerActivity;
import com.dmitrykologrivkogmail.todolist.injection.module.PresenterModule;
import com.dmitrykologrivkogmail.todolist.ui.tasks.TasksActivity;
import com.dmitrykologrivkogmail.todolist.ui.tasks.TasksPresenter;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = {PresenterModule.class})
public interface TasksComponent {

    void inject(TasksActivity activity);

    TasksPresenter tasksPresenter();

}