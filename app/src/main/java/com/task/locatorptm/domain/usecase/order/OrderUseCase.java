package com.task.locatorptm.domain.usecase.order;

import com.task.locatorptm.data.models.order.OrderPostResponse;
import com.task.locatorptm.data.models.order.OrderResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.repository.AppRepository;

import java.util.Map;

public class OrderUseCase {
    private final AppRepository repository;

    public OrderUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Resource<OrderResponse> execGetOrdersByUserId(String userId, String storeId) {
        return repository.getOrdersByUserId(userId, storeId);
    }

    public Resource<OrderPostResponse> execCreateOrder(Map<String, String> map) {
        return repository.createOrder(map);
    }

    public Resource<OrderPostResponse> execUpdateOrder(Map<String, String> map) {
        return repository.updateOrder(map);
    }

    public Resource<OrderPostResponse> execDeleteOrder(Map<String, String> map) {
        return repository.deleteOrder(map);
    }
}
