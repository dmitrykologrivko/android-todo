package com.dmitrykologrivkogmail.todolist.ui.module.splash;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;

import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SplashPresenterTest extends BaseTest {

    @Inject
    CompositeSubscription mSubscription;

    @Inject
    AuthorizationManager mManager;

    @Inject
    SplashView mView;

    private SplashPresenter mPresenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mComponent.plusSplashTestComponent().inject(this);
        mPresenter = new SplashPresenter(mSubscription, mManager);
        mPresenter.attachView(mView);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mPresenter.detachView(false);
    }

    @Test
    public void testIsAuthenticated_whenResultIsTrue_shouldNavigateToTasksView() {
        when(mManager.isAuthenticated()).thenReturn(Observable.just(true));

        mPresenter.isAuthenticated();

        verify(mView).startTasksActivity();
        verify(mView).finish();
    }

    @Test
    public void testIsAuthenticated_whenResultIsFalse_shouldNavigateToSignInView() {
        when(mManager.isAuthenticated()).thenReturn(Observable.just(false));

        mPresenter.isAuthenticated();

        verify(mView).startSignInActivity();
        verify(mView).finish();
    }
}
