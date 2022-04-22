package com.task.locatorptm.presentation.schedule.list;

import com.task.locatorptm.data.models.schedule.ScheduleResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.schedule.ScheduleUseCase;

import javax.inject.Inject;

public class ScheduleListPresenter {
    private final ScheduleListContract contract;
    private final ScheduleUseCase useCase;

    @Inject
    public ScheduleListPresenter(ScheduleListContract contract, ScheduleUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void get(String userId, String storeId) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<ScheduleResponse> result = useCase.execGet(userId, storeId);
                if (result instanceof Resource.Success) {
                    contract.showScheduleList(((Resource.Success<ScheduleResponse>) result).getData().getData());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<ScheduleResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
