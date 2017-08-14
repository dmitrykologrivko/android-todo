package com.dmitrykologrivkogmail.todolist.data.api.oauth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.common.constant.config.ApiConfig;
import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelper;
import com.dmitrykologrivkogmail.todolist.stubs.TokenDTOFactory;

import org.junit.Test;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class OAuthInterceptorTest extends BaseTest {

    @Inject
    CredentialsHelper mHelper;

    private OAuthInterceptor mInterceptor;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mComponent.inject(this);
        mInterceptor = new OAuthInterceptor(mHelper);
    }

    @Test
    public void testIntercept_shouldReturnResponseWithTokenHeader() throws Exception {
        TokenDTO stub = TokenDTOFactory.createStub();

        when(mHelper.getTokenType())
                .thenReturn(Observable.just(stub.getTokenType()));
        when(mHelper.getAccessToken())
                .thenReturn(Observable.just(stub.getAccessToken()));

        Response response = mInterceptor.intercept(getChain());

        verify(mHelper).getTokenType();
        verify(mHelper).getAccessToken();

        String expected =
                String.format("%s:%s", stub.getTokenType(), stub.getAccessToken());
        String result =
                response.request().header(OAuthInterceptor.HEADER_AUTHORIZATION);

        assertEquals(expected, result);
    }

    private Interceptor.Chain getChain() {
        return new Interceptor.Chain() {
            @Override
            public Request request() {
                return new Request.Builder()
                        .url(ApiConfig.BACKEND_URL)
                        .get()
                        .build();
            }

            @Override
            public Response proceed(@NonNull Request request) throws IOException {
                return new Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("{}")
                        .build();
            }

            @Nullable
            @Override
            public Connection connection() {
                return null;
            }
        };
    }
}
