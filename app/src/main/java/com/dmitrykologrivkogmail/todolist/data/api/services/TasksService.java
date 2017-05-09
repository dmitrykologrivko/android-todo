package com.dmitrykologrivkogmail.todolist.data.api.services;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;

import java.util.List;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface TasksService {

    @GET("tasks/")
    Observable<List<TaskDTO>> getTasks();

    @POST("tasks/")
    @FormUrlEncoded
    Observable<TaskDTO> createTask(@Field("description") String description);

    @PATCH("tasks/{id}/")
    @FormUrlEncoded
    Observable<TaskDTO> editTask(@Path("id") long id,
                                 @Field("description") String description);

    @PATCH("tasks/{id}/")
    @FormUrlEncoded
    Observable<TaskDTO> markTask(@Path("id") long id,
                                 @Field("is_done") boolean isDone);

    @DELETE("tasks/{id}/")
    Observable<Void> deleteTask(@Path("id") long id);

}
