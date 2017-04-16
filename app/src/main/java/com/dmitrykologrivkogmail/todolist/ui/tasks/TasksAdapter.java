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
import com.dmitrykologrivkogmail.todolist.ui.base.BaseAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class TasksAdapter extends BaseAdapter<TaskDTO, TasksAdapter.TasksViewHolder> {

    private OnTaskMarkedListener mOnTaskMarkedListener;

    @Inject
    public TasksAdapter() {
    }

    public void setOnTaskMarkedListener(OnTaskMarkedListener listener) {
        mOnTaskMarkedListener = listener;
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new TasksViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        holder.bind(mModels.get(position),
                mOnItemClickListener,
                mOnTaskMarkedListener);
    }

    @Override
    public long getItemId(int position) {
        return mModels.get(position).getId();
    }

    @Override
    protected Object getModelId(TaskDTO item) {
        return item.getId();
    }

    public interface OnTaskMarkedListener {
        void onTaskMarked(int position, boolean isChecked);
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

        void bind(final TaskDTO task,
                  final OnItemClickListener itemClickListener,
                  final OnTaskMarkedListener onTaskMarkedListener) {

            checkIsDone.setChecked(task.isDone());
            textDescription.setText(task.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(getAdapterPosition());
                }
            });

            checkIsDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTaskMarkedListener != null)
                        onTaskMarkedListener.onTaskMarked(getAdapterPosition(), checkIsDone.isChecked());
                }
            });
        }
    }
}
