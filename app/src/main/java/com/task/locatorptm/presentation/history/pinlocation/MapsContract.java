package com.task.locatorptm.presentation.history.pinlocation;

public interface MapsContract {
    void showLoading();
    void hideLoading();
    void successCreate(String message);
    void showError(String message);
}
