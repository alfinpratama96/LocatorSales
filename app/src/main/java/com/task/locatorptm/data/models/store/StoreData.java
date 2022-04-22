package com.task.locatorptm.data.models.store;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.task.locatorptm.data.models.auth.AuthData;

public class StoreData implements Parcelable {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;
    @SerializedName("user")
    AuthData user;

    protected StoreData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        user = in.readParcelable(AuthData.class.getClassLoader());
    }

    public static final Creator<StoreData> CREATOR = new Creator<StoreData>() {
        @Override
        public StoreData createFromParcel(Parcel in) {
            return new StoreData(in);
        }

        @Override
        public StoreData[] newArray(int size) {
            return new StoreData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
        parcel.writeParcelable(user, i);
    }
}
