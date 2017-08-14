package com.dmitrykologrivkogmail.todolist.data.mappers;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.injection.scope.PerApplication;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

@PerApplication
public class TasksListMapper implements Func1<List<TaskDTO>, List<Task>> {

    private final TaskMapper mTaskMapper;

    @Inject
    public TasksListMapper(TaskMapper taskMapper) {
        mTaskMapper = taskMapper;
    }

    @Override
    public List<Task> call(List<TaskDTO> dto) {
        if (dto == null) {
            return null;
        }
        return Observable.from(dto)
                .map(mTaskMapper)
                .toList()
                .toBlocking()
                .first();
    }
}
