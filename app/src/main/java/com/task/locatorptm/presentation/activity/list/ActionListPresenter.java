package com.task.locatorptm.presentation.activity.list;

import com.task.locatorptm.data.models.activity.ActivityResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;

import javax.inject.Inject;

public class ActionListPresenter {
    private final ActionListContract contract;
    private final ActivitiesUseCase useCase;

    @Inject
    public ActionListPresenter(ActionListContract contract, ActivitiesUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void get(String userId, String storeId) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<ActivityResponse> result = useCase.execute(userId, storeId);
                if (result instanceof Resource.Success) {
                    contract.showActivityList(((Resource.Success<ActivityResponse>) result).getData().getData());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<ActivityResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
