package com.dmitrykologrivkogmail.todolist.data;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.data.api.services.TasksService;
import com.dmitrykologrivkogmail.todolist.data.mappers.TaskMapper;
import com.dmitrykologrivkogmail.todolist.data.mappers.TasksListMapper;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.di.IoScheduler;
import com.dmitrykologrivkogmail.todolist.di.UiScheduler;
import com.dmitrykologrivkogmail.todolist.stubs.TasksFactory;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Scheduler;
import rx.observers.TestSubscriber;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DataManagerTest extends BaseTest {

    private static final int TASKS_LIST_SIZE = 2;
    private static final int TASK_ID = 1;

    @Inject
    @UiScheduler
    Scheduler mUiScheduler;

    @Inject
    @IoScheduler
    Scheduler mIoScheduler;

    @Inject
    TasksService mTasksService;

    @Inject
    TaskMapper mTaskMapper;

    @Inject
    TasksListMapper mTasksListMapper;

    private DataManager mManager;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mComponent.inject(this);
        mManager = new DataManager(mUiScheduler,
                mIoScheduler,
                mTasksService,
                mTaskMapper,
                mTasksListMapper);
    }

    @Test
    public void testGetTasks_shouldFetchAndMapTasks() {
        String ordering = Task.Ordering.IS_DONE.toString();

        List<TaskDTO> dto =
                TasksFactory.Dto.makeListTasks(TASKS_LIST_SIZE);
        List<Task> tasks =
                TasksFactory.makeListTasks(TASKS_LIST_SIZE);

        when(mTasksService.getTasks(ordering))
                .thenReturn(Observable.just(dto));
        when(mTasksListMapper.call(dto))
                .thenReturn(tasks);

        TestSubscriber<List<Task>> result = new TestSubscriber<>();
        mManager.getTasks().subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();

        assertEquals(tasks, result.getOnNextEvents().get(0));

        verify(mTasksService).getTasks(ordering);
        verify(mTasksListMapper).call(dto);
    }

    @Test
    public void testGetTasks_shouldContainsException() {
        String ordering = Task.Ordering.IS_DONE.toString();

        when(mTasksService.getTasks(ordering))
                .thenReturn(Observable.<List<TaskDTO>>error(new IOException()));

        TestSubscriber<List<Task>> result = new TestSubscriber<>();
        mManager.getTasks().subscribe(result);

        result.assertError(IOException.class);
    }

    @Test
    public void testCreateTask_shouldCreateAndMapTask() {
        TaskDTO dto =
                TasksFactory.Dto.makeTask(TASK_ID);
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.createTask(task.getDescription()))
                .thenReturn(Observable.just(dto));
        when(mTaskMapper.call(dto))
                .thenReturn(task);

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.createTask(task).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(task);

        verify(mTasksService).createTask(task.getDescription());
        verify(mTaskMapper).call(dto);
    }

    @Test
    public void testCreateTask_shouldContainsException() {
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.createTask(task.getDescription()))
                .thenReturn(Observable.<TaskDTO>error(new IOException()));

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.createTask(task).subscribe(result);

        result.assertError(IOException.class);
    }

    @Test
    public void testEditTask_shouldEditAndMapTask() {
        TaskDTO dto =
                TasksFactory.Dto.makeTask(TASK_ID);
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.editTask(task.getId(), task.getDescription()))
                .thenReturn(Observable.just(dto));
        when(mTaskMapper.call(dto))
                .thenReturn(task);

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.editTask(task).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(task);

        verify(mTasksService).editTask(task.getId(), task.getDescription());
        verify(mTaskMapper).call(dto);
    }

    @Test
    public void testEditTask_shouldContainsException() {
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.editTask(task.getId(), task.getDescription()))
                .thenReturn(Observable.<TaskDTO>error(new IOException()));

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.editTask(task).subscribe(result);

        result.assertError(IOException.class);
    }

    @Test
    public void testMarkTask_shouldMarkAndMapTask() {
        TaskDTO dto =
                TasksFactory.Dto.makeTask(TASK_ID);
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.markTask(task.getId(), task.isDone()))
                .thenReturn(Observable.just(dto));
        when(mTaskMapper.call(dto))
                .thenReturn(task);

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.markTask(task).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(task);

        verify(mTasksService).markTask(task.getId(), task.isDone());
        verify(mTaskMapper).call(dto);
    }

    @Test
    public void testMarkTask_shouldContainsError() {
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.markTask(task.getId(), task.isDone()))
                .thenReturn(Observable.<TaskDTO>error(new IOException()));

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.markTask(task).subscribe(result);

        result.assertError(IOException.class);
    }

    @Test
    public void testDeleteTask_shouldDeleteAndReturnTask() {
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.deleteTask(task.getId()))
                .thenReturn(Observable.<Void>just(null));

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.deleteTask(task).subscribe(result);

        result.assertNoErrors();
        result.assertCompleted();
        result.assertValue(task);

        verify(mTasksService).deleteTask(task.getId());
    }

    @Test
    public void testDeleteTask_shouldContainsError() {
        Task task =
                TasksFactory.makeTask(TASK_ID);

        when(mTasksService.deleteTask(task.getId()))
                .thenReturn(Observable.<Void>error(new IOException()));

        TestSubscriber<Task> result = new TestSubscriber<>();
        mManager.deleteTask(task).subscribe(result);

        result.assertError(IOException.class);
    }
}
