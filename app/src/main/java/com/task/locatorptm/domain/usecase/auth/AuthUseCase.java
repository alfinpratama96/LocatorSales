package com.task.locatorptm.domain.usecase.auth;

import com.task.locatorptm.data.models.auth.AuthResponse;
import com.task.locatorptm.data.utils.Resource;
import com.task.locatorptm.domain.repository.AppRepository;

import java.util.Map;

public class AuthUseCase {
    private final AppRepository repository;

    public AuthUseCase(AppRepository repository) {
        this.repository = repository;
    }

    public Resource<AuthResponse> execute(Map<String, String> map) {
        return repository.login(map);
    }

    public Resource<AuthResponse> execLogout(Map<String, String> map) {
        return repository.logout(map);
    }
}
