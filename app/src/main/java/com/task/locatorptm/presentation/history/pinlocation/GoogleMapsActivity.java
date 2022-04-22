package com.task.locatorptm.presentation.history.pinlocation;

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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.task.locatorptm.R;
import com.task.locatorptm.data.utils.AppPreference;
import com.task.locatorptm.data.utils.AppUtil;
import com.task.locatorptm.databinding.ActivityGoogleMapsBinding;
import com.task.locatorptm.domain.usecase.history.ActivitiesUseCase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GoogleMapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        MapsContract
{

    private ActivityGoogleMapsBinding binding;
    private GoogleMap mMap;
    private FusedLocationProviderClient fused;
    private LatLng latLng;
    private AppPreference pref;
    private MapsPresenter presenter;

    @Inject
    ActivitiesUseCase useCase;

    //Fields
    private double lat = 0.0;
    private double lng = 0.0;
    private String address;
    private final HashMap<String, String> map = new HashMap<>();

    static final int PERMISSION_ID = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        setAppBar();
        pref = new AppPreference(this);
        presenter = new MapsPresenter(this, useCase);
        fused = LocationServices.getFusedLocationProviderClient(this);
        binding.btnSaveLocation.setOnClickListener(saveLoc -> {
            map.put("lat", String.valueOf(latLng.latitude));
            map.put("lng", String.valueOf(latLng.longitude));
            map.put("address", address);
            map.put("userId", pref.getUserId());
            if (AppUtil.isNetworkAvailable(this)) {
                presenter.createActivity(map);
            } else errConn();
        });
    }

    private void setAppBar() {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Pin A Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setCurrentLocation() {
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15f));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    //============Get Last Location and it's permissions===========//

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                fused.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        latLng = new LatLng(lat, lng);
                        setCurrentLocation();
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
            latLng = new LatLng(lat, lng);
            setCurrentLocation();
        }
    };

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

    private boolean isLocationEnabled() {
        LocationManager locationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

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
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getLastLocation();
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);
    }

    //=========Listen to camera move events==========//

    @Override
    public void onCameraIdle() {
        binding.btnSaveLocation.setEnabled(true);
        latLng = mMap.getCameraPosition().target;
        final Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 5);
            address = addressList.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {
        binding.btnSaveLocation.setEnabled(false);
    }

    @Override
    public void onCameraMoveStarted(int i) {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } return super.onOptionsItemSelected(item);
    }

    //============Maps Contract=============//

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            binding.btnSaveLocation.setVisibility(View.GONE);
            binding.pbLoadingMaps.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            binding.btnSaveLocation.setVisibility(View.VISIBLE);
            binding.pbLoadingMaps.setVisibility(View.GONE);
        });
    }

    @Override
    public void successCreate(String message) {
        runOnUiThread(() -> {
            hideLoading();
            finish();
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    private void errConn() {
        runOnUiThread(() -> {
            hideLoading();
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No connection. Please try again", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                    .setAction("Try Again", view -> {
                        if (AppUtil.isNetworkAvailable(this)) {
                            presenter.createActivity(map);
                        } else errConn();
                    })
                    .show();
        });
    }
}