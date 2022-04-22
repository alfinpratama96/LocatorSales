package com.task.locatorptm.presentation.absence.detail;

import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.task.locatorptm.data.models.absence.AbsenceData;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityDetailAbsenceBinding;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DetailAbsenceActivity extends AppCompatActivity {

    public static final String ABSENCE_DATA = "ABSENCE_DATA";
    private AbsenceData data;

    private ActivityDetailAbsenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailAbsenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        data = getIntent().getParcelableExtra(ABSENCE_DATA);

        setAppBar();
        initView();
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Detail Absence");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        final Geocoder geocoder = new Geocoder(this);
        Glide.with(binding.getRoot())
                .load(data.getImg())
                .fitCenter()
                .into(binding.ivAbsence);
        binding.tvAbsenceDate.setText(AppUtil.convertDateTime(data.getCreatedAt()));
        String[] split = data.getLatLng().split(",");
        binding.tvLatLngAbsence.setText("Current location : "+split[0]+","+split[1]);
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    Double.parseDouble(split[0]), Double.parseDouble(split[1]), 5
            );
            binding.tvAbsenceAddress.setText(addressList.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
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