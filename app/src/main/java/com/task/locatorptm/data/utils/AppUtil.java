package com.task.locatorptm.data.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class AppUtil {
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) return false;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = connectivityManager.
                        getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }
                    return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        }
        return false;
    }

    public static boolean isMockSettingsON(Context context) {
        if (Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
            return false;
        else
            return true;
    }

    public static boolean areThereMockPermissionApps(Context context) {
        int count = 0;

        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages =
                pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages) {
            try {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                        PackageManager.GET_PERMISSIONS);

                String[] requestedPermissions = packageInfo.requestedPermissions;

                if (requestedPermissions != null) {
                    for (String requestedPermission : requestedPermissions) {
                        if (requestedPermission
                                .equals("android.permission.ACCESS_MOCK_LOCATION")
                                && !applicationInfo.packageName.equals(context.getPackageName())) {
                            count++;
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("NotFoundException" , e.getMessage());
            }
        }

        return count > 0;
    }

    public static boolean isMockLocationOn(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return "0".equals(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION));
        } else {
            PackageManager pm = context.getPackageManager();
            List<ApplicationInfo> packages =
                    pm.getInstalledApplications(PackageManager.GET_META_DATA);

            if (packages != null) {
                for (ApplicationInfo applicationInfo : packages) {
                    try {
                        PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName,
                                PackageManager.GET_PERMISSIONS);

                        // Get Permissions
                        String[] requestedPermissions = packageInfo.requestedPermissions;

                        if (requestedPermissions != null) {
                            for (int i = 0; i < requestedPermissions.length; i++) {
                                if (requestedPermissions[i]
                                        .equals("android.permission.ACCESS_MOCK_LOCATION")
                                        && !applicationInfo.packageName.equals(context.getPackageName())) {
                                    return true;
                                }
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("MockLocationError", e.getMessage());
                    }
                }
            }

            return false;
        }
    }

    public static String errorMessage(String errorBody) {
        try {
            JSONObject errBody = new JSONObject(errorBody);
            return errBody.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
            return "Something went wrong";
        }
    }

    public static String convertDateTime(String dateTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date dt = sdf.parse(dateTime);
            sdf.setTimeZone(TimeZone.getDefault());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("EEEE, d MMMM yyyy");
            String a = formatDate.format(dt);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
            String b = formatTime.format(dt);
            return a+" at "+b;
        } catch (ParseException e) {
            e.printStackTrace();
            return "Undefined Date";
        }
    }

    public static String getDateTimeNow() {
        Date dt = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("EEEE, d MMMM yyyy");
        String a = formatDate.format(dt);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        String b = formatTime.format(dt);
        return a+" at "+b;
    }

    public static ArrayList<String> days = new ArrayList<>(
            Arrays.asList(
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday",
                    "Friday",
                    "Saturday",
                    "Sunday"
            )
    );
}
