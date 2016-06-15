package com.chaimae.mineswipeerproject.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.serializable.Log;

import java.util.List;

/**
 * Created by Chaimae on 11/05/2016.
 */
public class LogFragment extends Fragment {
    private LinearLayout linearLayout;
    private int max = -1;
    private LinearLayout container;
    private List<String> logList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logList = Log.getInstance().log;
        container = (LinearLayout) view.findViewById(R.id.container);

        container.postDelayed(new Runnable() {
            @Override
            public void run() {
                View view1 = view.findViewById(R.id.test);
                max = (int) (container.getHeight() / (view1.getHeight() * 1.2));
                if (savedInstanceState != null) {
                    container.removeAllViews();
                    addViews();
                }
            }
        }, 100);

    }

    public void update(String text) {
        if (linearLayout == null || linearLayout.getChildCount() == max) {
            linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            container.addView(linearLayout);
            View line = new View(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT);
            int padding = 10;
            layoutParams.setMargins(padding, padding, padding, padding);
            line.setLayoutParams(layoutParams);
            line.setBackgroundResource(R.color.colorPrimary);
            container.addView(line);

        }
        TextView textView = new TextView(getActivity());
        textView.setText(text);
        linearLayout.addView(textView);
    }


    private void addViews() {
        int size = logList.size();
        for (int i = 0; i < size; i++) {
            update(logList.get(i));
        }
    }
}
