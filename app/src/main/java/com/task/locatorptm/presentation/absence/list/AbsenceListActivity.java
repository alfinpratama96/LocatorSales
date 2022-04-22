package com.task.locatorptm.presentation.absence.list;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.models.absence.AbsenceData;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityAbsenceListBinding;
import com.task.locatorptm.domain.usecase.absence.AbsenceUseCase;
import com.task.locatorptm.presentation.absence.create.CreateAbsenceActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AbsenceListActivity extends AppCompatActivity implements AbsenceListContract {

    public static final String STORE_ID = "STORE_ID";
    private int storeId = 0;

    @Inject
    AbsenceUseCase useCase;
    private ActivityAbsenceListBinding binding;
    private AbsenceListPresenter presenter;
    private AppPreference pref;
    private AbsenceListAdapter adapter;

    static final int PERMISSION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbsenceListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storeId = getIntent().getIntExtra(STORE_ID, 0);
        presenter = new AbsenceListPresenter(this, useCase);
        pref = new AppPreference(this);
        checkPermissions();

        setAppBar();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAbsences();
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Absence List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initRecyclerView() {
        adapter = new AbsenceListAdapter();
        binding.rvAbsenceList.setAdapter(adapter);
        binding.rvAbsenceList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getAbsences() {
        if (AppUtil.isNetworkAvailable(this)) {
            presenter.get(pref.getUserId(), String.valueOf(storeId));
        } else errConn();
    }

    private void takePicture() {
        ImagePicker.Companion.with(this)
                .compress(500)
                .cameraOnly()
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                File file = ImagePicker.Companion.getFile(data);
                Intent i = new Intent(this, CreateAbsenceActivity.class);
                i.putExtra(CreateAbsenceActivity.FILE, file);
                i.putExtra(CreateAbsenceActivity.STORE_ID, storeId);
                startActivity(i);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else Toast.makeText(this, "Picture not taken", Toast.LENGTH_SHORT).show();
    }

    //Get Latest Location Section
    //====================================================================================//


    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            }
        }
    }

    //====================================================================================//

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.pbLoadingAbsenceList.setVisibility(View.VISIBLE);
            binding.rvAbsenceList.setVisibility(View.GONE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> binding.pbLoadingAbsenceList.setVisibility(View.GONE));
    }

    @Override
    public void showAbsenceList(ArrayList<AbsenceData> list) {
        runOnUiThread(() -> {
            hideLoading();
            if (list.isEmpty()) {
                binding.llNoData.setVisibility(View.VISIBLE);
                binding.fabAddAbsence.setVisibility(View.GONE);
                binding.ivAddAbsence.setOnClickListener(ivAdd -> {
                    if (!checkPermissions()) requestPermissions();
                    else takePicture();
                });
            } else {
                binding.llNoData.setVisibility(View.GONE);
                binding.fabAddAbsence.setVisibility(View.VISIBLE);
                binding.fabAddAbsence.setOnClickListener(fabAdd -> {
                    if (!checkPermissions()) requestPermissions();
                    else takePicture();
                });
                adapter.differ.submitList(list);
                binding.rvAbsenceList.setVisibility(View.VISIBLE);
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