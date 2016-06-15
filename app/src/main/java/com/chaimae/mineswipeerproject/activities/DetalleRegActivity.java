package com.chaimae.mineswipeerproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.fragments.RegistroFrag;

public class DetalleRegActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reg);
        String text = getIntent().getStringExtra("text");
        RegistroFrag fragment = (RegistroFrag) getSupportFragmentManager().findFragmentById(R.id.detail_fragment);
        fragment.update(text);
    }
}
