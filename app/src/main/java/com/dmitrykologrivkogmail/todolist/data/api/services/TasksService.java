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
import retrofit2.http.Query;
import rx.Observable;

public interface TasksService {

    // URLs
    String TASKS_LIST_URL = "api/tasks/";
    String TASKS_DETAIL_URL = "api/tasks/{id}/";

    // Fields
    String ID = "id";
    String DESCRIPTION = "description";
    String IS_DONE = "is_done";

    // Params
    String ORDERING = "ordering";

    @GET(TASKS_LIST_URL)
    Observable<List<TaskDTO>> getTasks(@Query(ORDERING) String ordering);

    @POST(TASKS_LIST_URL)
    @FormUrlEncoded
    Observable<TaskDTO> createTask(@Field(DESCRIPTION) String description);

    @PATCH(TASKS_DETAIL_URL)
    @FormUrlEncoded
    Observable<TaskDTO> editTask(@Path(ID) long id,
                                 @Field(DESCRIPTION) String description);

    @PATCH(TASKS_DETAIL_URL)
    @FormUrlEncoded
    Observable<TaskDTO> markTask(@Path(ID) long id,
                                 @Field(IS_DONE) boolean isDone);

    @DELETE(TASKS_DETAIL_URL)
    Observable<Void> deleteTask(@Path(ID) long id);

}
