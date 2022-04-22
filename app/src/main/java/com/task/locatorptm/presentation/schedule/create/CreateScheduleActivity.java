package com.task.locatorptm.presentation.schedule.create;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.schedule.ScheduleData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityCreateScheduleBinding;
import com.task.locatorptm.databinding.CreateScheduleItemBinding;
import com.task.locatorptm.domain.usecase.schedule.ScheduleUseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateScheduleActivity extends AppCompatActivity implements CreateScheduleContract {

    public static final String STORE_ID = "STORE_ID";
    public static final String SCHEDULE = "SCHEDULE";
    public static final String IS_FROM_EDIT = "IS_FROM_EDIT";
    private int storeId = 0;
    private ScheduleData data;
    private boolean isFromEdit = false;

    @Inject
    ScheduleUseCase useCase;
    private ActivityCreateScheduleBinding binding;
    private AppPreference pref;
    private CreateSchedulePresenter presenter;
    private ArrayList<String> days = new ArrayList<>();
    private final HashMap<String, String> map = new HashMap<>();
    private String joinedDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateScheduleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storeId = getIntent().getIntExtra(STORE_ID, 0);
        data = getIntent().getParcelableExtra(SCHEDULE);
        isFromEdit = getIntent().getBooleanExtra(IS_FROM_EDIT, false);
        pref = new AppPreference(this);
        presenter = new CreateSchedulePresenter(this, useCase);

        setAppBar();
        initRecyclerView();

        binding.btnAddSchedule.setOnClickListener(view -> createOrUpdateSchedule());
    }

    @SuppressLint("SetTextI18n")
    private void setAppBar() {
        if (isFromEdit) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Update Schedule");
            binding.btnAddSchedule.setText("Edit Schedule");
            days = new ArrayList<>(Arrays.asList(data.getDay().split(",")));
            joinedDays = TextUtils.join(",", days);
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Create Schedule");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        CreateScheduleAdapter adapter = new CreateScheduleAdapter(this::tapListener, days);
        binding.rvScheduleList.setAdapter(adapter);
        binding.rvScheduleList.setLayoutManager(new GridLayoutManager(this, 3));
        adapter.differ.submitList(AppUtil.days);
    }

    private void tapListener(String day, CreateScheduleItemBinding binding) {
        if (!days.contains(day)) {
            days.add(day);
            binding.cvDay.setCardBackgroundColor(getResources().getColor(R.color.purple_700));
        } else {
            days.remove(day);
            binding.cvDay.setCardBackgroundColor(getResources().getColor(R.color.limeDark));
        }
        joinedDays = TextUtils.join(",", days);
    }

    private void createOrUpdateSchedule() {
        map.put("day", joinedDays);
        map.put("userId", pref.getUserId());
        map.put("storeId", String.valueOf(storeId));
        if (AppUtil.isNetworkAvailable(this)) {
            if (isFromEdit) {
                map.put("id", String.valueOf(data.getId()));
                presenter.update(map);
            }
            else presenter.create(map);
        } else errConn();
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.btnAddSchedule.setVisibility(View.GONE);
            binding.pbLoadingAddSchedule.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            binding.btnAddSchedule.setVisibility(View.VISIBLE);
            binding.pbLoadingAddSchedule.setVisibility(View.GONE);
        });
    }

    @Override
    public void success(String message) {
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
                    .setAction("Try Again", view -> createOrUpdateSchedule())
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