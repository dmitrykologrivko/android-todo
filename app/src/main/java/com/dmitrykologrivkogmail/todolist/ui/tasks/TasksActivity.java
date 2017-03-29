package com.dmitrykologrivkogmail.todolist.ui.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.TodoApplication;
import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.injection.component.TasksComponent;
import com.dmitrykologrivkogmail.todolist.ui.base.BaseActivity;
import com.dmitrykologrivkogmail.todolist.util.DialogFactory;

import java.util.Collections;
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

    @BindView(R.id.text_empty)
    TextView mTextEmpty;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

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

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setAdapter(mTasksAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getPresenter().getTasks();
    }

    @NonNull
    @Override
    public TasksPresenter createPresenter() {
        return mTasksComponent.tasksPresenter();
    }

    @Override
    public void showProgress() {
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mRecyclerView.setVisibility(View.GONE);
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
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTasks(List<TaskDTO> tasks) {
        mTextEmpty.setVisibility(View.GONE);

        mTasksAdapter.setTasks(tasks);
        mTasksAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String message) {
        DialogFactory.createSimpleOkErrorDialog(this,
                getString(R.string.dialog_error_title),
                message)
                .show();
    }

    @Override
    public void showTasksEmpty() {
        mTasksAdapter.setTasks(Collections.<TaskDTO>emptyList());
        mTasksAdapter.notifyDataSetChanged();

        mTextEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        getPresenter().getTasks();
    }
}
