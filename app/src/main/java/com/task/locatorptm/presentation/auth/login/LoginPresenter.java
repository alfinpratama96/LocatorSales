package com.task.locatorptm.presentation.auth.login;

import com.task.locatorptm.data.models.auth.AuthResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.auth.AuthUseCase;

import java.util.Map;

import javax.inject.Inject;

public class LoginPresenter {
    private final LoginContract contract;
    private final AuthUseCase useCase;

    @Inject
    public LoginPresenter(LoginContract contract, AuthUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void doLogin(Map<String, String> map) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<AuthResponse> result = useCase.execute(map);
                if (result instanceof Resource.Success) {
                    contract.successLogin(((Resource.Success<AuthResponse>) result).getData().getData());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<AuthResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
