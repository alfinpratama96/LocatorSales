package com.task.locatorptm.data.models.activity;

import com.google.gson.annotations.SerializedName;

public class ActivityPostResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ActivityData data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ActivityData getData() {
        return data;
    }

    public void setData(ActivityData data) {
        this.data = data;
    }
}
