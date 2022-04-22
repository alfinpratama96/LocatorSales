package com.task.locatorptm.data.models.store;

import com.google.gson.annotations.SerializedName;

public class StorePostResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    StoreData data;

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

    public StoreData getData() {
        return data;
    }

    public void setData(StoreData data) {
        this.data = data;
    }
}
