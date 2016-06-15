package com.chaimae.mineswipeerproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chaimae.mineswipeerproject.R;

/**
 * Created by Chaimae on 13/06/2016.
 */
public class RegistroFrag extends Fragment {

    private TextView register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registro, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        register = (TextView) view.findViewById(R.id.register_text);
    }

    public void update(String text){
        register.setText(text);
    }
}
