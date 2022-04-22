package com.task.locatorptm.data.repository;

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
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.repository.AppRepository;

import java.io.IOException;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class AppRepositoryImpl implements AppRepository {
    private final RemoteDataSource remoteDataSource;

    public AppRepositoryImpl(RemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Resource<ActivityResponse> getActivities(String userId, String storeId) {
        return activityToResponse(remoteDataSource.getActivities(userId, storeId));
    }

    @Override
    public Resource<ActivityPostResponse> createActivity(Map<String, String> map) {
        return postActivityToResponse(remoteDataSource.createActivity(map));
    }

    @Override
    public Resource<ActivityPostResponse> updateActivity(Map<String, String> map) {
        return postActivityToResponse(remoteDataSource.updateActivity(map));
    }

    private Resource<ActivityResponse> activityToResponse(Call<ActivityResponse> response) {
        try {
            Response<ActivityResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    private Resource<ActivityPostResponse> postActivityToResponse(Call<ActivityPostResponse> response) {
        try {
            Response<ActivityPostResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    @Override
    public Resource<AuthResponse> login(Map<String, String> map) {
        return loginToResponse(remoteDataSource.login(map));
    }

    @Override
    public Resource<AuthResponse> logout(Map<String, String> map) {
        return loginToResponse(remoteDataSource.logout(map));
    }

    private Resource<AuthResponse> loginToResponse(Call<AuthResponse> response) {
        try {
            Response<AuthResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else {
                return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    @Override
    public Resource<OrderResponse> getOrdersByUserId(String userId, String storeId) {
        return getOrderToResponse(remoteDataSource.getOrdersByUserId(userId, storeId));
    }

    @Override
    public Resource<OrderPostResponse> createOrder(Map<String, String> map) {
        return postOrderToResponse(remoteDataSource.createOrder(map));
    }

    @Override
    public Resource<OrderPostResponse> updateOrder(Map<String, String> map) {
        return postOrderToResponse(remoteDataSource.updateOrder(map));
    }

    @Override
    public Resource<OrderPostResponse> deleteOrder(Map<String, String> map) {
        return postOrderToResponse(remoteDataSource.deleteOrder(map));
    }

    private Resource<OrderResponse> getOrderToResponse(Call<OrderResponse> response) {
        try {
            Response<OrderResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    private Resource<OrderPostResponse> postOrderToResponse(Call<OrderPostResponse> response) {
        try {
            Response<OrderPostResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    @Override
    public Resource<StoreResponse> getStores(String userId) {
        return storeToResponse(remoteDataSource.getStores(userId));
    }

    @Override
    public Resource<StorePostResponse> createStore(Map<String, String> map) {
        return postStoreToResponse(remoteDataSource.createStore(map));
    }

    @Override
    public Resource<StorePostResponse> deleteStore(Map<String, String> map) {
        return postStoreToResponse(remoteDataSource.deleteStore(map));
    }

    private Resource<StoreResponse> storeToResponse(Call<StoreResponse> response) {
        try {
            Response<StoreResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    private Resource<StorePostResponse> postStoreToResponse(Call<StorePostResponse> response) {
        try {
            Response<StorePostResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    @Override
    public Resource<AbsenceResponse> getAbsences(String userId, String storeId) {
        return absenceToResponse(remoteDataSource.getAbsences(userId, storeId));
    }

    @Override
    public Resource<AbsencePostResponse> createAbsence(Map<String, RequestBody> map, MultipartBody.Part file) {
        return postAbsenceToResponse(remoteDataSource.createAbsence(map, file));
    }

    private Resource<AbsenceResponse> absenceToResponse(Call<AbsenceResponse> response) {
        try {
            Response<AbsenceResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    private Resource<AbsencePostResponse> postAbsenceToResponse(Call<AbsencePostResponse> response) {
        try {
            Response<AbsencePostResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    @Override
    public Resource<ScheduleResponse> getSchedules(String userId, String storeId) {
        return scheduleToResponse(remoteDataSource.getSchedules(userId, storeId));
    }

    @Override
    public Resource<SchedulePostResponse> createSchedule(Map<String, String> map) {
        return postScheduleToResponse(remoteDataSource.createSchedule(map));
    }

    @Override
    public Resource<SchedulePostResponse> updateSchedule(Map<String, String> map) {
        return postScheduleToResponse(remoteDataSource.updateSchedule(map));
    }

    private Resource<ScheduleResponse> scheduleToResponse(Call<ScheduleResponse> response) {
        try {
            Response<ScheduleResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }

    private Resource<SchedulePostResponse> postScheduleToResponse(Call<SchedulePostResponse> response) {
        try {
            Response<SchedulePostResponse> res = response.execute();
            if (res.code() == 200) {
                return new Resource.Success<>(res.body());
            } else return new Resource.Error<>(null, AppUtil.errorMessage(res.errorBody().string()));
        } catch (IOException e) {
            e.printStackTrace();
            return new Resource.Error<>(null, ""+e);
        }
    }
}
