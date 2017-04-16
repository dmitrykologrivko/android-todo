package com.dmitrykologrivkogmail.todolist.data.api.services;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import rx.Observable;

public interface TasksService {

    @GET("tasks")
    Observable<List<TaskDTO>> getTasks();

    @PATCH("tasks/{id}/")
    @FormUrlEncoded
    Observable<TaskDTO> markTask(@Path("id") long id,
                                 @Field("is_done") boolean isDone);

}
