package com.dmitrykologrivkogmail.todolist.data.preferences;

import android.content.SharedPreferences;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.stubs.TokenDTOFactory;

import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import rx.observers.TestSubscriber;

import static com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper.PREF_ACCESS_TOKEN;
import static com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper.PREF_EXPIRES_IN;
import static com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper.PREF_REFRESH_TOKEN;
import static com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper.PREF_SCOPE;
import static com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper.PREF_TOKEN_TYPE;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CredentialsHelperTest extends BaseTest {

    @Inject
    SharedPreferences mPref;

    @Inject
    SharedPreferences.Editor mEditor;

    private CredentialsHelper mHelper;

    private TokenDTO mStub;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mComponent.inject(this);
        mHelper = new CredentialsHelper(mPref);
        mStub = TokenDTOFactory.createStub();

        when(mPref.edit()).thenReturn(mEditor);
        when(mEditor.clear()).thenReturn(mEditor);
    }

    @Test
    public void testGetAccessToken_shouldReturnAccessToken() {
        when(mPref.getString(PREF_ACCESS_TOKEN, "")).thenReturn(mStub.getAccessToken());

        TestSubscriber<String> result = new TestSubscriber<>();
        mHelper.getAccessToken().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(mStub.getAccessToken());

        verify(mPref).getString(PREF_ACCESS_TOKEN, "");
    }

    @Test
    public void testGetRefreshToken_shouldReturnRefreshToken() {
        when(mPref.getString(PREF_REFRESH_TOKEN, "")).thenReturn(mStub.getRefreshToken());

        TestSubscriber<String> result = new TestSubscriber<>();
        mHelper.getRefreshToken().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(mStub.getRefreshToken());

        verify(mPref).getString(PREF_REFRESH_TOKEN, "");
    }

    @Test
    public void testGetExpiresIn_shouldReturnExpiresIn() {
        when(mPref.getInt(PREF_EXPIRES_IN, 0)).thenReturn(mStub.getExpiresIn());

        TestSubscriber<Integer> result = new TestSubscriber<>();
        mHelper.getExpiresIn().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(mStub.getExpiresIn());

        verify(mPref).getInt(PREF_EXPIRES_IN, 0);
    }

    @Test
    public void testGetScope_shouldReturnScope() {
        when(mPref.getString(PREF_SCOPE, "")).thenReturn(mStub.getScope());

        TestSubscriber<String> result = new TestSubscriber<>();
        mHelper.getScope().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(mStub.getScope());

        verify(mPref).getString(PREF_SCOPE, "");
    }

    @Test
    public void testGetTokenType_shouldReturnTokenType() {
        when(mPref.getString(PREF_TOKEN_TYPE, "")).thenReturn(mStub.getTokenType());

        TestSubscriber<String> result = new TestSubscriber<>();
        mHelper.getTokenType().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(mStub.getTokenType());

        verify(mPref).getString(PREF_TOKEN_TYPE, "");
    }

    @Test
    public void testSave_shouldSaveToken() {
        when(mEditor.putString(PREF_ACCESS_TOKEN, mStub.getAccessToken()))
                .thenReturn(mEditor);
        when(mEditor.putString(PREF_REFRESH_TOKEN, mStub.getRefreshToken()))
                .thenReturn(mEditor);
        when(mEditor.putString(PREF_SCOPE, mStub.getScope()))
                .thenReturn(mEditor);
        when(mEditor.putString(PREF_TOKEN_TYPE, mStub.getTokenType()))
                .thenReturn(mEditor);
        when(mEditor.putInt(PREF_EXPIRES_IN, mStub.getExpiresIn()))
                .thenReturn(mEditor);

        TestSubscriber<TokenDTO> result = new TestSubscriber<>();
        mHelper.save(mStub).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(mStub);

        verify(mPref).edit();
        verify(mEditor).putString(PREF_ACCESS_TOKEN, mStub.getAccessToken());
        verify(mEditor).putString(PREF_REFRESH_TOKEN, mStub.getRefreshToken());
        verify(mEditor).putString(PREF_SCOPE, mStub.getScope());
        verify(mEditor).putString(PREF_TOKEN_TYPE, mStub.getTokenType());
        verify(mEditor).putInt(PREF_EXPIRES_IN, mStub.getExpiresIn());
        verify(mEditor).apply();
    }

    @Test
    public void testClear_shouldClearCredentials() {
        TestSubscriber<Void> result = new TestSubscriber<>();
        mHelper.clear().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(null);

        verify(mPref).edit();
        verify(mEditor).clear();
        verify(mEditor).apply();
    }
}
