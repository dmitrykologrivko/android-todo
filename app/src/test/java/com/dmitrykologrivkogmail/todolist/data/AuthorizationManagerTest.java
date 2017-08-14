package com.dmitrykologrivkogmail.todolist.data;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthClient;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthError;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.di.IoScheduler;
import com.dmitrykologrivkogmail.todolist.di.UiScheduler;
import com.dmitrykologrivkogmail.todolist.stubs.TokenDTOFactory;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthorizationManagerTest extends BaseTest {

    @Inject
    @UiScheduler
    Scheduler mUiScheduler;

    @Inject
    @IoScheduler
    Scheduler mIoScheduler;

    @Inject
    OAuthClient mClient;

    @Inject
    CredentialsHelper mHelper;

    private AuthorizationManager mManager;

    private TokenDTO mStub;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mComponent.inject(this);
        mManager = new AuthorizationManager(mUiScheduler,
                mIoScheduler,
                mClient,
                mHelper);
        mStub = TokenDTOFactory.createStub();
    }

    @Test
    public void testSignIn_whenCredentialsAreCorrect_shouldFetchAndSaveToken() {
        String username = "admin";
        String password = "12345678";

        when(mClient.requestAccessToken(username, password))
                .thenReturn(Observable.just(mStub));
        when(mHelper.save(mStub))
                .thenReturn(Observable.just(mStub));

        TestSubscriber<TokenDTO> result = new TestSubscriber<>();
        mManager.signIn(username, password).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(mStub);

        verify(mClient).requestAccessToken(username, password);
        verify(mHelper).save(mStub);
    }

    @Test
    public void testSignIn_whenCredentialsAreWrong_shouldContainsException() {
        String username = "admin";
        String password = "12345678";

        when(mClient.requestAccessToken(username, password))
                .thenReturn(Observable.<TokenDTO>error(new OAuthError()));

        TestSubscriber<TokenDTO> result = new TestSubscriber<>();
        mManager.signIn(username, password).subscribe(result);

        result.assertError(OAuthError.class);

        verify(mClient).requestAccessToken(username, password);
        verify(mHelper, never()).save(mStub);
    }

    @Test
    public void testSignOut_shouldSignOut() {
        when(mHelper.clear()).thenReturn(Observable.<Void>empty());

        TestSubscriber<Void> result = new TestSubscriber<>();
        mManager.signOut().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();

        verify(mHelper).clear();
    }

    @Test
    public void testIsAuthenticated_whenTokenSaved_shouldReturnTrue() {
        when(mHelper.getAccessToken())
                .thenReturn(Observable.just(mStub.getAccessToken()));
        when(mHelper.getRefreshToken())
                .thenReturn(Observable.just(mStub.getRefreshToken()));

        TestSubscriber<Boolean> result = new TestSubscriber<>();
        mManager.isAuthenticated().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(true);
    }

    @Test
    public void testIsAuthenticated_whenTokenNotSaved_shouldReturnFalse() {
        when(mHelper.getAccessToken()).thenReturn(Observable.just(""));
        when(mHelper.getRefreshToken()).thenReturn(Observable.just(""));

        TestSubscriber<Boolean> result = new TestSubscriber<>();
        mManager.isAuthenticated().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(false);
    }
}
