package com.task.locatorptm.presentation.absence.list;

import com.task.locatorptm.data.models.absence.AbsenceResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.absence.AbsenceUseCase;

import javax.inject.Inject;

public class AbsenceListPresenter {
    private final AbsenceListContract contract;
    private final AbsenceUseCase useCase;

    @Inject
    public AbsenceListPresenter(AbsenceListContract contract, AbsenceUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void get(String userId, String storeId) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<AbsenceResponse> result = useCase.execGet(userId, storeId);
                if (result instanceof Resource.Success) {
                    contract.showAbsenceList(((Resource.Success<AbsenceResponse>) result).getData().getData());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<AbsenceResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

}
