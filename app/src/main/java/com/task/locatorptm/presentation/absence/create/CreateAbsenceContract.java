package com.task.locatorptm.presentation.absence.create;

public interface CreateAbsenceContract {
    void showLoading();
    void hideLoading();
    void successCreate(String message);
    void showError(String message);
}
