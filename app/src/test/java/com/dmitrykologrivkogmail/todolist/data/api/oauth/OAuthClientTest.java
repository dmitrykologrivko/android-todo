package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.common.constant.Injection;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.stubs.TokenDTOFactory;
import com.google.gson.Gson;
import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.OkHttpClient;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;

public class OAuthClientTest extends BaseTest {

    @Inject
    OkHttpClient mClient;

    @Inject
    MockWebServer mMockWebServer;

    @Inject
    Gson mGson;

    @Inject
    @Named(Injection.OAUTH_CLIENT_ID)
    String mClientId;

    @Inject
    @Named(Injection.OAUTH_CLIENT_SECRET)
    String mClientSecret;

    private OAuthClient mAuthClient;

    private TokenDTO mStub;

    private String mMockResponse;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        mComponent.inject(this);

        mMockWebServer.play();

        mAuthClient = new OAuthClient(mClient,
                mGson,
                mMockWebServer.getUrl("/"),
                mClientId,
                mClientSecret);

        mStub = TokenDTOFactory.createStub();

        mMockResponse = mUtils.getGson().toJson(mStub);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        mMockWebServer.shutdown();
    }

    @Test
    public void testRequestAccessToken_whenCredentialsAreCorrect_shouldReturnToken() {
        mMockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mMockResponse));

        final String username = "admin";
        final String password = "12345678";

        TestSubscriber<TokenDTO> result = new TestSubscriber<>();
        mAuthClient.requestAccessToken(username, password).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();

        TokenDTO token = result.getOnNextEvents().get(0);

        assertEquals(mStub.getAccessToken(), token.getAccessToken());
        assertEquals(mStub.getExpiresIn(), token.getExpiresIn());
        assertEquals(mStub.getTokenType(), token.getTokenType());
        assertEquals(mStub.getScope(), token.getScope());
        assertEquals(mStub.getRefreshToken(), token.getRefreshToken());
    }

    @Test
    public void testRequestAccessToken_whenCredentialsAreWrong_shouldThrowException() {
        mMockWebServer.enqueue(new MockResponse().setResponseCode(401));

        final String username = "admin";
        final String password = "12345678";

        TestSubscriber<TokenDTO> result = new TestSubscriber<>();
        mAuthClient.requestAccessToken(username, password).subscribe(result);

        result.assertError(OAuthError.class);
    }

    @Test
    public void testRefreshAccessToken_whenRefreshTokenIsCorrect_shouldReturnToken() {
        mMockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(mMockResponse));

        TestSubscriber<TokenDTO> result = new TestSubscriber<>();
        mAuthClient.refreshAccessToken(mStub.getRefreshToken()).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();

        TokenDTO token = result.getOnNextEvents().get(0);

        assertEquals(mStub.getAccessToken(), token.getAccessToken());
        assertEquals(mStub.getExpiresIn(), token.getExpiresIn());
        assertEquals(mStub.getTokenType(), token.getTokenType());
        assertEquals(mStub.getScope(), token.getScope());
        assertEquals(mStub.getRefreshToken(), token.getRefreshToken());
    }

    @Test
    public void testRefreshAccessToken_whenRefreshTokenIsWrong_shouldThrowException() {
        mMockWebServer.enqueue(new MockResponse().setResponseCode(401));

        TestSubscriber<TokenDTO> result = new TestSubscriber<>();
        mAuthClient.refreshAccessToken(mStub.getRefreshToken()).subscribe(result);

        result.assertError(OAuthError.class);
    }
}
