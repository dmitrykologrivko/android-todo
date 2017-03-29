package com.dmitrykologrivkogmail.todolist.data.api.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TaskDTO {

    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String IS_DONE = "is_done";
    private static final String USER = "user";
    private static final String CREATED = "created";
    private static final String UPDATED = "updated";

    @SerializedName(ID)
    private int mId;
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

    public int getId() {
        return mId;
    }

    public void setId(int id) {
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
}
