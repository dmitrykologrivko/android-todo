package com.dmitrykologrivkogmail.todolist.data;

import rx.Observable;
import rx.Scheduler;

public class AsyncTransformer<T> implements Observable.Transformer<T, T> {

    private final Scheduler mUiThread;
    private final Scheduler mIoThread;

    public AsyncTransformer(Scheduler uiThread, Scheduler ioThread) {
        mUiThread = uiThread;
        mIoThread = ioThread;
    }

    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable
                .subscribeOn(mIoThread)
                .observeOn(mUiThread);
    }
}