package com.task.locatorptm.presentation.history;

import com.task.locatorptm.data.models.activity.ActivityData;

import java.util.ArrayList;

public interface ActivityLogContract {
    void showLoading();
    void hideLoading();
    void showActivities(ArrayList<ActivityData> list);
    void successDelete(String message);
    void successCreate();
    void showError(String message);
}
