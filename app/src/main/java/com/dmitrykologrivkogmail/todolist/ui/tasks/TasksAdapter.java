package com.dmitrykologrivkogmail.todolist.ui.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.api.models.TaskDTO;
import com.dmitrykologrivkogmail.todolist.injection.PerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private List<TaskDTO> mTasks;

    @Inject
    public TasksAdapter() {
        mTasks = new ArrayList<>();
    }

    public void setTasks(List<TaskDTO> tasks) {
        mTasks = tasks;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        TaskDTO task = mTasks.get(position);
        holder.checkIsDone.setChecked(task.isDone());
        holder.textDescription.setText(task.getDescription());
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check_is_done)
        CheckBox checkIsDone;

        @BindView(R.id.text_description)
        TextView textDescription;

        public TasksViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
