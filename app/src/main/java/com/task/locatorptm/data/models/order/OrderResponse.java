package com.task.locatorptm.data.models.order;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ArrayList<OrderData> data;

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

    public ArrayList<OrderData> getData() {
        return data;
    }

    public void setData(ArrayList<OrderData> data) {
        this.data = data;
    }
}
