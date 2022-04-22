package com.task.locatorptm.presentation.schedule.list;

import com.task.locatorptm.data.models.schedule.ScheduleData;

import java.util.ArrayList;

public interface ScheduleListContract {
    void showLoading();
    void hideLoading();
    void showScheduleList(ArrayList<ScheduleData> list);
    void showError(String message);
}
