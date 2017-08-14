package com.dmitrykologrivkogmail.todolist.ui.module.tasks;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.injection.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@PerActivity
public class TasksPresenter extends BasePresenter<TasksView> {

    private final DataManager mDataManager;

    private final AuthorizationManager mAuthorizationManager;

    private List<Task> mTasks;

    @Inject
    public TasksPresenter(CompositeSubscription cs, DataManager dm, AuthorizationManager am) {
        super(cs);
        mDataManager = dm;
        mAuthorizationManager = am;
    }

    public void getTasks() {
        checkViewAttached();

        getView().showProgress();

        Subscription subscription = mDataManager.getTasks()
                .subscribe(new Observer<List<Task>>() {
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
                    public void onNext(List<Task> tasks) {
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

        Task task = new Task.Builder()
                .description(description)
                .done(false)
                .build();

        getView().showProgress();

        Subscription subscription = mDataManager.createTask(task)
                .subscribe(new Observer<Task>() {
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
                    public void onNext(Task task) {
                        getView().addTask(task);
                        getView().sortTasks();
                    }
                });

        addSubscription(subscription);
    }

    public void editTask(Task task, String description) {
        checkViewAttached();

        if (description == null || description.isEmpty()) {
            getView().showError(R.string.tasks_empty_description_error);
            return;
        }

        task.setDescription(description);

        getView().showProgress();

        Subscription subscription = mDataManager.editTask(task)
                .subscribe(new Observer<Task>() {
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
                    public void onNext(Task task) {
                        getView().updateTask(task);
                    }
                });

        addSubscription(subscription);
    }

    public void markTask(Task task) {
        checkViewAttached();

        task.setDone(!task.isDone());

        Subscription subscription = mDataManager.markTask(task)
                .subscribe(new Observer<Task>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showError(e.toString());
                    }

                    @Override
                    public void onNext(Task task) {
                        getView().updateTask(task);
                        getView().sortTasks();
                    }
                });

        addSubscription(subscription);
    }

    public void deleteTask(Task task) {
        checkViewAttached();

        getView().showProgress();

        Subscription subscription = mDataManager.deleteTask(task)
                .subscribe(new Observer<Task>() {
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
                    public void onNext(Task task) {
                        getView().removeTask(task);
                    }
                });

        addSubscription(subscription);
    }

    public void signOut() {
        checkViewAttached();

        Subscription subscription = mAuthorizationManager.signOut()
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

    public void onTaskClick(Task task) {
        getView().showEditDialog(task);
    }

    public void onDeleteButtonClick(Task task) {
        getView().showDeleteDialog(task);
    }

    private boolean isTasksEmpty() {
        return mTasks == null || mTasks.isEmpty();
    }
}
