package com.dmitrykologrivkogmail.todolist.stubs;

import com.dmitrykologrivkogmail.todolist.data.api.models.TokenDTO;

public class TokenDTOFactory {

    public static TokenDTO createStub(String accessToken,
                                      String scope,
                                      int expiresIn,
                                      String tokenType,
                                      String refreshToken) {

        TokenDTO token = new TokenDTO();
        token.setAccessToken(accessToken);
        token.setScope(scope);
        token.setExpiresIn(expiresIn);
        token.setTokenType(tokenType);
        token.setRefreshToken(refreshToken);
        return token;
    }

    public static TokenDTO createStub() {
        return createStub("KfAnHpqFI45fxsyDtdB2iRYOQUY9i7",
                "read write",
                36000,
                "bearer",
                "b2zjSATlwUymlDXv3CVMVwDiltWLxF");
    }
}