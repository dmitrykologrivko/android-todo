package com.dmitrykologrivkogmail.todolist.ui.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<M, H extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<H> {

    protected final List<M> mModels;

    protected OnItemClickListener mOnItemClickListener;

    public BaseAdapter() {
        mModels = new ArrayList<>();
    }

    public BaseAdapter(List<M> data) {
        mModels = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void clear() {
        mModels.clear();
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<M> data) {
        mModels.clear();
        mModels.addAll(data);
        notifyDataSetChanged();
    }

    public void addAll(List<M> data) {
        mModels.addAll(data);

        int addedSize = data.size();
        int oldSize = mModels.size() - addedSize;
        notifyItemRangeChanged(addedSize, oldSize);
    }

    public void addItem(M item) {
        mModels.add(item);
        notifyItemInserted(mModels.size());
    }

    public void updateItem(M item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            mModels.remove(position);
            mModels.add(position, item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position, M item) {
        if (position >= 0 && mModels.size() >= position + 1) {
            mModels.remove(position);
            mModels.add(position, item);
            notifyItemChanged(position);
        }
    }

    public void removeItem(M item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            mModels.remove(item);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && mModels.size() >= position + 1) {
            mModels.remove(position);
            notifyItemRemoved(position);
        }
    }

    public int getItemPosition(M item) {
        Object modelId = getModelId(item);

        int position = -1;
        for (int i = 0; i < mModels.size(); i++) {
            M model = mModels.get(i);
            if (getModelId(model).equals(modelId)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public M getItem(int position) {
        return mModels.get(position);
    }

    @Override
    public int getItemCount() {
        return (null != mModels ? mModels.size() : 0);
    }

    protected abstract Object getModelId(M item);

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
