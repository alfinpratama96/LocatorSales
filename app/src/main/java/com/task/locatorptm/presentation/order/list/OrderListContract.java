package com.task.locatorptm.presentation.order.list;

import com.task.locatorptm.data.models.order.OrderData;

import java.util.ArrayList;

public interface OrderListContract {
    void showLoading();
    void hideLoading();
    void showOrderList(ArrayList<OrderData> list);
    void successDelete(String message);
    void successCreate();
    void showError(String message);
}
