package com.dmitrykologrivkogmail.todolist.ui.module.signin;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthError;
import com.dmitrykologrivkogmail.todolist.stubs.TokenDTOFactory;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import javax.inject.Inject;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SignInPresenterTest extends BaseTest {

    @Inject
    CompositeSubscription mSubscription;

    @Inject
    AuthorizationManager mManager;

    @Inject
    SignInView mView;

    private SignInPresenter mPresenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mComponent.plusSignInTestComponent().inject(this);
        mPresenter = new SignInPresenter(mSubscription, mManager);
        mPresenter.attachView(mView);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mPresenter.detachView(false);
    }

    @Test
    public void testSignIn_whenCredentialsAreNull_shouldHandleValidationError() {
        String username = null;
        String password = null;

        when(mView.getEditUsername()).thenReturn(username);
        when(mView.getEditPassword()).thenReturn(password);

        mPresenter.signIn();

        verify(mView).getEditUsername();
        verify(mView).getEditPassword();
        verify(mView).showError(R.string.sign_in_empty_email_error);

        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).startTasksActivity();
        verify(mView, never()).finish();

        verify(mManager, never()).signIn(username, password);
    }

    @Test
    public void testSignIn_whenCredentialsAreEmpty_shouldHandleValidationError() {
        String username = "";
        String password = "";

        when(mView.getEditUsername()).thenReturn(username);
        when(mView.getEditPassword()).thenReturn(password);

        mPresenter.signIn();

        verify(mView).getEditUsername();
        verify(mView).getEditPassword();
        verify(mView).showError(R.string.sign_in_empty_email_error);

        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).startTasksActivity();
        verify(mView, never()).finish();

        verify(mManager, never()).signIn(username, password);
    }

    @Test
    public void testSignIn_whenPasswordIsNull_shouldHandleValidationError() {
        String username = "admin";
        String password = null;

        when(mView.getEditUsername()).thenReturn(username);
        when(mView.getEditPassword()).thenReturn(password);

        mPresenter.signIn();

        verify(mView).getEditUsername();
        verify(mView).getEditPassword();
        verify(mView).showError(R.string.sign_in_empty_password_error);

        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).startTasksActivity();
        verify(mView, never()).finish();

        verify(mManager, never()).signIn(username, password);
    }

    @Test
    public void testSignIn_whenPasswordIsEmpty_shouldHandleValidationError() {
        String username = "admin";
        String password = "";

        when(mView.getEditUsername()).thenReturn(username);
        when(mView.getEditPassword()).thenReturn(password);

        mPresenter.signIn();

        verify(mView).getEditUsername();
        verify(mView).getEditPassword();
        verify(mView).showError(R.string.sign_in_empty_password_error);

        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).startTasksActivity();
        verify(mView, never()).finish();

        verify(mManager, never()).signIn(username, password);
    }

    @Test
    public void testSignIn_shouldNavigateToMainView() {
        String username = "admin";
        String password = "12345678";

        TokenDTO stub = TokenDTOFactory.createStub();

        when(mView.getEditUsername()).thenReturn(username);
        when(mView.getEditPassword()).thenReturn(password);
        when(mManager.signIn(username, password)).thenReturn(Observable.just(stub));

        mPresenter.signIn();

        verify(mView).getEditUsername();
        verify(mView).getEditPassword();
        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).startTasksActivity();
        verify(mView).finish();

        verify(mManager).signIn(username, password);

        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showError(ArgumentMatchers.anyString());
    }

    @Test
    public void testSignIn_shouldHandleError() {
        String username = "admin";
        String password = "12345678";

        when(mView.getEditUsername()).thenReturn(username);
        when(mView.getEditPassword()).thenReturn(password);
        when(mManager.signIn(username, password))
                .thenReturn(Observable.<TokenDTO>error(new OAuthError()));

        mPresenter.signIn();

        verify(mView).getEditUsername();
        verify(mView).getEditPassword();
        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).showError(ArgumentMatchers.any(Throwable.class));

        verify(mManager).signIn(username, password);

        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).startTasksActivity();
        verify(mView, never()).finish();
    }
}
