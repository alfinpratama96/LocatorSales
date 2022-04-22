package com.task.locatorptm.presentation.schedule.create;

public interface CreateScheduleContract {
    void showLoading();
    void hideLoading();
    void success(String message);
    void showError(String message);
}
