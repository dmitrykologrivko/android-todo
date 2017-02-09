package com.dmitrykologrivkogmail.todolist.data.api.oauth;

class OAuthError extends RuntimeException {

    OAuthError(String message) {
        super(message);
    }

    OAuthError(String message, Throwable cause) {
        super(message, cause);
    }
}
