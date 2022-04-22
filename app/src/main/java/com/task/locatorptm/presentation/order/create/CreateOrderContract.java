package com.task.locatorptm.presentation.order.create;

public interface CreateOrderContract {
    void showLoading();
    void hideLoading();
    void successCreate(String message);
    void showError(String message);
}
