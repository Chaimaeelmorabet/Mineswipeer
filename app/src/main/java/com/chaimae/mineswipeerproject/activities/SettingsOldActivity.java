package com.chaimae.mineswipeerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chaimae.mineswipeerproject.R;

public class SettingsOldActivity extends AppCompatActivity {
    public static final String tamany = "t";
    public static final String alies = "a";
    public static final String mines = "m";
    public static final String temps = "ct";
    public static final String result = "res";
    public static final String numMines = "numMines";
    public static String tempsP = "temps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_old);
    }


    public void goGame(View view) {
        Intent gameSettings = new Intent(this, GameActivity.class);

        String alies = ((EditText) findViewById(R.id.aliesJugador)).getText().toString();
        if (checkAliesValid(alies)) {
            showToast("El Alies no pot estar buit");
            return;
        }

        int tamany = getTamany();
        if (!checkTamanyGraella(tamany)) {
            showToast("El tamany ha de ser més gran que 1 i més petit que 10");
            return;
        }
        boolean controlTemps = ((CheckBox) findViewById(R.id.controlTemps)).isChecked();

        int radioButtonString = getPercentatge();

        gameSettings.putExtra(SettingsOldActivity.alies, alies);
        gameSettings.putExtra(SettingsOldActivity.tamany, tamany);
        gameSettings.putExtra(SettingsOldActivity.temps, controlTemps);
        gameSettings.putExtra(SettingsOldActivity.mines, radioButtonString);
        startActivity(gameSettings);
        overridePendingTransition(android.R.anim.slide_in_left, 0);
        finish();
    }

    private boolean checkTamanyGraella(int tamany) {
        return  1 < tamany && tamany < 10;
    }

    private void showToast(String s) {
        Toast.makeText(SettingsOldActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private boolean checkAliesValid(String alies) {
        return alies.equals("");
    }


    public int getTamany() {
        String s = ((EditText) findViewById(R.id.tamany)).getText().toString();
        return s.equals("") ? -1 : Integer.parseInt(s);
    }


    private int getPercentatge() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.mines);
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
        String s = radioButton.getText().toString();
        return Integer.parseInt(s.substring(0, s.length() - 1)); // Borro el % de la String
    }


}
