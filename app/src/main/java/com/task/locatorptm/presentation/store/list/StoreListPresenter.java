package com.task.locatorptm.presentation.store.list;

import com.task.locatorptm.data.models.store.StorePostResponse;
import com.task.locatorptm.data.models.store.StoreResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.store.StoreUseCase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class StoreListPresenter {
    private final StoreListContract contract;
    private final StoreUseCase useCase;

    @Inject
    public StoreListPresenter(StoreListContract contract, StoreUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void create(Map<String, String> map) {
        try {
            Thread thread = new Thread(() -> {
                Resource<StorePostResponse> result = useCase.execCreate(map);
                if (result instanceof Resource.Success) {
                    contract.successCreate();
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<StorePostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

    void getStoreList(String userId) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<StoreResponse> result = useCase.execGet(userId);
                if (result instanceof Resource.Success) {
                    contract.showStoreListData(((Resource.Success<StoreResponse>) result).getData().getData());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<StoreResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

    void delete(int id) {
        try {
            Thread thread = new Thread(() -> {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(id));
                Resource<StorePostResponse> result = useCase.execDelete(map);
                if (result instanceof Resource.Success) {
                    contract.successDelete(((Resource.Success<StorePostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<StorePostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
