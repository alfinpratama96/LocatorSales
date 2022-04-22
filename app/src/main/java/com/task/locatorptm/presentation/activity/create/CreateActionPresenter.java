package com.task.locatorptm.presentation.activity.create;

import com.task.locatorptm.data.models.activity.ActivityPostResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;

import java.util.Map;

import javax.inject.Inject;

public class CreateActionPresenter {
    private final CreateActionContract contract;
    private final ActivitiesUseCase useCase;

    @Inject
    public CreateActionPresenter(CreateActionContract contract, ActivitiesUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void create(Map<String, String> map) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<ActivityPostResponse> result = useCase.execCreate(map);
                if (result instanceof Resource.Success) {
                    contract.successCreate(((Resource.Success<ActivityPostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<ActivityPostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

    void update(Map<String, String> map) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<ActivityPostResponse> result = useCase.execUpdate(map);
                if (result instanceof Resource.Success) {
                    contract.successCreate(((Resource.Success<ActivityPostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<ActivityPostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
