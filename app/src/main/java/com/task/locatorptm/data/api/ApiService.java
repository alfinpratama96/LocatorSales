package com.task.locatorptm.data.api;

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

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {

    //Auth
    @POST("users/login")
    @FormUrlEncoded
    Call<AuthResponse> login(@FieldMap Map<String, String> map);

    @POST("users/logout")
    @FormUrlEncoded
    Call<AuthResponse> logout(@FieldMap Map<String, String> map);

    //=================================================================//

    //Activities
    @GET("activities")
    Call<ActivityResponse> getActivities(
            @Query("userId") String userId,
            @Query("storeId") String storeId
    );

    @POST("activities/create")
    @FormUrlEncoded
    Call<ActivityPostResponse> createActivity(@FieldMap Map<String, String> map);

    @POST("activities/update")
    @FormUrlEncoded
    Call<ActivityPostResponse> updateActivity(@FieldMap Map<String, String> map);

    //=================================================================//

    //Orders
    @GET("orders/user")
    Call<OrderResponse> getOrderByUserId(
            @Query("userId") String userId,
            @Query("storeId") String storeId
    );

    @POST("orders/create")
    @FormUrlEncoded
    Call<OrderPostResponse> createOrder(@FieldMap Map<String, String> map);

    @POST("orders/update")
    @FormUrlEncoded
    Call<OrderPostResponse> updateOrder(@FieldMap Map<String, String> map);

    @POST("orders/delete")
    @FormUrlEncoded
    Call<OrderPostResponse> deleteOrder(@FieldMap Map<String, String> map);

    //=================================================================//

    //Store
    @GET("stores")
    Call<StoreResponse> getAllStores(@Query("userId") String userId);

    @POST("stores/create")
    @FormUrlEncoded
    Call<StorePostResponse> createStore(@FieldMap Map<String, String> map);

    @POST("stores/delete")
    @FormUrlEncoded
    Call<StorePostResponse> deleteStore(@FieldMap Map<String, String> map);

    //=================================================================//

    //Absence
    @GET("absences")
    Call<AbsenceResponse> getAbsences(
            @Query("userId") String userId,
            @Query("storeId") String storeId
    );

    @POST("absences/create")
    @Multipart
    Call<AbsencePostResponse> createAbsence(
            @PartMap Map<String, RequestBody> map,
            @Part MultipartBody.Part file
    );

    //=================================================================//

    //Visit Schedule
    @GET("schedules")
    Call<ScheduleResponse> getSchedules(
            @Query("userId") String userId,
            @Query("storeId") String storeId
    );

    @POST("schedules/create")
    @FormUrlEncoded
    Call<SchedulePostResponse> createSchedule(@FieldMap Map<String, String> map);

    @POST("schedules/update")
    @FormUrlEncoded
    Call<SchedulePostResponse> updateSchedule(@FieldMap Map<String, String> map);
}
