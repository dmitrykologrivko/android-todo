package com.dmitrykologrivkogmail.todolist.stubs;

import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.data.models.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TasksFactory {

    public static Task makeTask(long id) {
        return new Task.Builder()
                .id(id)
                .description(String.format("%s%d", "TODO #", id))
                .done(false)
                .user(2)
                .created(new Date())
                .updated(new Date())
                .build();
    }

    public static List<Task> makeListTasks(int size) {
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            tasks.add(makeTask(i));
        }
        return tasks;
    }

    public static class Dto {

        public static TaskDTO makeTask(long id) {
            TaskDTO task = new TaskDTO();
            task.setId(id);
            task.setDescription(String.format("%s%d", "TODO #", id));
            task.setDone(false);
            task.setUser(2);
            task.setCreated(new Date());
            task.setUpdated(new Date());
            return task;
        }

        public static List<TaskDTO> makeListTasks(int size) {
            List<TaskDTO> tasks = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                makeTask(i);
            }
            return tasks;
        }
    }
}
