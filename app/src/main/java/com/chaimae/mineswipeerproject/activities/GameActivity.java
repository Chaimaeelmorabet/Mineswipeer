package com.chaimae.mineswipeerproject.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.fragments.GameFragment.OnCreateLogListener;
import com.chaimae.mineswipeerproject.fragments.LogFragment;
import com.chaimae.mineswipeerproject.serializable.Log;

public class GameActivity extends AppCompatActivity implements OnCreateLogListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public void writeLog(String text) {
        Log.getInstance().writeLog(text);
        LogFragment fragment = (LogFragment) getSupportFragmentManager().findFragmentById(R.id.log_fragment);
        if (fragment != null && fragment.isInLayout()) {
            fragment.update(text);}
    }
}
