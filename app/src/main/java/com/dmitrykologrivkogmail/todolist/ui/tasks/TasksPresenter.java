package com.dmitrykologrivkogmail.todolist.ui.tasks;

import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.injection.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@PerActivity
public class TasksPresenter extends BasePresenter<TasksView> {

    @Inject
    public TasksPresenter(CompositeSubscription cs, DataManager dataManager) {
        super(cs, dataManager);
    }

    public void getTasks() {
        checkViewAttached();

        Subscription subscription = mDataManager.getTasks()
                .subscribe(new Observer<List<TaskDTO>>() {
                    @Override
                    public void onCompleted() {
                        getView().dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissProgress();
                        getView().showError(e.toString());
                    }

                    @Override
                    public void onNext(List<TaskDTO> tasks) {
                        if (tasks.isEmpty()) {
                            getView().showTasksEmpty();
                        } else {
                            getView().showTasks(tasks);
                        }
                    }
                });

        getView().showProgress();

        addSubscription(subscription);
    }

    public void markTask(long id, boolean isDone) {
        checkViewAttached();

        Subscription subscription = mDataManager.markTask(id, isDone)
                .subscribe(new Observer<TaskDTO>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.toString());
                    }

                    @Override
                    public void onNext(TaskDTO task) {
                        getView().updateTask(task);
                    }
                });

        addSubscription(subscription);
    }

    public void onTaskMarked(long id, boolean isDone) {
        markTask(id, isDone);
    }
}
