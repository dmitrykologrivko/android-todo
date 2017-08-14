package com.dmitrykologrivkogmail.todolist.data;

import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthManager;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthResponse;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.data.mappers.TaskMapper;
import com.dmitrykologrivkogmail.todolist.data.mappers.TasksListMapper;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.injection.PerApplication;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

@PerApplication
public class DataManager {

    private final Observable.Transformer mSchedulersTransformer;

    private final OAuthManager mOAuthManager;

    private TasksService mTasksService;

    private TaskMapper mTaskMapper;
    private TasksListMapper mTasksListMapper;

    @Inject
    public DataManager(OAuthManager manager,
                       TasksService tasksService,
                       TaskMapper taskMapper,
                       TasksListMapper tasksListMapper) {

        mOAuthManager = manager;
        mTasksService = tasksService;
        mSchedulersTransformer = null;
        mTaskMapper = taskMapper;
        mTasksListMapper = tasksListMapper;
//        mSchedulersTransformer = o -> ((Observable) o).subscribeOn(mIoThread)
//                .observeOn(mUiThread)
//                .unsubscribeOn(mIoThread);
    }

    public Observable<OAuthResponse> signIn(String username, String password) {
        return mOAuthManager.getAccessToken(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Void> signOut() {
        return mOAuthManager.clearTokens()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> isAuthenticated() {
        return mOAuthManager.isAuthenticated()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Task>> getTasks() {
        return mTasksService.getTasks(Task.Ordering.IS_DONE.toString())
                .map(mTasksListMapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Task> createTask(Task task) {
        return mTasksService.createTask(task.getDescription())
                .map(mTaskMapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Task> editTask(Task task) {
        return mTasksService.editTask(task.getId(), task.getDescription())
                .map(mTaskMapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Task> markTask(Task task) {
        return mTasksService.markTask(task.getId(), task.isDone())
                .map(mTaskMapper)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Task> deleteTask(final Task task) {
        return mTasksService.deleteTask(task.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Void, Observable<Task>>() {
                    @Override
                    public Observable<Task> call(Void aVoid) {
                        return Observable.just(task);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }
}
