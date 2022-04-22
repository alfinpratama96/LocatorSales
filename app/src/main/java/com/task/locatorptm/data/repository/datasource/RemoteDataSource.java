package com.task.locatorptm.data.repository.datasource;

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

import java.io.File;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public interface RemoteDataSource {
    //===================AUTH==========================//

    Call<AuthResponse> login(Map<String, String> map);
    Call<AuthResponse> logout(Map<String, String> map);

    //===================AUTH==========================//

    //=================ACTIVITIES=====================//

    Call<ActivityResponse> getActivities(String userId, String storeId);
    Call<ActivityPostResponse> createActivity(Map<String, String> map);
    Call<ActivityPostResponse> updateActivity(Map<String, String> map);

    //=================ACTIVITIES=====================//

    //===================ORDERS========================//

    Call<OrderResponse> getOrdersByUserId(String userId, String storeId);
    Call<OrderPostResponse> createOrder(Map<String, String> map);
    Call<OrderPostResponse> updateOrder(Map<String, String> map);
    Call<OrderPostResponse> deleteOrder(Map<String, String> map);

    //===================ORDERS========================//

    //===================STORES========================//

    Call<StoreResponse> getStores(String userId);
    Call<StorePostResponse> createStore(Map<String, String> map);
    Call<StorePostResponse> deleteStore(Map<String, String> map);

    //===================STORES========================//

    //===================ABSENCES========================//

    Call<AbsenceResponse> getAbsences(String userId, String storeId);
    Call<AbsencePostResponse> createAbsence(Map<String, RequestBody> map, MultipartBody.Part file);

    //===================ABSENCES========================//

    //===================SCHEDULES========================//

    Call<ScheduleResponse> getSchedules(String userId, String storeId);
    Call<SchedulePostResponse> createSchedule(Map<String, String> map);
    Call<SchedulePostResponse> updateSchedule(Map<String, String> map);
}
