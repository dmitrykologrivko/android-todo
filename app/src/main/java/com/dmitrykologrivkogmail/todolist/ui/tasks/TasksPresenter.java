package com.dmitrykologrivkogmail.todolist.ui.tasks;

import com.dmitrykologrivkogmail.todolist.R;
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

    private List<TaskDTO> mTasks;

    @Inject
    public TasksPresenter(CompositeSubscription cs, DataManager dataManager) {
        super(cs, dataManager);
    }

    public void getTasks() {
        checkViewAttached();

        getView().showProgress();

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
                        mTasks = tasks;

                        if (isTasksEmpty()) {
                            getView().showTasksEmpty();
                        } else {
                            getView().showTasks(mTasks);
                        }
                    }
                });

        addSubscription(subscription);
    }

    public void createTask() {
        checkViewAttached();

        String description = getView().getDescription();

        if (description == null || description.isEmpty()) {
            getView().showError(R.string.tasks_empty_description_error);
            return;
        }

        TaskDTO task = new TaskDTO.Builder()
                .description(description)
                .done(false)
                .build();

        getView().showProgress();

        Subscription subscription = mDataManager.createTask(task)
                .subscribe(new Observer<TaskDTO>() {
                    @Override
                    public void onCompleted() {
                        getView().dismissProgress();
                        getView().clearDescription();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().dismissProgress();
                        getView().showError(e.toString());
                    }

                    @Override
                    public void onNext(TaskDTO task) {
                        getView().addTask(task);
                    }
                });

        addSubscription(subscription);
    }

    public void editTask(TaskDTO task, String description) {
        checkViewAttached();

        if (description == null || description.isEmpty()) {
            getView().showError(R.string.tasks_empty_description_error);
            return;
        }

        task.setDescription(description);

        getView().showProgress();

        Subscription subscription = mDataManager.editTask(task)
                .subscribe(new Observer<TaskDTO>() {
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
                    public void onNext(TaskDTO task) {
                        getView().updateTask(task);
                    }
                });

        addSubscription(subscription);
    }

    public void markTask(TaskDTO task) {
        checkViewAttached();

        task.setDone(!task.isDone());

        Subscription subscription = mDataManager.markTask(task)
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

    public void deleteTask(TaskDTO task) {
        checkViewAttached();

        getView().showProgress();

        Subscription subscription = mDataManager.deleteTask(task)
                .subscribe(new Observer<TaskDTO>() {
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
                    public void onNext(TaskDTO task) {
                        getView().removeTask(task);
                    }
                });

        addSubscription(subscription);
    }

    public void signOut() {
        checkViewAttached();

        Subscription subscription = mDataManager.signOut()
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onCompleted() {
                        getView().runSignInActivity();
                        getView().finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });

        addSubscription(subscription);
    }

    public void onTaskClick(TaskDTO task) {
        getView().showEditDialog(task);
    }

    public void onDeleteClick(TaskDTO task) {
        getView().showDeleteDialog(task);
    }

    private boolean isTasksEmpty() {
        return mTasks == null || mTasks.isEmpty();
    }
}
