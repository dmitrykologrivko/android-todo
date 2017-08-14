package com.dmitrykologrivkogmail.todolist.ui.tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.TodoApplication;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.injection.component.TasksComponent;
import com.dmitrykologrivkogmail.todolist.ui.base.BaseActivity;
import com.dmitrykologrivkogmail.todolist.ui.signin.SignInActivity;
import com.dmitrykologrivkogmail.todolist.util.DialogFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TasksActivity extends BaseActivity<TasksView, TasksPresenter> implements TasksView, SwipeRefreshLayout.OnRefreshListener {

    private static final int LAYOUT = R.layout.activity_tasks;

    private TasksComponent mTasksComponent =
            TodoApplication.getApplicationComponent().plusTasksComponent();

    @Inject
    TasksAdapter mTasksAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.edit_description)
    EditText mEditDescription;

    public static Intent getStartIntent(Context ctx) {
        return new Intent(ctx, TasksActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTasksComponent.inject(this);
        setContentView(LAYOUT);
        ButterKnife.bind(this);

        mToolbar.setTitle(R.string.app_name);
        mToolbar.inflateMenu(R.menu.menu_tasks);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_sign_out) {
                    getPresenter().signOut();
                    return true;
                }
                return false;
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(R.color.accent);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setAdapter(mTasksAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setNestedScrollingEnabled(false);

        mEditDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getPresenter().createTask();
                    return true;
                }
                return false;
            }
        });

        getPresenter().getTasks();
    }

    @NonNull
    @Override
    public TasksPresenter createPresenter() {
        return mTasksComponent.tasksPresenter();
    }

    @Override
    public String getDescription() {
        return mEditDescription.getText().toString();
    }

    @Override
    public void clearDescription() {
        mEditDescription.setText("");
    }

    @Override
    public void showProgress() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissProgress() {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
            return;
        }
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mTasksAdapter.setModels(tasks);
    }

    @Override
    public void sortTasks() {
        mTasksAdapter.sortTasks();
    }

    @Override
    public void addTask(Task task) {
        mTasksAdapter.addItem(task);
    }

    @Override
    public void updateTask(Task task) {
        mTasksAdapter.updateItem(task);
    }

    @Override
    public void removeTask(Task task) {
        mTasksAdapter.removeItem(task);
    }

    @Override
    public void showEditDialog(final Task task) {
        final LayoutInflater inflater = getLayoutInflater();
        final View editDialog = inflater.inflate(R.layout.dialog_edit, null);

        final EditText editText = (EditText) editDialog.findViewById(R.id.edit_text);
        editText.setText(task.getDescription());
        editText.setSelection(editText.getText().length());

        final DialogInterface.OnClickListener neutralClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().onDeleteButtonClick(task);
            }
        };

        final DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().editTask(task, editText.getText().toString());
            }
        };

        DialogFactory.createViewDialog(this,
                editDialog,
                R.string.tasks_dialog_edit_title,
                R.string.dialog_action_delete,
                R.string.dialog_action_cancel,
                R.string.dialog_action_edit,
                neutralClick,
                null,
                positiveClick)
                .show();
    }

    @Override
    public void showDeleteDialog(final Task task) {
        final DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getPresenter().deleteTask(task);
            }
        };

        DialogFactory.createDialog(this,
                R.string.tasks_dialog_delete_title,
                R.string.tasks_dialog_delete_message,
                R.string.dialog_action_no,
                R.string.dialog_action_yes,
                null,
                positiveClick)
                .show();
    }

    @Override
    public void showError(String message) {
        DialogFactory.createSimpleOkErrorDialog(this,
                getString(R.string.dialog_error_title),
                message)
                .show();
    }

    @Override
    public void showError(int messageResource) {
        DialogFactory.createSimpleOkErrorDialog(this,
                R.string.dialog_error_title,
                messageResource)
                .show();
    }

    @Override
    public void showTasksEmpty() {
        mTasksAdapter.clear();

        Toast.makeText(this,
                R.string.msg_empty_list,
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void runSignInActivity() {
        startActivity(SignInActivity.getStartIntent(this));
    }

    @Override
    public void onRefresh() {
        getPresenter().getTasks();
    }
}
