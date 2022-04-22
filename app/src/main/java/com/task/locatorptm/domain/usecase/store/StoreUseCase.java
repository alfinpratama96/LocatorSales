package com.task.locatorptm.domain.usecase.store;

import com.task.locatorptm.data.models.store.StorePostResponse;
import com.task.locatorptm.data.models.store.StoreResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.repository.AppRepository;

import java.util.Map;

public class StoreUseCase {
    private final AppRepository repository;

    public StoreUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Resource<StoreResponse> execGet(String userId) {
        return repository.getStores(userId);
    }

    public Resource<StorePostResponse> execCreate(Map<String, String> map) {
        return repository.createStore(map);
    }

    public Resource<StorePostResponse> execDelete(Map<String, String> map) {
        return repository.deleteStore(map);
    }
}
