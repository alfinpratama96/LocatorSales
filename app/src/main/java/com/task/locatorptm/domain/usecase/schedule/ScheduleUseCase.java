package com.task.locatorptm.domain.usecase.schedule;

import com.task.locatorptm.data.models.schedule.SchedulePostResponse;
import com.task.locatorptm.data.models.schedule.ScheduleResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.repository.AppRepository;

import java.util.Map;

public class ScheduleUseCase {
    private final AppRepository repository;

    public ScheduleUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Resource<ScheduleResponse> execGet(String userId, String storeId) {
        return repository.getSchedules(userId, storeId);
    }

    public Resource<SchedulePostResponse> execCreate(Map<String, String> map) {
        return repository.createSchedule(map);
    }

    public Resource<SchedulePostResponse> execUpdate(Map<String, String> map) {
        return repository.updateSchedule(map);
    }
}
