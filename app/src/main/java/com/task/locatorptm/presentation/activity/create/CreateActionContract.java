package com.task.locatorptm.presentation.activity.create;

public interface CreateActionContract {
    void showLoading();
    void hideLoading();
    void successCreate(String message);
    void showError(String message);
}
