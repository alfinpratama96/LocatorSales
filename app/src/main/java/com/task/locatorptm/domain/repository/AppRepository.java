package com.task.locatorptm.domain.repository;

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
import com.task.locatorptm.data.utils.Resource;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface AppRepository {
    Resource<AuthResponse> login(Map<String, String> map);
    Resource<AuthResponse> logout(Map<String, String> map);
    Resource<ActivityResponse> getActivities(String userId, String storeId);
    Resource<ActivityPostResponse> createActivity(Map<String, String> map);
    Resource<ActivityPostResponse> updateActivity(Map<String, String> map);
    Resource<OrderResponse> getOrdersByUserId(String userId, String storeId);
    Resource<OrderPostResponse> createOrder(Map<String, String> map);
    Resource<OrderPostResponse> updateOrder(Map<String, String> map);
    Resource<OrderPostResponse> deleteOrder(Map<String, String> map);
    Resource<StoreResponse> getStores(String userId);
    Resource<StorePostResponse> createStore(Map<String, String> map);
    Resource<StorePostResponse> deleteStore(Map<String, String> map);
    Resource<AbsenceResponse> getAbsences(String userId, String storeId);
    Resource<AbsencePostResponse> createAbsence(
            Map<String, RequestBody> map, MultipartBody.Part file
    );
    Resource<ScheduleResponse> getSchedules(String userId, String storeId);
    Resource<SchedulePostResponse> createSchedule(Map<String, String> map);
    Resource<SchedulePostResponse> updateSchedule(Map<String, String> map);
}
