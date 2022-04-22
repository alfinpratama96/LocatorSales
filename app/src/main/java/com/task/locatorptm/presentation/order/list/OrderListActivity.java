package com.task.locatorptm.presentation.order.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.order.OrderData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityOrderListBinding;
import com.task.locatorptm.domain.usecase.order.OrderUseCase;
import com.task.locatorptm.presentation.order.create.CreateOrderActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderListActivity extends AppCompatActivity implements OrderListContract {

    public static final String STORE_ID = "STORE_ID";
    private int storeId = 0;

    @Inject
    OrderUseCase useCase;
    private ActivityOrderListBinding binding;
    private OrderListPresenter presenter;
    private AppPreference pref;
    private OrderListAdapter adapter;
    private OrderData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storeId = getIntent().getIntExtra(STORE_ID, 0);

        presenter = new OrderListPresenter(this, useCase);
        pref = new AppPreference(this);

        initRecyclerView();
        setAppBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrders();
        new ItemTouchHelper(touchCallback).attachToRecyclerView(binding.rvOrderList);
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Order List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        adapter = new OrderListAdapter();
        binding.rvOrderList.setAdapter(adapter);
        binding.rvOrderList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getOrders() {
        if (AppUtil.isNetworkAvailable(this)) {
            presenter.getOrders(pref.getUserId(), String.valueOf(storeId));
        } else errConn();
    }

    private final ItemTouchHelper.SimpleCallback touchCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP | ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
    ) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder,
                              @NonNull RecyclerView.ViewHolder target) { return true; }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            OrderData orderData = adapter.differ.getCurrentList().get(pos);
            data = orderData;
            if (AppUtil.isNetworkAvailable(getApplicationContext())) {
                presenter.deleteOrder(String.valueOf(orderData.getId()));
            } else errConn();
        }
    };

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.pbLoadingOrderList.setVisibility(View.VISIBLE);
            binding.rvOrderList.setVisibility(View.GONE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> binding.pbLoadingOrderList.setVisibility(View.GONE));
    }

    @Override
    public void showOrderList(ArrayList<OrderData> list) {
        runOnUiThread(() -> {
            hideLoading();
            if (list.isEmpty()) {
                binding.llNoData.setVisibility(View.VISIBLE);
                binding.fabAddOrder.setVisibility(View.GONE);
                binding.ivAddToCart.setOnClickListener(ivAdd -> {
                    Intent i = new Intent(this, CreateOrderActivity.class);
                    i.putExtra(CreateOrderActivity.STORE_ID, storeId);
                    startActivity(i);
                });
            } else {
                binding.llNoData.setVisibility(View.GONE);
                binding.fabAddOrder.setVisibility(View.VISIBLE);
                binding.fabAddOrder.setOnClickListener(fabAddOrder -> {
                    Intent i = new Intent(this, CreateOrderActivity.class);
                    i.putExtra(CreateOrderActivity.STORE_ID, storeId);
                    startActivity(i);
                });
                adapter.differ.submitList(list);
                binding.rvOrderList.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void successDelete(String message) {
        runOnUiThread(() -> {
            View parentLayout = this.findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setAction("Undo", view -> {
                        if (AppUtil.isNetworkAvailable(this)) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", data.getOrderName());
                            map.put("quantity", String.valueOf(data.getQuantity()));
                            map.put("userId", pref.getUserId());
                            map.put("storeId", String.valueOf(storeId));
                            presenter.createOrder(map);
                        } else errConn();
                    })
                    .show();
        });
        getOrders();
    }

    @Override
    public void successCreate() {
        getOrders();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void errConn() {
        runOnUiThread(() -> {
            hideLoading();
            View parentLayout = this.findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No connection. Please try again", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                    .setAction("Try Again", view -> {
                        if (AppUtil.isNetworkAvailable(this)) {
                            presenter.getOrders(pref.getUserId(), String.valueOf(storeId));
                        } else errConn();
                    })
                    .show();
        });
    }
}