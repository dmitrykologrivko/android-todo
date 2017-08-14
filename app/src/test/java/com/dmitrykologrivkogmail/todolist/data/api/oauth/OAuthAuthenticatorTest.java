package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.common.constant.config.ApiConfig;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.stubs.TokenDTOFactory;

import org.junit.Test;

import javax.inject.Inject;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OAuthAuthenticatorTest extends BaseTest {

    @Inject
    OAuthClient mAuthClient;

    @Inject
    CredentialsHelper mHelper;

    private OAuthAuthenticator mAuthenticator;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mComponent.inject(this);
        mAuthenticator = new OAuthAuthenticator(mAuthClient, mHelper);
    }

    @Test
    public void testAuthenticate_shouldRefreshToken() throws Exception {
        TokenDTO stub = TokenDTOFactory.createStub();

        when(mHelper.getRefreshToken())
                .thenReturn(Observable.just(stub.getRefreshToken()));
        when(mHelper.save(stub))
                .thenReturn(Observable.just(stub));
        when(mAuthClient.refreshAccessToken(stub.getRefreshToken()))
                .thenReturn(Observable.just(stub));

        Request dummyRequest = new Request.Builder()
                .url(ApiConfig.BACKEND_URL)
                .get()
                .build();
        Response response = new Response.Builder()
                .request(dummyRequest)
                .protocol(Protocol.HTTP_1_1)
                .code(401)
                .message("{}")
                .build();

        Request authenticated = mAuthenticator.authenticate(null, response);

        verify(mHelper).getRefreshToken();
        verify(mHelper).save(stub);
        verify(mAuthClient).refreshAccessToken(stub.getRefreshToken());

        String expected =
                String.format("%s:%s", stub.getTokenType(), stub.getAccessToken());
        String result =
                authenticated.header(OAuthAuthenticator.HEADER_AUTHORIZATION);

        assertEquals(expected, result);
    }
}
