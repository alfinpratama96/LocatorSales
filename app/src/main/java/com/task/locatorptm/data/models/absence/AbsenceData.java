package com.task.locatorptm.data.models.absence;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.task.locatorptm.data.models.auth.AuthData;
import com.task.locatorptm.data.models.store.StoreData;

public class AbsenceData implements Parcelable {
    @SerializedName("id")
    int id;
    @SerializedName("latLng")
    String latLng;
    @SerializedName("img")
    String img;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;
    @SerializedName("user")
    AuthData user;
    @SerializedName("store")
    StoreData store;

    protected AbsenceData(Parcel in) {
        id = in.readInt();
        latLng = in.readString();
        img = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        user = in.readParcelable(AuthData.class.getClassLoader());
        store = in.readParcelable(StoreData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(latLng);
        dest.writeString(img);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(store, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AbsenceData> CREATOR = new Creator<AbsenceData>() {
        @Override
        public AbsenceData createFromParcel(Parcel in) {
            return new AbsenceData(in);
        }

        @Override
        public AbsenceData[] newArray(int size) {
            return new AbsenceData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatLng() {
        return latLng;
    }

    public void setLatLng(String latLng) {
        this.latLng = latLng;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
