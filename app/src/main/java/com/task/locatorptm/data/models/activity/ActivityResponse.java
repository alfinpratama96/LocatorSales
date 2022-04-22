package com.task.locatorptm.data.models.activity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActivityResponse {
    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public ArrayList<ActivityData> data;

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

    public ArrayList<ActivityData> getData() {
        return data;
    }

    public void setData(ArrayList<ActivityData> data) {
        this.data = data;
    }
}

