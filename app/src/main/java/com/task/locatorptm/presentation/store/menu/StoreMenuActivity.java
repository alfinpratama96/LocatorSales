package com.task.locatorptm.presentation.store.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.task.locatorptm.databinding.ActivityStoreMenuBinding;
import com.task.locatorptm.presentation.absence.list.AbsenceListActivity;
import com.task.locatorptm.presentation.activity.list.ActionListActivity;
import com.task.locatorptm.presentation.order.list.OrderListActivity;
import com.task.locatorptm.presentation.schedule.list.ScheduleListActivity;

import java.util.Objects;

public class StoreMenuActivity extends AppCompatActivity {

    //Const Store
    public static final String STORE = "STORE";
    public static final String STORE_ID = "STORE_ID";
    private int storeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStoreMenuBinding binding = ActivityStoreMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setAppBar();
        storeId = getIntent().getIntExtra(STORE_ID, 0);
        binding.cvAbsence.setOnClickListener(cvAbsence -> {
            Intent i = new Intent(this, AbsenceListActivity.class);
            i.putExtra(AbsenceListActivity.STORE_ID, storeId);
            startActivity(i);
        });
        binding.cvOrder.setOnClickListener(cvOrder -> {
            Intent i = new Intent(this, OrderListActivity.class);
            i.putExtra(OrderListActivity.STORE_ID, storeId);
            startActivity(i);
        });
        binding.cvActivity.setOnClickListener(cvActivity -> {
            Intent i = new Intent(this, ActionListActivity.class);
            i.putExtra(ActionListActivity.STORE_ID, storeId);
            startActivity(i);
        });
        binding.cvSchedule.setOnClickListener(view -> {
            Intent i = new Intent(this, ScheduleListActivity.class);
            i.putExtra(ScheduleListActivity.STORE_ID, storeId);
            startActivity(i);
        });
    }

    private void setAppBar() {
        String store = getIntent().getStringExtra(STORE);
        Objects.requireNonNull(getSupportActionBar()).setTitle(store);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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