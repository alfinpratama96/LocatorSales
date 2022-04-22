package com.task.locatorptm.presentation.history;

import android.content.Intent;
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

import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.activity.ActivityData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.FragmentActivityLogBinding;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;
import com.task.locatorptm.presentation.history.pinlocation.GoogleMapsActivity;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActivityLogFragment extends Fragment implements ActivityLogContract{

    private FragmentActivityLogBinding binding;
    ActivityLogPresenter presenter;
    @Inject
    ActivitiesUseCase activitiesUseCase;
    private AppPreference preference;
    private ActivityLogAdapter adapter;
    private ActivityData data;

    public ActivityLogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        presenter = new ActivityLogPresenter(this, activitiesUseCase);
        preference = new AppPreference(requireActivity());
        return inflater.inflate(R.layout.fragment_activity_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentActivityLogBinding.bind(view);
        initRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getLogs();
        new ItemTouchHelper(touchCallback).attachToRecyclerView(binding.rvActivityLog);
    }

    private void getLogs() {
        if (AppUtil.isNetworkAvailable(requireActivity())) {
            presenter.getActivities(preference.getUserId());
        } else errConn();
    }

    private void initRecyclerView() {
        adapter = new ActivityLogAdapter();
        binding.rvActivityLog.setAdapter(adapter);
        binding.rvActivityLog.setLayoutManager(new LinearLayoutManager(requireActivity()));
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
            data = adapter.differ.getCurrentList().get(pos);
            if (AppUtil.isNetworkAvailable(requireActivity())) {
                presenter.deleteActivity(String.valueOf(data.getId()));
            } else errConn();
        }
    };

    private void openMap() {
        Intent i = new Intent(requireActivity(), GoogleMapsActivity.class);
        startActivity(i);
    }

    @Override
    public void showLoading() {
        requireActivity().runOnUiThread(() -> {
            binding.rvActivityLog.setVisibility(View.GONE);
            binding.pbLoadingLog.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        requireActivity().runOnUiThread(() -> binding.pbLoadingLog.setVisibility(View.GONE));
    }

    @Override
    public void showActivities(ArrayList<ActivityData> list) {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
            binding.btnMap.setOnClickListener(btnMap -> openMap());
            if (list.isEmpty()) {
                binding.tvNoData.setVisibility(View.VISIBLE);
                binding.fabPinLoc.setVisibility(View.GONE);
            } else {
                binding.tvNoData.setVisibility(View.GONE);
                adapter.differ.submitList(list);
                binding.rvActivityLog.setVisibility(View.VISIBLE);
                binding.fabPinLoc.setVisibility(View.VISIBLE);
                binding.fabPinLoc.setOnClickListener(fabPin -> openMap());
            }
        });
    }

    @Override
    public void successDelete(String message) {
//        requireActivity().runOnUiThread(() -> {
//            View parentLayout = requireActivity().findViewById(android.R.id.content);
//            Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG)
//                    .setAction("Undo", view -> {
//                        if (AppUtil.isNetworkAvailable(requireActivity())) {
//                            HashMap<String, String> map = new HashMap<>();
//                            map.put("lat", data.getLat());
//                            map.put("lng", data.getLng());
//                            map.put("address", data.getAddress());
//                            map.put("userId", String.valueOf(data.getUserId()));
//                            presenter.createActivity(map);
//                        } else errConn();
//                    })
//                    .show();
//        });
//        getLogs();
    }

    @Override
    public void successCreate() {
        getLogs();
    }

    @Override
    public void showError(String message) {
        requireActivity().runOnUiThread(() -> {
            hideLoading();
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
                            presenter.getActivities(preference.getUserId());
                        } else errConn();
                    })
                    .show();
        });
    }
}