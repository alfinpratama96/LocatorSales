package com.task.locatorptm.data.models.schedule;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScheduleResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ArrayList<ScheduleData> data;

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

    public ArrayList<ScheduleData> getData() {
        return data;
    }

    public void setData(ArrayList<ScheduleData> data) {
        this.data = data;
    }
}
