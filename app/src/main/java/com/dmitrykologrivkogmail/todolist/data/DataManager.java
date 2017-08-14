package com.dmitrykologrivkogmail.todolist.data;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthManager;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthResponse;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
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

    @Inject
    public DataManager(OAuthManager manager, TasksService tasksService) {
        mOAuthManager = manager;
        mTasksService = tasksService;
        mSchedulersTransformer = null;
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

    public Observable<List<TaskDTO>> getTasks() {
        return mTasksService.getTasks(TasksService.IS_DONE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TaskDTO> createTask(TaskDTO task) {
        return mTasksService.createTask(task.getDescription())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TaskDTO> editTask(TaskDTO task) {
        return mTasksService.editTask(task.getId(), task.getDescription())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TaskDTO> markTask(TaskDTO task) {
        return mTasksService.markTask(task.getId(), task.isDone())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<TaskDTO> deleteTask(final TaskDTO task) {
        return mTasksService.deleteTask(task.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Void, Observable<TaskDTO>>() {
                    @Override
                    public Observable<TaskDTO> call(Void aVoid) {
                        return Observable.just(task);
                    }
                });
    }

    @SuppressWarnings("unchecked")
    private <T> Observable.Transformer<T, T> applySchedulers() {
        return (Observable.Transformer<T, T>) mSchedulersTransformer;
    }
}
