package com.dmitrykologrivkogmail.todolist.ui.tasks;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

public interface TasksView extends MvpView {

    void showProgress();

    void dismissProgress();

    void showTasks(List<TaskDTO> tasks);

    void updateTask(TaskDTO task);

    void showError(String message);

    void showTasksEmpty();

}
