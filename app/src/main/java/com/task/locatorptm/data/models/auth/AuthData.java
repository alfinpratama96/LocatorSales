package com.task.locatorptm.data.models.auth;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AuthData implements Parcelable {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String name;
    @SerializedName("idCard")
    String idCard;
    @SerializedName("img")
    String img;
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;

    protected AuthData(Parcel in) {
        id = in.readInt();
        name = in.readString();
        idCard = in.readString();
        img = in.readString();
        username = in.readString();
        email = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
    }

    public static final Creator<AuthData> CREATOR = new Creator<AuthData>() {
        @Override
        public AuthData createFromParcel(Parcel in) {
            return new AuthData(in);
        }

        @Override
        public AuthData[] newArray(int size) {
            return new AuthData[size];
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(idCard);
        parcel.writeString(img);
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(createdAt);
        parcel.writeString(updatedAt);
    }
}
