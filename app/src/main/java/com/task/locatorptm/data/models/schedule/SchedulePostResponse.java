package com.task.locatorptm.data.models.schedule;

import com.google.gson.annotations.SerializedName;

public class SchedulePostResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ScheduleData data;

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

    public ScheduleData getData() {
        return data;
    }

    public void setData(ScheduleData data) {
        this.data = data;
    }
}
