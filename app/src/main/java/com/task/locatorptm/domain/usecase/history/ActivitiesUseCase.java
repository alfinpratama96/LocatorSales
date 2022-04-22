package com.task.locatorptm.domain.usecase.history;

import com.task.locatorptm.data.models.activity.ActivityPostResponse;
import com.task.locatorptm.data.models.activity.ActivityResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.repository.AppRepository;

import java.util.Map;

public class ActivitiesUseCase {
    private final AppRepository repository;

    public ActivitiesUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Resource<ActivityResponse> execute(String userId, String storeId) {
        return repository.getActivities(userId, storeId);
    }

    public Resource<ActivityPostResponse> execCreate(Map<String, String> map) {
        return repository.createActivity(map);
    }

    public Resource<ActivityPostResponse> execUpdate(Map<String, String> map) {
        return repository.updateActivity(map);
    }
}
