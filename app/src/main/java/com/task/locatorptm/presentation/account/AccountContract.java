package com.task.locatorptm.presentation.account;

public interface AccountContract {
    void showLoading();
    void hideLoading();
    void successLogout(String message);
    void showError(String message);
}
