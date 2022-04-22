package com.task.locatorptm.presentation.auth.login;

import com.task.locatorptm.data.models.auth.AuthData;

public interface LoginContract {
    void showLoading();
    void hideLoading();
    void successLogin(AuthData data);
    void showError(String message);
}
