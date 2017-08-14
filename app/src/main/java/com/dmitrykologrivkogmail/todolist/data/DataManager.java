package com.dmitrykologrivkogmail.todolist.data;

import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.data.mappers.TaskMapper;
import com.dmitrykologrivkogmail.todolist.data.mappers.TasksListMapper;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.injection.IoScheduler;
import com.dmitrykologrivkogmail.todolist.injection.scope.PerApplication;
import com.dmitrykologrivkogmail.todolist.injection.UiScheduler;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

@PerApplication
public class DataManager {

    private final Scheduler mUiThread;
    private final Scheduler mIoThread;

    private TasksService mTasksService;

    private TaskMapper mTaskMapper;
    private TasksListMapper mTasksListMapper;

    @Inject
    public DataManager(@UiScheduler Scheduler uiThread,
                       @IoScheduler Scheduler ioThread,
                       TasksService tasksService,
                       TaskMapper taskMapper,
                       TasksListMapper tasksListMapper) {

        mUiThread = uiThread;
        mIoThread = ioThread;
        mTasksService = tasksService;
        mTaskMapper = taskMapper;
        mTasksListMapper = tasksListMapper;
    }

    public Observable<List<Task>> getTasks() {
        return mTasksService.getTasks(Task.Ordering.IS_DONE.toString())
                .map(mTasksListMapper)
                .compose(new AsyncTransformer<List<Task>>(mUiThread, mIoThread));
    }

    public Observable<Task> createTask(Task task) {
        return mTasksService.createTask(task.getDescription())
                .map(mTaskMapper)
                .compose(new AsyncTransformer<Task>(mUiThread, mIoThread));
    }

    public Observable<Task> editTask(Task task) {
        return mTasksService.editTask(task.getId(), task.getDescription())
                .map(mTaskMapper)
                .compose(new AsyncTransformer<Task>(mUiThread, mIoThread));
    }

    public Observable<Task> markTask(Task task) {
        return mTasksService.markTask(task.getId(), task.isDone())
                .map(mTaskMapper)
                .compose(new AsyncTransformer<Task>(mUiThread, mIoThread));
    }

    public Observable<Task> deleteTask(final Task task) {
        return mTasksService.deleteTask(task.getId())
                .concatMap(new Func1<Void, Observable<Task>>() {
                    @Override
                    public Observable<Task> call(Void aVoid) {
                        return Observable.just(task);
                    }
                })
                .compose(new AsyncTransformer<Task>(mUiThread, mIoThread));
    }
}
