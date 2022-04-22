package com.task.locatorptm.data.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.task.locatorptm.data.models.auth.AuthData;

public class AppPreference {
    final Context context;
    SharedPreferences pref;

    public AppPreference(Context context) {
        this.context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLogin(AuthData data) {
        SharedPreferences.Editor idPref = this.pref.edit();
        idPref.putString(userId, ""+data.getId());
        idPref.apply();

        SharedPreferences.Editor usernamePref = this.pref.edit();
        usernamePref.putString(username, data.getUsername());
        usernamePref.apply();

        SharedPreferences.Editor idCardPref = this.pref.edit();
        idCardPref.putString(idCard, data.getIdCard());
        idCardPref.apply();

        SharedPreferences.Editor imgPref = this.pref.edit();
        imgPref.putString(img, data.getImg());
        imgPref.apply();

        SharedPreferences.Editor namePref = this.pref.edit();
        namePref.putString(name, data.getName());
        namePref.apply();

        SharedPreferences.Editor emailPref = this.pref.edit();
        emailPref.putString(email, data.getEmail());
        emailPref.apply();
    }

    public void removeLogin() {
        SharedPreferences.Editor idPref = this.pref.edit();
        idPref.putString(userId, "");
        idPref.apply();

        SharedPreferences.Editor usernamePref = this.pref.edit();
        usernamePref.putString(username, "");
        usernamePref.apply();

        SharedPreferences.Editor idCardPref = this.pref.edit();
        idCardPref.putString(idCard, "");
        idCardPref.apply();

        SharedPreferences.Editor imgPref = this.pref.edit();
        imgPref.putString(img, "");
        imgPref.apply();

        SharedPreferences.Editor namePref = this.pref.edit();
        namePref.putString(name, "");
        namePref.apply();

        SharedPreferences.Editor emailPref = this.pref.edit();
        emailPref.putString(email, "");
        emailPref.apply();
    }

    public String getUserId() {
        return this.pref.getString(userId, "");
    }

    public String getUsername() {
        return this.pref.getString(username, "");
    }

    public String getIdCard() { return this.pref.getString(idCard, ""); }

    public String getImg() {
        return this.pref.getString(img, "");
    }

    public String getName() {
        return this.pref.getString(name, "");
    }

    public  String getEmail() {
        return this.pref.getString(email, "");
    }

    //Static const of SharedPreferences
    private static final String userId = "USER_ID";
    private static final String username = "USERNAME";
    private static final String idCard = "ID_CARD";
    private static final String img = "IMG";
    private static final String name = "NAME";
    private static final String email = "EMAIL";
}
