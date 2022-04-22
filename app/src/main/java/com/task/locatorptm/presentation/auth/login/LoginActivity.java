package com.task.locatorptm.presentation.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.MainActivity;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.auth.AuthData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.databinding.ActivityLoginBinding;
import com.task.locatorptm.domain.usecase.auth.AuthUseCase;

import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity implements LoginContract {

    ActivityLoginBinding binding;
    LoginPresenter presenter;
    @Inject
    AuthUseCase useCase;
    AppPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        presenter = new LoginPresenter(this, useCase);
        preference = new AppPreference(this);
        View views = binding.getRoot();
        setContentView(views);
        if (!preference.getUsername().equals("")) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        binding.btnLogin.setOnClickListener(view -> handleLogin());
    }

    private void handleLogin() {
        String username = binding.etUsername.getText().toString();
        String password = binding.etPassword.getText().toString();
        if (username.equals("")) {
            binding.etUsername.setError("Username must be filled");
        } if (password.equals("")) {
            binding.etPassword.setError("Password must be filled");
        } if (!username.isEmpty() && !password.isEmpty()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("username", username);
            map.put("password", password);
            runOnUiThread(() -> presenter.doLogin(map));
        }
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.btnLogin.setVisibility(View.GONE);
            binding.pbLoadingLogin.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.pbLoadingLogin.setVisibility(View.GONE);
        });
    }

    @Override
    public void successLogin(AuthData data) {
        runOnUiThread(() -> {
            hideLoading();
            preference.setLogin(data);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void showError(String message) {
        hideLoading();
        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                .show();
    }
}