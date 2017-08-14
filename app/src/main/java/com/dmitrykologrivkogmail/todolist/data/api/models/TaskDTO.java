package com.dmitrykologrivkogmail.todolist.data.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.Date;

public class TaskDTO {

    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String IS_DONE = "is_done";
    private static final String USER = "user";
    private static final String CREATED = "created";
    private static final String UPDATED = "updated";

    @SerializedName(ID)
    private long mId;
    @SerializedName(DESCRIPTION)
    private String mDescription;
    @SerializedName(IS_DONE)
    private boolean mIsDone;
    @SerializedName(USER)
    private int mUser;
    @SerializedName(CREATED)
    private Date mCreated;
    @SerializedName(UPDATED)
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

    public static class CreatedComparator implements Comparator<TaskDTO> {

        @Override
        public int compare(TaskDTO a, TaskDTO b) {
            return b.getCreated().compareTo(a.getCreated());
        }
    }

    public static class IsDoneComparator implements Comparator<TaskDTO> {

        @Override
        public int compare(TaskDTO a, TaskDTO b) {
            return Boolean.valueOf(a.isDone())
                    .compareTo(b.isDone());
        }
    }

    public static class Builder {

        private long mId;
        private String mDescription;
        private boolean mIsDone;

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

        public TaskDTO build() {
            TaskDTO task = new TaskDTO();
            task.setId(mId);
            task.setDescription(mDescription);
            task.setDone(mIsDone);
            return task;
        }
    }
}
