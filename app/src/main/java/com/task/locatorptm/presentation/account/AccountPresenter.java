package com.task.locatorptm.presentation.account;

import com.task.locatorptm.data.models.auth.AuthResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.auth.AuthUseCase;

import java.util.Map;

import javax.inject.Inject;

public class AccountPresenter {
    private final AccountContract contract;
    private final AuthUseCase useCase;

    @Inject
    public AccountPresenter(AccountContract contract, AuthUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void doLogout(Map<String, String> map) {
        contract.showLoading();
        try {
            Resource<AuthResponse> result = useCase.execLogout(map);
            if (result instanceof Resource.Success) {
                contract.successLogout(((Resource.Success<AuthResponse>) result).getData().getMessage());
            } if (result instanceof Resource.Error) {
                contract.showError(((Resource.Error<AuthResponse>) result).getMessage());
            }
        } catch (Exception e) {
            contract.showError("Error "+e);
        }
    }
}
