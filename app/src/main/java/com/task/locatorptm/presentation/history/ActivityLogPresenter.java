package com.task.locatorptm.presentation.history;

import com.task.locatorptm.data.models.activity.ActivityPostResponse;
import com.task.locatorptm.data.models.activity.ActivityResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class ActivityLogPresenter {

    private final ActivityLogContract contract;
    private final ActivitiesUseCase useCase;

    @Inject
    public ActivityLogPresenter(ActivityLogContract contract, ActivitiesUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void getActivities(String userId) {
        contract.showLoading();
        try {
//            Thread thread = new Thread(() -> {
//                Resource<ActivityResponse> result = useCase.execute(userId);
//                if (result instanceof Resource.Success) {
//                    contract.showActivities(((Resource.Success<ActivityResponse>) result).getData().data);
//                } if (result instanceof Resource.Error) {
//                    contract.showError(((Resource.Error<ActivityResponse>) result).getData().message);
//                }
//            });
//            thread.start();
        } catch (Exception e) {
            e.printStackTrace();
            contract.showError("Error : "+e);
        }
    }

    void createActivity(Map<String, String> map) {
        try {
//            Thread thread = new Thread(() -> {
//                Resource<ActivityPostResponse> result = useCase.execCreate(map);
//                if (result instanceof Resource.Success) {
//                    contract.successCreate();
//                } if (result instanceof Resource.Error) {
//                    contract.showError(((Resource.Error<ActivityPostResponse>) result).getMessage());
//                }
//            });
//            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

    void deleteActivity(String id) {
        try {
//            Thread thread = new Thread(() -> {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("id", id);
//                Resource<ActivityPostResponse> result = useCase.execDelete(map);
//                if (result instanceof Resource.Success) {
//                    contract.successDelete(((Resource.Success<ActivityPostResponse>) result).getData().getMessage());
//                } if (result instanceof Resource.Error) {
//                    contract.showError(((Resource.Error<ActivityPostResponse>) result).getMessage());
//                }
//            });
//            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
