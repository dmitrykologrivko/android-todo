package com.dmitrykologrivkogmail.todolist.data.mappers;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.injection.scope.PerApplication;

import javax.inject.Inject;

import rx.functions.Func1;

@PerApplication
public class TaskMapper implements Func1<TaskDTO, Task> {

    @Inject
    public TaskMapper() {
    }

    @Override
    public Task call(TaskDTO dto) {
        if (dto == null) {
            return null;
        }
        return new Task.Builder()
                .id(dto.getId())
                .description(dto.getDescription())
                .done(dto.isDone())
                .user(dto.getUser())
                .created(dto.getCreated())
                .updated(dto.getUpdated())
                .build();
    }
}
