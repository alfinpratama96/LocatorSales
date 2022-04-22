package com.task.locatorptm.data.repository.datasourceimpl;

import com.task.locatorptm.data.api.ApiService;
import com.task.locatorptm.data.models.absence.AbsencePostResponse;
import com.task.locatorptm.data.models.absence.AbsenceResponse;
import com.task.locatorptm.data.models.activity.ActivityPostResponse;
import com.task.locatorptm.data.models.activity.ActivityResponse;
import com.task.locatorptm.data.models.auth.AuthResponse;
import com.task.locatorptm.data.models.order.OrderPostResponse;
import com.task.locatorptm.data.models.order.OrderResponse;
import com.task.locatorptm.data.models.schedule.SchedulePostResponse;
import com.task.locatorptm.data.models.schedule.ScheduleResponse;
import com.task.locatorptm.data.models.store.StorePostResponse;
import com.task.locatorptm.data.models.store.StoreResponse;
import com.task.locatorptm.data.repository.datasource.RemoteDataSource;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class RemoteDataSourceImpl implements RemoteDataSource {
    private final ApiService service;

    public RemoteDataSourceImpl(ApiService service) {
        this.service = service;
    }


    @Override
    public Call<AuthResponse> login(Map<String, String> map) {
        return service.login(map);
    }

    @Override
    public Call<AuthResponse> logout(Map<String, String> map) {
        return service.logout(map);
    }

    @Override
    public Call<ActivityResponse> getActivities(String userId, String storeId) {
        return service.getActivities(userId, storeId);
    }

    @Override
    public Call<ActivityPostResponse> createActivity(Map<String, String> map) {
        return service.createActivity(map);
    }

    @Override
    public Call<ActivityPostResponse> updateActivity(Map<String, String> map) {
        return service.updateActivity(map);
    }

    @Override
    public Call<OrderResponse> getOrdersByUserId(String userId, String storeId) {
        return service.getOrderByUserId(userId, storeId);
    }

    @Override
    public Call<OrderPostResponse> createOrder(Map<String, String> map) {
        return service.createOrder(map);
    }

    @Override
    public Call<OrderPostResponse> updateOrder(Map<String, String> map) {
        return service.updateOrder(map);
    }

    @Override
    public Call<OrderPostResponse> deleteOrder(Map<String, String> map) {
        return service.deleteOrder(map);
    }

    @Override
    public Call<StoreResponse> getStores(String userId) {
        return service.getAllStores(userId);
    }

    @Override
    public Call<StorePostResponse> createStore(Map<String, String> map) {
        return service.createStore(map);
    }

    @Override
    public Call<StorePostResponse> deleteStore(Map<String, String> map) {
        return service.deleteStore(map);
    }

    @Override
    public Call<AbsenceResponse> getAbsences(String userId, String storeId) {
        return service.getAbsences(userId, storeId);
    }

    @Override
    public Call<AbsencePostResponse> createAbsence(Map<String, RequestBody> map, MultipartBody.Part file) {
        return service.createAbsence(map, file);
    }

    @Override
    public Call<ScheduleResponse> getSchedules(String userId, String storeId) {
        return service.getSchedules(userId, storeId);
    }

    @Override
    public Call<SchedulePostResponse> createSchedule(Map<String, String> map) {
        return service.createSchedule(map);
    }

    @Override
    public Call<SchedulePostResponse> updateSchedule(Map<String, String> map) {
        return service.updateSchedule(map);
    }
}
