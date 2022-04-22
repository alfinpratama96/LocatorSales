package com.task.locatorptm.presentation.activity.list;

import com.task.locatorptm.data.models.activity.ActivityData;

import java.util.ArrayList;

public interface ActionListContract {
    void showLoading();
    void hideLoading();
    void showActivityList(ArrayList<ActivityData> list);
    void showError(String message);
}
