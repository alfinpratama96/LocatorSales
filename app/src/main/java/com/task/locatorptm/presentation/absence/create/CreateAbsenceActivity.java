package com.task.locatorptm.presentation.absence.create;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityCreateAbsenceBinding;
import com.task.locatorptm.domain.usecase.absence.AbsenceUseCase;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@AndroidEntryPoint
public class CreateAbsenceActivity extends AppCompatActivity implements CreateAbsenceContract {

    public static final String STORE_ID = "STORE_ID";
    public static final String FILE = "FILE";
    private int storeId = 0;
    private File file;

    HashMap<String, RequestBody> map = new HashMap<>();

    @Inject
    AbsenceUseCase useCase;
    private ActivityCreateAbsenceBinding binding;
    private AppPreference pref;
    private CreateAbsencePresenter presenter;

    private FusedLocationProviderClient fused;
    private Location location;
    private double lat = 0.0;
    private double lng = 0.0;
    static final int PERMISSION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAbsenceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storeId = getIntent().getIntExtra(STORE_ID, 0);
        file = (File) getIntent().getSerializableExtra(FILE);
        pref = new AppPreference(this);
        presenter = new CreateAbsencePresenter(this, useCase);
        fused = LocationServices.getFusedLocationProviderClient(this);

        setAppBar();
        getLastLocation();

        binding.btnRecordAbsence.setOnClickListener(btnRecord -> {
            map.put("userId", RequestBody.create(MultipartBody.FORM, pref.getUserId()));
            map.put("storeId", RequestBody.create(MultipartBody.FORM, String.valueOf(storeId)));
            map.put("latLng", RequestBody.create(MultipartBody.FORM, ""+lat+","+lng));
            if (AppUtil.isNetworkAvailable(this)) {
                if (location.isFromMockProvider()) {
                    Toast.makeText(this, "You are using fake GPS!", Toast.LENGTH_SHORT).show();
                } else presenter.create(map, file);
            } else errConn();
        });
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Absence");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @SuppressLint("SetTextI18n")
    private void initValues() {
        final Geocoder geocoder = new Geocoder(this);
        Glide.with(binding.getRoot())
                .load(file)
                .fitCenter()
                .into(binding.ivAbsence);
        binding.tvAbsenceDate.setText(AppUtil.getDateTimeNow());
        binding.tvLatLngAbsence.setText("Current location : "+lat+","+lng);
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 5);
            binding.tvAbsenceAddress.setText(addressList.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get Current Location Section
    //===========================================================================================//

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

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fused.getLastLocation().addOnCompleteListener(task -> {
                    location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else if (location.isFromMockProvider()) {
                        Toast.makeText(this, "You are using fake GPS!", Toast.LENGTH_SHORT).show();
                    } else {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        initValues();
                    }
                });
            } else {
                Toast.makeText(this, "Turn on your location for tracking",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }



    private boolean isLocationEnabled() {
        LocationManager locationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        return Objects.requireNonNull(locationManager).isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(5)
                .setFastestInterval(0)
                .setNumUpdates(1);

        fused = LocationServices.getFusedLocationProviderClient(this);
        fused.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
        }
    };

    //===========================================================================================//

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.btnRecordAbsence.setVisibility(View.GONE);
            binding.pbLoadingCreateAbsence.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            binding.btnRecordAbsence.setVisibility(View.VISIBLE);
            binding.pbLoadingCreateAbsence.setVisibility(View.GONE);
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
                            presenter.create(map, file);
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