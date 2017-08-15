package com.dmitrykologrivkogmail.todolist.data.mappers;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.stubs.TasksFactory;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TaskMapperTest extends BaseTest {

    private static final int TASK_ID = 1;

    private final TaskMapper mMapper = new TaskMapper();

    @Test
    public void testCall_shouldReturnMappedTask() {
        TaskDTO dto = TasksFactory.Dto.makeTask(TASK_ID);

        Task task = mMapper.call(dto);

        assertEquals(dto.getId(), task.getId());
        assertEquals(dto.getDescription(), task.getDescription());
        assertEquals(dto.isDone(), task.isDone());
        assertEquals(dto.getUser(), task.getUser());
        assertEquals(dto.getCreated(), task.getCreated());
        assertEquals(dto.getUpdated(), task.getUpdated());
    }

}
