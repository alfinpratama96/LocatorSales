package com.task.locatorptm.presentation.store.list;

import com.task.locatorptm.data.models.store.StoreData;

import java.util.ArrayList;

public interface StoreListContract {
    void showLoading();
    void hideLoading();
    void showStoreListData(ArrayList<StoreData> list);
    void successDelete(String message);
    void successCreate();
    void showError(String message);
}
