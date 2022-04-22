package com.task.locatorptm.presentation.order.create;

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
import com.task.locatorptm.data.models.order.OrderData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.databinding.ActivityCreateOrderBinding;
import com.task.locatorptm.domain.usecase.order.OrderUseCase;

import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateOrderActivity extends AppCompatActivity implements CreateOrderContract{

    public static final String STORE_ID = "STORE_ID";
    private int storeId = 0;

    @Inject
    OrderUseCase useCase;
    private ActivityCreateOrderBinding binding;
    private CreateOrderPresenter presenter;
    private AppPreference pref;
    private int qty = 0;
    private boolean isEdit = false;
    private final HashMap<String, String> map = new HashMap<>();
    private OrderData data;

    public static String EDIT_KEY = "EDIT_KEY";
    public static String PARCELABLE = "PARCELABLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isEdit = getIntent().getBooleanExtra(EDIT_KEY, false);

        if (isEdit) setCurrentData();
        presenter = new CreateOrderPresenter(this, useCase);
        pref = new AppPreference(this);
        storeId = getIntent().getIntExtra(STORE_ID, 0);

        setAppBar();
        binding.btnAddOrder.setOnClickListener(addOrder -> addOrder());
    }

    @Override
    protected void onResume() {
        super.onResume();
        addRemoveQty();
    }

    @SuppressLint("SetTextI18n")
    private void setCurrentData() {
        data = getIntent().getParcelableExtra(PARCELABLE);
        binding.btnAddOrder.setText("Edit Order");
        binding.etOrderName.setText(data.getOrderName());
        qty = data.getQuantity();
        binding.tvQuantity.setText(String.valueOf(qty));
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Order");
        if (isEdit) getSupportActionBar().setTitle("Edit Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void addRemoveQty() {
        binding.tvQuantity.setText(String.valueOf(qty));
        binding.btnAddQty.setOnClickListener(add -> {
            qty = qty+1;
            binding.tvQuantity.setText(String.valueOf(qty));
        });
        binding.btnRemoveQty.setOnClickListener(remove -> {
            if (qty > 0) qty = qty-1;
            binding.tvQuantity.setText(String.valueOf(qty));
        });
    }

    private void addOrder() {
        String order = binding.etOrderName.getText().toString();

        if (order.equals("")) {
            binding.etOrderName.setError("Order must be filled");
        }  if (qty == 0) {
            Toast.makeText(this, "Quantity cannot be 0", Toast.LENGTH_SHORT).show();
        } if (!order.equals("") && qty != 0) {
            map.put("name", order);
            map.put("quantity", String.valueOf(qty));
            map.put("userId", pref.getUserId());
            map.put("storeId", String.valueOf(storeId));
            if (isEdit) {
                map.put("id", String.valueOf(data.getId()));
                presenter.updateOrder(map);
            } else presenter.createOrder(map);
        }
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.btnAddOrder.setVisibility(View.GONE);
            binding.pbLoadingCreateOrder.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            binding.btnAddOrder.setVisibility(View.VISIBLE);
            binding.pbLoadingCreateOrder.setVisibility(View.GONE);
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
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
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