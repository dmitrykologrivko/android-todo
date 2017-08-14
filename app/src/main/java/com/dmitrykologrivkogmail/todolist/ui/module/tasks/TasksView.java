package com.dmitrykologrivkogmail.todolist.ui.module.tasks;

import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface TasksView extends MvpView {

    String getDescription();

    void clearDescription();

    void showProgress();

    void dismissProgress();

    void showTasks(List<Task> tasks);

    void sortTasks();

    void addTask(Task task);

    void updateTask(Task task);

    void removeTask(Task task);

    void showEditDialog(Task task);

    void showDeleteDialog(Task task);

    void showError(Throwable e);

    void showError(String message);

    void showError(int messageResource);

    void showTasksEmpty();

    void runSignInActivity();

    void finish();

}
