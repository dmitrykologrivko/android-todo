package com.dmitrykologrivkogmail.todolist.data.mappers;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.stubs.TasksFactory;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class TasksListMapperTest extends BaseTest {

    private static final int TASKS_LIST_SIZE = 2;

    // dependencies
    private final TaskMapper mTaskMapper = new TaskMapper();

    private final TasksListMapper mMapper = new TasksListMapper(mTaskMapper);

    @Test
    public void testCall_shouldReturnMappedTasksList() {
        List<TaskDTO> dto = TasksFactory.Dto.makeListTasks(TASKS_LIST_SIZE);

        List<Task> tasks = mMapper.call(dto);

        assertEquals(dto.size(), tasks.size());

        for (int i = 0; i < tasks.size(); i++) {
            TaskDTO taskDto = dto.get(i);
            Task task = tasks.get(i);

            assertEquals(taskDto.getId(), task.getId());
            assertEquals(taskDto.getDescription(), task.getDescription());
            assertEquals(taskDto.isDone(), task.isDone());
            assertEquals(taskDto.getUser(), task.getUser());
            assertEquals(taskDto.getCreated(), task.getCreated());
            assertEquals(taskDto.getUpdated(), task.getUpdated());
        }
    }

}
