package com.dmitrykologrivkogmail.todolist.data.api.services;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

public interface TasksService {

    @GET("tasks")
    Observable<List<TaskDTO>> getTasks();

}
