package com.task.locatorptm.presentation.order.create;

import com.task.locatorptm.data.models.order.OrderPostResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.order.OrderUseCase;

import java.util.Map;

import javax.inject.Inject;

public class CreateOrderPresenter {
    private final CreateOrderContract contract;
    private final OrderUseCase useCase;

    @Inject
    public CreateOrderPresenter(CreateOrderContract contract, OrderUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void createOrder(Map<String, String> map) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<OrderPostResponse> result = useCase.execCreateOrder(map);
                if (result instanceof Resource.Success) {
                    contract.successCreate(((Resource.Success<OrderPostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<OrderPostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }

    void updateOrder(Map<String, String> map) {
        contract.showLoading();
        try {
            Thread thread = new Thread(() -> {
                Resource<OrderPostResponse> result = useCase.execUpdateOrder(map);
                if (result instanceof Resource.Success) {
                    contract.successCreate(((Resource.Success<OrderPostResponse>) result).getData().getMessage());
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
