package com.task.locatorptm.data.models.order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.task.locatorptm.data.models.auth.AuthData;
import com.task.locatorptm.data.models.store.StoreData;

public class OrderData implements Parcelable {
    @SerializedName("id")
    int id;
    @SerializedName("name")
    String orderName;
    @SerializedName("quantity")
    int quantity;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("updatedAt")
    String updatedAt;
    @SerializedName("user")
    AuthData user;
    @SerializedName("store")
    StoreData store;

    public OrderData(int id, String orderName, int quantity, String createdAt, String updatedAt, AuthData user, StoreData store) {
        this.id = id;
        this.orderName = orderName;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.store = store;
    }

    protected OrderData(Parcel in) {
        id = in.readInt();
        orderName = in.readString();
        quantity = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        user = in.readParcelable(AuthData.class.getClassLoader());
        store = in.readParcelable(StoreData.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(orderName);
        dest.writeInt(quantity);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(store, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderData> CREATOR = new Creator<OrderData>() {
        @Override
        public OrderData createFromParcel(Parcel in) {
            return new OrderData(in);
        }

        @Override
        public OrderData[] newArray(int size) {
            return new OrderData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
