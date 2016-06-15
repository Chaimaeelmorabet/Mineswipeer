package com.chaimae.mineswipeerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.serializable.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void help(View view) {
        Intent goHelp = new Intent(this, HelpActivity.class);
        startActivity(goHelp);
    }

    public void play(View view) {
        Log.reset();
        Intent playGame = new Intent(this, GameActivity.class);
        startActivity(playGame);
        finish();
    }

    public void exit(View view) {
        finish();
    }

    public void partides(View view) {
        Intent partides = new Intent(this, AccesoBDActivity.class);
        startActivity(partides);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Això afegeix els items creats a la barra de menu
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
