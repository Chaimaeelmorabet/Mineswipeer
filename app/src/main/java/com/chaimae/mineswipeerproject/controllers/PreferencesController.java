package com.chaimae.mineswipeerproject.controllers;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.chaimae.mineswipeerproject.R;

/**
 * Created by Chaiame on 14/06/2016.
 */
public class PreferencesController {
    private final SharedPreferences preferences;
    private final Activity a;

    public PreferencesController(Activity a) {
        this.a = a;
        preferences = PreferenceManager.getDefaultSharedPreferences(a);
    }

    public String getAlies() {
        return preferences.getString(getStringResource(R.string.alies_key), getStringResource(R.string.defaultalies));
    }

    public int getTamanyGraella() {
        String s = preferences.getString(getStringResource(R.string.tamany_key), "2");
        return Integer.valueOf(s);
    }

    public boolean isControlTemps() {
        return preferences.getBoolean(getStringResource(R.string.control_temps_key), false);
    }

    public int getPercentMines() {
        String s = preferences.getString(getStringResource(R.string.mines_key), getStringResource(R.string.defaultmines));
        return Integer.valueOf(s);
    }
    public boolean useVibrate(){
        return preferences.getBoolean(getStringResource(R.string.vibrar_key), false);
    }
    public boolean useSound(){
        return preferences.getBoolean(getStringResource(R.string.so_key), false);
    }


    private String getStringResource(int id) {
        return a.getString(id);
    }
}
