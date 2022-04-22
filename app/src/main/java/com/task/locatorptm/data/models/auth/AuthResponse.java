package com.task.locatorptm.data.models.auth;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    AuthData data;

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

    public AuthData getData() {
        return data;
    }

    public void setData(AuthData data) {
        this.data = data;
    }
}
