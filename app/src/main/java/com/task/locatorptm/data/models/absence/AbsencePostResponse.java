package com.task.locatorptm.data.models.absence;

import com.google.gson.annotations.SerializedName;

public class AbsencePostResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    AbsenceData data;

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

    public AbsenceData getData() {
        return data;
    }

    public void setData(AbsenceData data) {
        this.data = data;
    }
}
