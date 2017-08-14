package com.dmitrykologrivkogmail.todolist.ui.tasks;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface TasksView extends MvpView {

    String getDescription();

    void clearDescription();

    void showProgress();

    void dismissProgress();

    void showTasks(List<TaskDTO> tasks);

    void sortTasks();

    void addTask(TaskDTO task);

    void updateTask(TaskDTO task);

    void removeTask(TaskDTO task);

    void showEditDialog(TaskDTO task);

    void showDeleteDialog(TaskDTO task);

    void showError(String message);

    void showError(int messageResource);

    void showTasksEmpty();

    void runSignInActivity();

    void finish();

}
