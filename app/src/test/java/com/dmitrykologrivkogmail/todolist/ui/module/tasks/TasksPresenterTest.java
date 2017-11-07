package com.dmitrykologrivkogmail.todolist.ui.module.tasks;

import com.dmitrykologrivkogmail.todolist.BaseTest;
import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.AuthorizationManager;
import com.dmitrykologrivkogmail.todolist.data.DataManager;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.stubs.TasksFactory;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import javax.inject.Inject;

import edu.emory.mathcs.backport.java.util.Collections;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TasksPresenterTest extends BaseTest {

    private static final int TASKS_SIZE = 2;
    private static final int TASK_ID = 1;

    @Inject
    CompositeSubscription mSubscription;

    @Inject
    AuthorizationManager mAuthManager;

    @Inject
    DataManager mDataManager;

    @Inject
    TasksView mView;

    private TasksPresenter mPresenter;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mComponent.plusTasksTestComponent().inject(this);
        mPresenter = new TasksPresenter(mSubscription, mDataManager, mAuthManager);
        mPresenter.attachView(mView);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        mPresenter.detachView(false);
    }

    @Test
    public void testGetTasks_shouldPassTasksToView() {
        List<Task> tasks = TasksFactory.makeListTasks(TASKS_SIZE);

        when(mDataManager.getTasks())
                .thenReturn(Observable.just(tasks));

        mPresenter.getTasks();

        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).showTasks(tasks);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testGetTasks_shouldHandleEmptyOrders() {
        when(mDataManager.getTasks())
                .thenReturn(Observable.<List<Task>>just(Collections.emptyList()));

        mPresenter.getTasks();

        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).showTasksEmpty();

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testGetTasks_shouldHandleError() {
        when(mDataManager.getTasks())
                .thenReturn(Observable.<List<Task>>error(new RuntimeException()));

        mPresenter.getTasks();

        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).showError(ArgumentMatchers.any(Throwable.class));

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testCreateTask_shouldCreateTaskAndPassToView() {
        final Task task = TasksFactory.makeTask(TASK_ID);

        when(mDataManager.createTask(ArgumentMatchers.any(Task.class)))
                .thenAnswer(new Answer<Observable<Task>>() {
                    @Override
                    public Observable<Task> answer(InvocationOnMock invocation) throws Throwable {
                        Task creatable = (Task) invocation.getArguments()[0];

                        assertEquals(task.getDescription(), creatable.getDescription());
                        assertEquals(false, creatable.isDone());

                        return Observable.just(task);
                    }
                });

        when(mView.getDescription())
                .thenReturn(task.getDescription());

        mPresenter.createTask();

        verify(mDataManager).createTask(ArgumentMatchers.any(Task.class));

        verify(mView).getDescription();
        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).clearDescription();
        verify(mView).addTask(task);
        verify(mView).sortTasks();

        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testCreateTask_whenDescriptionIsEmpty_shouldShowError() {
        when(mView.getDescription()).thenReturn("");

        mPresenter.createTask();

        verify(mView).getDescription();
        verify(mView).showError(R.string.tasks_empty_description_error);

        verify(mDataManager, never()).createTask(ArgumentMatchers.any(Task.class));

        verify(mView, never()).clearDescription();
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testCreateTask_shouldHandleError() {
        when(mDataManager.createTask(ArgumentMatchers.any(Task.class)))
                .thenReturn(Observable.<Task>error(new RuntimeException()));

        when(mView.getDescription())
                .thenReturn("Description");

        mPresenter.createTask();

        verify(mView).getDescription();
        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).showError(ArgumentMatchers.any(Throwable.class));

        verify(mView, never()).clearDescription();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testEditTask_shouldEditTaskAndPassToView() {
        Task task = TasksFactory.makeTask(TASK_ID);

        when(mDataManager.editTask(task))
                .thenReturn(Observable.just(task));

        mPresenter.editTask(task, task.getDescription());

        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).updateTask(task);

        verify(mDataManager).editTask(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testEditTask_whenDescriptionIsEmpty_shouldShowError() {
        Task task = TasksFactory.makeTask(TASK_ID);

        mPresenter.editTask(task, "");

        verify(mView).showError(R.string.tasks_empty_description_error);

        verify(mDataManager, never()).editTask(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testEditTask_shouldHandleError() {
        Task task = TasksFactory.makeTask(TASK_ID);

        when(mDataManager.editTask(task))
                .thenReturn(Observable.<Task>error(new RuntimeException()));

        mPresenter.editTask(task, task.getDescription());

        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).showError(ArgumentMatchers.any(Throwable.class));

        verify(mDataManager).editTask(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testMarkTask_shouldMarkTaskAndPassToView() {
        final Task task = TasksFactory.makeTask(TASK_ID);
        final boolean expectedMark = !task.isDone();

        when(mDataManager.markTask(task))
                .thenAnswer(new Answer<Observable<Task>>() {
                    @Override
                    public Observable<Task> answer(InvocationOnMock invocation) throws Throwable {
                        Task creatable = (Task) invocation.getArguments()[0];

                        assertEquals(expectedMark, creatable.isDone());

                        return Observable.just(task);
                    }
                });

        mPresenter.markTask(task);

        verify(mView).updateTask(task);
        verify(mView).sortTasks();

        verify(mDataManager).markTask(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testMarkTask_shouldHandleError() {
        Task task = TasksFactory.makeTask(TASK_ID);

        when(mDataManager.markTask(task))
                .thenReturn(Observable.<Task>error(new RuntimeException()));

        mPresenter.markTask(task);

        verify(mView).showError(ArgumentMatchers.any(Throwable.class));

        verify(mDataManager).markTask(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testDeleteTask_shouldDeleteTask() {
        Task task = TasksFactory.makeTask(TASK_ID);

        when(mDataManager.deleteTask(task))
                .thenReturn(Observable.just(task));

        mPresenter.deleteTask(task);

        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).removeTask(task);

        verify(mDataManager).deleteTask(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testDeleteTask_shouldHandleError() {
        Task task = TasksFactory.makeTask(TASK_ID);

        when(mDataManager.deleteTask(task))
                .thenReturn(Observable.<Task>error(new RuntimeException()));

        mPresenter.deleteTask(task);

        verify(mView).showProgress();
        verify(mView).dismissProgress();
        verify(mView).showError(ArgumentMatchers.any(Throwable.class));

        verify(mDataManager).deleteTask(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testSignOut_shouldSignOutAndFinishView() {
        when(mAuthManager.signOut())
                .thenReturn(Observable.<Void>empty());

        mPresenter.signOut();

        verify(mView).runSignInActivity();
        verify(mView).finish();

        verify(mAuthManager).signOut();

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
    }

    @Test
    public void testOnTaskClick_shouldShowEditDialog() {
        Task task = TasksFactory.makeTask(TASK_ID);

        mPresenter.onTaskClick(task);

        verify(mView).showEditDialog(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

    @Test
    public void testOnDeleteButtonClick_showShowDeleteDialog() {
        Task task = TasksFactory.makeTask(TASK_ID);

        mPresenter.onDeleteButtonClick(task);

        verify(mView).showDeleteDialog(task);

        verify(mView, never()).getDescription();
        verify(mView, never()).clearDescription();
        verify(mView, never()).showProgress();
        verify(mView, never()).dismissProgress();
        verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
        verify(mView, never()).sortTasks();
        verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
        verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
        verify(mView, never()).showError(ArgumentMatchers.anyString());
        verify(mView, never()).showError(ArgumentMatchers.anyInt());
        verify(mView, never()).showTasksEmpty();
        verify(mView, never()).runSignInActivity();
        verify(mView, never()).finish();
    }

//    verify(mView, never()).getDescription();
//    verify(mView, never()).clearDescription();
//    verify(mView, never()).showProgress();
//    verify(mView, never()).dismissProgress();
//    verify(mView, never()).showTasks(ArgumentMatchers.<Task>anyList());
//    verify(mView, never()).sortTasks();
//    verify(mView, never()).addTask(ArgumentMatchers.any(Task.class));
//    verify(mView, never()).updateTask(ArgumentMatchers.any(Task.class));
//    verify(mView, never()).removeTask(ArgumentMatchers.any(Task.class));
//    verify(mView, never()).showEditDialog(ArgumentMatchers.any(Task.class));
//    verify(mView, never()).showDeleteDialog(ArgumentMatchers.any(Task.class));
//    verify(mView, never()).showError(ArgumentMatchers.any(Throwable.class));
//    verify(mView, never()).showError(ArgumentMatchers.anyString());
//    verify(mView, never()).showError(ArgumentMatchers.anyInt());
//    verify(mView, never()).showTasksEmpty();
//    verify(mView, never()).runSignInActivity();
//    verify(mView, never()).finish();
}
