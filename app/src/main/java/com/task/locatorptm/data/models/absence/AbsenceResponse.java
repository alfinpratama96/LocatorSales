package com.task.locatorptm.data.models.absence;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AbsenceResponse {
    @SerializedName("status")
    boolean status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    ArrayList<AbsenceData> data;

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

    public ArrayList<AbsenceData> getData() {
        return data;
    }

    public void setData(ArrayList<AbsenceData> data) {
        this.data = data;
    }
}
