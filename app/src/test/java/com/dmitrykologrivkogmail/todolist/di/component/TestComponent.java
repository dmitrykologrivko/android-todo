package com.dmitrykologrivkogmail.todolist.di.component;

import com.dmitrykologrivkogmail.todolist.data.AuthorizationManagerTest;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthAuthenticatorTest;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthClientTest;
import com.dmitrykologrivkogmail.todolist.data.api.oauth.OAuthInterceptorTest;
import com.dmitrykologrivkogmail.todolist.data.preferences.CredentialsHelperTest;
import com.dmitrykologrivkogmail.todolist.di.modules.ApplicationTestModule;
import com.dmitrykologrivkogmail.todolist.di.modules.DataTestModule;
import com.dmitrykologrivkogmail.todolist.di.scope.PerApplication;

import dagger.Component;

@PerApplication
@Component(modules = {ApplicationTestModule.class, DataTestModule.class})
public interface TestComponent extends ApplicationComponent {

    void inject(AuthorizationManagerTest test);

    void inject(CredentialsHelperTest test);

    void inject(OAuthClientTest test);

    void inject(OAuthAuthenticatorTest test);

    void inject(OAuthInterceptorTest test);

    SignInTestComponent plusSignInTestComponent();

    SplashTestComponent plusSplashTestComponent();

    TasksTestComponent plusTasksTestComponent();

}
