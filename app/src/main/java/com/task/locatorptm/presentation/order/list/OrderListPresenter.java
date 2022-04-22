package com.task.locatorptm.presentation.order.list;

import com.task.locatorptm.data.models.order.OrderPostResponse;
import com.task.locatorptm.data.models.order.OrderResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.order.OrderUseCase;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class OrderListPresenter {
    private final OrderListContract contract;
    private final OrderUseCase useCase;

    @Inject
    public OrderListPresenter(OrderListContract contract, OrderUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void getOrders(String userId, String storeId) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<OrderResponse> result = useCase.execGetOrdersByUserId(userId, storeId);
                if (result instanceof Resource.Success) {
                    contract.showOrderList(((Resource.Success<OrderResponse>) result).getData().getData());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<OrderResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

    void createOrder(Map<String, String> map) {
        try {
            Thread thread = new Thread(() -> {
                Resource<OrderPostResponse> result = useCase.execCreateOrder(map);
                if (result instanceof Resource.Success) {
                    contract.successCreate();
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<OrderPostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

    void deleteOrder(String id) {
        try {
            Thread thread = new Thread(() -> {
                HashMap<String, String> map = new HashMap<>();
                map.put("id", id);
                Resource<OrderPostResponse> result = useCase.execDeleteOrder(map);
                if (result instanceof Resource.Success) {
                    contract.successDelete(((Resource.Success<OrderPostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<OrderPostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
