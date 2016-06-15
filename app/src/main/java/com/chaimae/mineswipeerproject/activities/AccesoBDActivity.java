package com.chaimae.mineswipeerproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.fragments.QueryFrag;
import com.chaimae.mineswipeerproject.fragments.RegistroFrag;

public class AccesoBDActivity extends AppCompatActivity implements QueryFrag.OnQueryClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_bd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onQueryClick(String text) {
        RegistroFrag fragment = (RegistroFrag) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        if (fragment != null && fragment.isInLayout()) { // Tablet
            fragment.update(text);
        } else { // Mobil
            Intent intent = new Intent(this, DetalleRegActivity.class);
            intent.putExtra("text", text);
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
