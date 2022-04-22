package com.task.locatorptm.presentation.store.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.store.StoreData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.CreateStoreLayoutBinding;
import com.task.locatorptm.databinding.FragmentStoreListBinding;
import com.task.locatorptm.domain.usecase.store.StoreUseCase;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StoreListFragment extends Fragment implements StoreListContract {

    @Inject
    StoreUseCase useCase;
    private FragmentStoreListBinding binding;
    private BottomSheetDialog bsd;
    private StoreListPresenter presenter;
    private AppPreference pref;
    private StoreListAdapter adapter;
    private CreateStoreLayoutBinding createStoreLayoutBinding;

    private StoreData data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new StoreListPresenter(this, useCase);
        pref = new AppPreference(getActivity());
        return inflater.inflate(R.layout.fragment_store_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentStoreListBinding.bind(view);
        initRecyclerView();
        initCreateStoreDialog();
    }

    private void getStores() {
        if (AppUtil.isNetworkAvailable(requireActivity())) {
            presenter.getStoreList(pref.getUserId());
        } else errConn();
    }

    private void initRecyclerView() {
        adapter = new StoreListAdapter();
        binding.rvStoreList.setAdapter(adapter);
        binding.rvStoreList.setLayoutManager(new LinearLayoutManager(requireActivity()));
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
            StoreData storeData = adapter.differ.getCurrentList().get(pos);
            data = storeData;
            if (AppUtil.isNetworkAvailable(requireActivity())) {
                presenter.delete(storeData.getId());
            } else errConn();
        }
    };

    private void initCreateStoreDialog() {
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        createStoreLayoutBinding = CreateStoreLayoutBinding.inflate(inflater);
        bsd = new BottomSheetDialog(requireActivity());
        bsd.setContentView(createStoreLayoutBinding.getRoot());
        createStoreLayoutBinding.btnAddStoreSbs.setOnClickListener(btnAdd -> {
            HashMap<String, String> map = new HashMap<>();
            if (createStoreLayoutBinding.etStoreName.getText() == null) {
                createStoreLayoutBinding.etStoreName.setError("Must be filled");
            } else {
                map.put("name", createStoreLayoutBinding.etStoreName.getText().toString());
                map.put("userId", pref.getUserId());
                if (AppUtil.isNetworkAvailable(requireActivity())) {
                    presenter.create(map);
                    createStoreLayoutBinding.btnAddStoreSbs.setEnabled(false);
                } else errConn();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getStores();
        new ItemTouchHelper(touchCallback).attachToRecyclerView(binding.rvStoreList);
    }

    @Override
    public void showLoading() {
        requireActivity().runOnUiThread(() -> {
            binding.rvStoreList.setVisibility(View.GONE);
            binding.pbLoadingStoreList.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        requireActivity().runOnUiThread(() -> binding.pbLoadingStoreList.setVisibility(View.GONE));
    }

    @Override
    public void showStoreListData(ArrayList<StoreData> list) {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
            if (list.isEmpty()) {
                binding.llNoData.setVisibility(View.VISIBLE);
                binding.fabAddStore.setVisibility(View.GONE);
                binding.btnAddStore.setVisibility(View.VISIBLE);
                binding.btnAddStore.setOnClickListener(view -> bsd.show());
            } else {
                binding.llNoData.setVisibility(View.GONE);
                binding.fabAddStore.setVisibility(View.VISIBLE);
                binding.fabAddStore.setOnClickListener(view -> bsd.show());
                adapter.differ.submitList(list);
                binding.rvStoreList.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void successDelete(String message) {
        requireActivity().runOnUiThread(() -> {
            createStoreLayoutBinding.btnAddStoreSbs.setEnabled(true);
            View parentLayout = requireActivity().findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setAction("Undo", view -> {
                        if (AppUtil.isNetworkAvailable(requireActivity())) {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", data.getName());
                            map.put("userId", pref.getUserId());
                            presenter.create(map);
                        } else errConn();
                    })
                    .show();
        });
        getStores();
    }

    @Override
    public void successCreate() {
        requireActivity().runOnUiThread(() -> {
            if (bsd.isShowing()) {
                bsd.dismiss();
                createStoreLayoutBinding.btnAddStoreSbs.setEnabled(true);
                createStoreLayoutBinding.etStoreName.setText("");
                View parentLayout = requireActivity().findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Store has been created", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
        getStores();
    }

    @Override
    public void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
            createStoreLayoutBinding.btnAddStoreSbs.setEnabled(true);
            View parentLayout = requireActivity().findViewById(android.R.id.content);
            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.red))
                    .show();
        });
    }

    private void errConn() {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
            View parentLayout = requireActivity().findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No connection. Please try again", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(requireActivity(), R.color.red))
                    .setAction("Try Again", view -> {
                        if (AppUtil.isNetworkAvailable(requireActivity())) {
                            presenter.getStoreList(pref.getUserId());
                        } else errConn();
                    })
                    .show();
        });
    }
}