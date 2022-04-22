package com.task.locatorptm.presentation.absence.list;

import com.task.locatorptm.data.models.absence.AbsenceData;

import java.util.ArrayList;

public interface AbsenceListContract {
    void showLoading();
    void hideLoading();
    void showAbsenceList(ArrayList<AbsenceData> list);
    void showError(String message);
}
