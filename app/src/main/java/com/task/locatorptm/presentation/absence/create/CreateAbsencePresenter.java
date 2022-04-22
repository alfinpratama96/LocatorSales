package com.task.locatorptm.presentation.absence.create;

import android.util.Log;
import android.webkit.MimeTypeMap;

import com.task.locatorptm.data.models.absence.AbsencePostResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.usecase.absence.AbsenceUseCase;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateAbsencePresenter {
    private final CreateAbsenceContract contract;
    private final AbsenceUseCase useCase;

    @Inject
    public CreateAbsencePresenter(CreateAbsenceContract contract, AbsenceUseCase useCase) {
        this.contract = contract;
        this.useCase = useCase;
    }

    void create(Map<String, RequestBody> map, File file) {
        contract.showLoading();
        try {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            Thread thread = new Thread(() -> {
                Resource<AbsencePostResponse> result = useCase.execCreate(
                        map,
                        part
                );
                if (result instanceof Resource.Success) {
                    contract.successCreate(((Resource.Success<AbsencePostResponse>) result).getData().getMessage());
                } if (result instanceof Resource.Error) {
                    contract.showError(((Resource.Error<AbsencePostResponse>) result).getMessage());
                }
            });
            thread.start();
        } catch (Exception e) {
            contract.showError("Error : "+e);
        }
    }
}
