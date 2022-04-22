package com.task.locatorptm.domain.usecase.absence;

import com.task.locatorptm.data.models.absence.AbsencePostResponse;
import com.task.locatorptm.data.models.absence.AbsenceResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.repository.AppRepository;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AbsenceUseCase {
    private final AppRepository repository;

    public AbsenceUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Resource<AbsenceResponse> execGet(String userId, String storeId) {
        return repository.getAbsences(userId, storeId);
    }

    public Resource<AbsencePostResponse> execCreate(Map<String, RequestBody> map, MultipartBody.Part file) {
        return repository.createAbsence(map, file);
    }
}
