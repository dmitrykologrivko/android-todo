package com.dmitrykologrivkogmail.todolist.presenters;

import android.content.Context;

import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.hannesdorfmann.mosby.mvp.MvpView;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<V extends MvpView> extends MvpBasePresenter<V> {

    final CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    final DataManager mDataManager;

    public BasePresenter(Context context) {
        mDataManager = new DataManager(context);
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        mCompositeSubscription.clear();
    }

    protected void addSubscription(Subscription subscription) {
        mCompositeSubscription.add(subscription);
    }

    protected void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    protected static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
