package com.dmitrykologrivkogmail.todolist.data.models;

import java.util.Comparator;
import java.util.Date;

public class Task {

    private long mId;
    private String mDescription;
    private boolean mIsDone;
    private int mUser;
    private Date mCreated;
    private Date mUpdated;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public void setDone(boolean done) {
        mIsDone = done;
    }

    public int getUser() {
        return mUser;
    }

    public void setUser(int user) {
        mUser = user;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        mCreated = created;
    }

    public Date getUpdated() {
        return mUpdated;
    }

    public void setUpdated(Date updated) {
        mUpdated = updated;
    }

    /**
     * Supported ordering
     */
    public enum Ordering {
        IS_DONE("is_done"),
        _IS_DONE("-is_done");

        private final String mOrdering;

        Ordering(String ordering) {
            mOrdering = ordering;
        }

        public String toString() {
            return this.mOrdering;
        }
    }

    /**
     * Created date comparator
     */
    public static class CreatedComparator implements Comparator<Task> {

        @Override
        public int compare(Task a, Task b) {
            return b.getCreated().compareTo(a.getCreated());
        }
    }

    /**
     * Is done comparator
     */
    public static class IsDoneComparator implements Comparator<Task> {

        @Override
        public int compare(Task a, Task b) {
            return Boolean.valueOf(a.isDone())
                    .compareTo(b.isDone());
        }
    }

    /**
     * Model builder
     */
    public static class Builder {

        private long mId;
        private String mDescription;
        private boolean mIsDone;
        private int mUser;
        private Date mCreated;
        private Date mUpdated;

        public Builder id(long id) {
            mId = id;
            return this;
        }

        public Builder description(String description) {
            mDescription = description;
            return this;
        }

        public Builder done(boolean done) {
            mIsDone = done;
            return this;
        }

        public Builder user(int user) {
            mUser = user;
            return this;
        }

        public Builder created(Date created) {
            mCreated = created;
            return this;
        }

        public Builder updated(Date updated) {
            mUpdated = updated;
            return this;
        }

        public Task build() {
            Task task = new Task();
            task.setId(mId);
            task.setDescription(mDescription);
            task.setDone(mIsDone);
            task.setUser(mUser);
            task.setCreated(mCreated);
            task.setUpdated(mUpdated);
            return task;
        }
    }
}
