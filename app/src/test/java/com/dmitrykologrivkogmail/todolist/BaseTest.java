package com.dmitrykologrivkogmail.todolist;

import com.dmitrykologrivkogmail.todolist.di.component.TestComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 23)
@Ignore
public class BaseTest {

    protected TestComponent mComponent;
    protected TestUtils mUtils;

    @Before
    public void setUp() throws Exception {
        mComponent = (TestComponent) TodoApplication.getApplicationComponent();
        mUtils = new TestUtils();
    }

    @After
    public void tearDown() throws Exception {

    }
}
