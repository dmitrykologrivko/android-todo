package com.dmitrykologrivkogmail.todolist;

import com.dmitrykologrivkogmail.todolist.di.component.ApplicationComponent;
import com.dmitrykologrivkogmail.todolist.di.component.DaggerTestComponent;
import com.dmitrykologrivkogmail.todolist.di.modules.ApplicationTestModule;

public class TestApplication extends TodoApplication {

    @Override
    protected ApplicationComponent buildComponent() {
        return DaggerTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(this))
                .build();
    }
}
