package com.task.locatorptm.presentation.schedule.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.schedule.ScheduleData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityScheduleListBinding;
import com.task.locatorptm.domain.usecase.schedule.ScheduleUseCase;
import com.task.locatorptm.presentation.schedule.create.CreateScheduleActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ScheduleListActivity extends AppCompatActivity implements ScheduleListContract {

    public static final String STORE_ID = "STORE_ID";
    private int storeId = 0;

    @Inject
    ScheduleUseCase useCase;
    private ActivityScheduleListBinding binding;
    private AppPreference pref;
    private ScheduleListPresenter presenter;
    private ScheduleListAdapter adapter;
    private String[] days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScheduleListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storeId = getIntent().getIntExtra(STORE_ID, 0);
        pref = new AppPreference(this);
        presenter = new ScheduleListPresenter(this, useCase);

        setAppBar();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSchedules();
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Visit Schedule List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        adapter = new ScheduleListAdapter();
        binding.rvScheduleList.setAdapter(adapter);
        binding.rvScheduleList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getSchedules() {
        if (AppUtil.isNetworkAvailable(this)) {
            presenter.get(pref.getUserId(), String.valueOf(storeId));
        } else errConn();
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.pbLoadingScheduleList.setVisibility(View.VISIBLE);
            binding.rvScheduleList.setVisibility(View.GONE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> binding.pbLoadingScheduleList.setVisibility(View.GONE));
    }

    @Override
    public void showScheduleList(ArrayList<ScheduleData> list) {
        runOnUiThread(() -> {
            hideLoading();
            if (list.isEmpty()) {
                binding.llNoData.setVisibility(View.VISIBLE);
                binding.fabAddSchedule.setVisibility(View.GONE);
                binding.fabEditSchedule.setVisibility(View.GONE);
                binding.btnAddSchedule.setVisibility(View.VISIBLE);
                binding.btnAddSchedule.setOnClickListener(view -> {
                    Intent i = new Intent(this, CreateScheduleActivity.class);
                    i.putExtra(CreateScheduleActivity.STORE_ID, storeId);
                    i.putExtra(CreateScheduleActivity.IS_FROM_EDIT, false);
                    startActivity(i);
                });
            } else {
                days = list.get(0).getDay().split(",");
                binding.llNoData.setVisibility(View.GONE);
                binding.fabEditSchedule.setVisibility(View.VISIBLE);
                binding.fabAddSchedule.setVisibility(View.GONE);
                binding.fabEditSchedule.setOnClickListener(view -> {
                    Intent i = new Intent(this, CreateScheduleActivity.class);
                    i.putExtra(CreateScheduleActivity.STORE_ID, storeId);
                    i.putExtra(CreateScheduleActivity.IS_FROM_EDIT, true);
                    i.putExtra(CreateScheduleActivity.SCHEDULE, list.get(0));
                    startActivity(i);
                });
                adapter.differ.submitList(Arrays.asList(days));
                binding.rvScheduleList.setVisibility(View.VISIBLE);
            }
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
                    .setAction("Try Again", view -> getSchedules())
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