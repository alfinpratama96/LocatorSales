package com.task.locatorptm.data.models.schedule;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.task.locatorptm.data.models.auth.AuthData;
import com.task.locatorptm.data.models.store.StoreData;

public class ScheduleData implements Parcelable {
    @SerializedName("id")
    int id;
    @SerializedName("day")
    String day;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;
    @SerializedName("user")
    AuthData user;
    @SerializedName("store")
    StoreData store;

    protected ScheduleData(Parcel in) {
        id = in.readInt();
        day = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        user = in.readParcelable(AuthData.class.getClassLoader());
        store = in.readParcelable(StoreData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(day);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(store, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ScheduleData> CREATOR = new Creator<ScheduleData>() {
        @Override
        public ScheduleData createFromParcel(Parcel in) {
            return new ScheduleData(in);
        }

        @Override
        public ScheduleData[] newArray(int size) {
            return new ScheduleData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public AuthData getUser() {
        return user;
    }

    public void setUser(AuthData user) {
        this.user = user;
    }

    public StoreData getStore() {
        return store;
    }

    public void setStore(StoreData store) {
        this.store = store;
    }
}
