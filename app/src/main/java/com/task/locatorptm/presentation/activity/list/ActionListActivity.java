package com.task.locatorptm.presentation.activity.list;

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
import com.task.locatorptm.data.models.activity.ActivityData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityActionListBinding;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;
import com.task.locatorptm.presentation.activity.create.CreateActionActivity;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActionListActivity extends AppCompatActivity implements ActionListContract {

    public static final String STORE_ID = "STORE_ID";
    private int storeId = 0;

    @Inject
    ActivitiesUseCase useCase;
    private ActivityActionListBinding binding;
    private AppPreference pref;
    private ActionListPresenter presenter;
    private ActionListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActionListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storeId = getIntent().getIntExtra(STORE_ID, 0);
        pref = new AppPreference(this);
        presenter = new ActionListPresenter(this, useCase);

        setAppBar();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActivities();
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Activity List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        adapter = new ActionListAdapter();
        binding.rvActivityList.setAdapter(adapter);
        binding.rvActivityList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getActivities() {
        if (AppUtil.isNetworkAvailable(this)) {
            presenter.get(pref.getUserId(), String.valueOf(storeId));
        } else errConn();
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.pbLoadingActivityList.setVisibility(View.VISIBLE);
            binding.rvActivityList.setVisibility(View.GONE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> binding.pbLoadingActivityList.setVisibility(View.GONE));
    }

    @Override
    public void showActivityList(ArrayList<ActivityData> list) {
        runOnUiThread(() -> {
            hideLoading();
            if (list.isEmpty()) {
                binding.llNoData.setVisibility(View.VISIBLE);
                binding.fabAddActivity.setVisibility(View.GONE);
                binding.btnAddActivity.setVisibility(View.VISIBLE);
                binding.btnAddActivity.setOnClickListener(view -> {
                    Intent i = new Intent(this, CreateActionActivity.class);
                    i.putExtra(CreateActionActivity.IS_UPDATE, false);
                    i.putExtra(CreateActionActivity.STORE_ID, storeId);
                    startActivity(i);
                });
            } else {
                binding.llNoData.setVisibility(View.GONE);
                binding.fabAddActivity.setVisibility(View.VISIBLE);
                binding.fabAddActivity.setOnClickListener(view -> {
                    Intent i = new Intent(this, CreateActionActivity.class);
                    i.putExtra(CreateActionActivity.IS_UPDATE, false);
                    i.putExtra(CreateActionActivity.STORE_ID, storeId);
                    startActivity(i);
                });
                adapter.differ.submitList(list);
                binding.rvActivityList.setVisibility(View.VISIBLE);
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
                    .setAction("Try Again", view -> {
                        if (AppUtil.isNetworkAvailable(this)) {
                            presenter.get(pref.getUserId(), String.valueOf(storeId));
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