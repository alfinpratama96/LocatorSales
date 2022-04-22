package com.task.locatorptm.presentation.activity.create;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.activity.ActivityData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityCreateActionBinding;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateActionActivity extends AppCompatActivity implements CreateActionContract {

    public static final String STORE_ID = "STORE_ID";
    public static final String IS_UPDATE = "IS_UPDATE";
    public static final String ACTIVITY_DATA = "ACTIVITY_DATA";
    private int storeId = 0;
    private boolean isUpdate = false;
    private ActivityData data;

    @Inject
    ActivitiesUseCase useCase;
    private ActivityCreateActionBinding binding;
    private AppPreference pref;
    private CreateActionPresenter presenter;

    private final HashMap<String, String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateActionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storeId = getIntent().getIntExtra(STORE_ID, 0);
        isUpdate = getIntent().getBooleanExtra(IS_UPDATE, false);
        data = getIntent().getParcelableExtra(ACTIVITY_DATA);
        pref = new AppPreference(this);
        presenter = new CreateActionPresenter(this, useCase);

        setView();
        onTapCreate();
    }

    @SuppressLint("SetTextI18n")
    private void setView() {
        if (isUpdate) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Update Activity");
            binding.btnAddActivity.setText("Update Activity");
            binding.etActivity.setText(data.getActivity());
        }
        else Objects.requireNonNull(getSupportActionBar()).setTitle("Create Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void onTapCreate() {
        binding.btnAddActivity.setOnClickListener(view -> {
            String activity = binding.etActivity.getText().toString();
            if (activity.isEmpty()) {
                binding.etActivity.setError("Activity must be filled");
            } else {
                map.put("activity", activity);
                map.put("userId", pref.getUserId());
                map.put("storeId", String.valueOf(storeId));
                if (AppUtil.isNetworkAvailable(this)) {
                    if (!isUpdate) presenter.create(map);
                    else {
                        map.put("id", String.valueOf(data.getId()));
                        presenter.update(map);
                    }
                } else errConn();
            }
        });
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.btnAddActivity.setVisibility(View.GONE);
            binding.pbLoadingAddActivity.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            binding.btnAddActivity.setVisibility(View.VISIBLE);
            binding.pbLoadingAddActivity.setVisibility(View.GONE);
        });
    }

    @Override
    public void successCreate(String message) {
        runOnUiThread(() -> {
            hideLoading();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    public void showError(String message) {
        runOnUiThread(() -> {
            hideLoading();
            View parentLayout = this.findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                    .show();
        });
    }

    private void errConn() {
        runOnUiThread(() -> {
            hideLoading();
            View parentLayout = this.findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No connection. Please try again", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                    .setAction("Try Again", view -> {
                        if (AppUtil.isNetworkAvailable(this)) {
                            presenter.create(map);
                        } else errConn();
                    })
                    .show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}