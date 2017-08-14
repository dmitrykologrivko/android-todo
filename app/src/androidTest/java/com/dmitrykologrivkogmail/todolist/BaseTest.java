package com.dmitrykologrivkogmail.todolist;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.dmitrykologrivkogmail.todolist.di.component.TestComponent;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
@Ignore
public class BaseTest {

    protected TestComponent mComponent;

    @Before
    public void setUp() throws Exception {
        mComponent = (TestComponent) TestApplication.getApplicationComponent();
    }

    @After
    public void tearDown() throws Exception {

    }
}
