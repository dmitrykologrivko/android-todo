package com.dmitrykologrivkogmail.todolist.ui.modules.tasks;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dmitrykologrivkogmail.todolist.R;
import com.dmitrykologrivkogmail.todolist.data.models.Task;
import com.dmitrykologrivkogmail.todolist.injection.PerActivity;
import com.dmitrykologrivkogmail.todolist.ui.base.BaseAdapter;
import com.dmitrykologrivkogmail.todolist.common.utils.MultiComparator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class TasksAdapter extends BaseAdapter<Task, TasksAdapter.TasksViewHolder> {

    private TasksPresenter mPresenter;

    @Inject
    public TasksAdapter(TasksPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        holder.bind(mModels.get(position));
    }

    @Override
    public long getItemId(int position) {
        return mModels.get(position).getId();
    }

    @Override
    protected Object getModelId(Task item) {
        return item.getId();
    }

    public void sortTasks() {
        MultiComparator.sort(mModels,
                new Task.IsDoneComparator(),
                new Task.CreatedComparator());
        notifyDataSetChanged();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check_is_done)
        CheckBox checkIsDone;

        @BindView(R.id.text_description)
        TextView textDescription;

        TasksViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(final Task task) {
            checkIsDone.setChecked(task.isDone());
            textDescription.setText(task.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onTaskClick(task);
                }
            });

            checkIsDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.markTask(task);
                }
            });
        }
    }
}
