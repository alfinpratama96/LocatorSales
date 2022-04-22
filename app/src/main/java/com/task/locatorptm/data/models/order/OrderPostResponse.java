package com.task.locatorptm.data.models.order;

import com.google.gson.annotations.SerializedName;

public class OrderPostResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    OrderData data;

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

    public OrderData getData() {
        return data;
    }

    public void setData(OrderData data) {
        this.data = data;
    }
}
