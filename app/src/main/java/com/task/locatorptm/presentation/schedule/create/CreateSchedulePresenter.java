package com.task.locatorptm.presentation.schedule.create;

import com.task.locatorptm.data.models.schedule.SchedulePostResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.schedule.ScheduleUseCase;

import java.util.Map;

import javax.inject.Inject;

public class CreateSchedulePresenter {
    private final CreateScheduleContract contract;
    private final ScheduleUseCase useCase;

    @Inject
    public CreateSchedulePresenter(CreateScheduleContract contract, ScheduleUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void create(Map<String, String> map) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<SchedulePostResponse> result = useCase.execCreate(map);
                if (result instanceof Resource.Success) {
                    contract.success(((Resource.Success<SchedulePostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<SchedulePostResponse>) result).getMessage());
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
                Resource<SchedulePostResponse> result = useCase.execUpdate(map);
                if (result instanceof Resource.Success) {
                    contract.success(((Resource.Success<SchedulePostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<SchedulePostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
