package com.task.locatorptm.data.models.store;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StoreResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ArrayList<StoreData> data;

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

    public ArrayList<StoreData> getData() {
        return data;
    }

    public void setData(ArrayList<StoreData> data) {
        this.data = data;
    }
}
