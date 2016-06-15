package com.chaimae.mineswipeerproject.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.chaimae.mineswipeerproject.R;

/**
 * Created by Chaiame on 15/06/2016.
 */
public class CustomCursorAdapter extends CursorAdapter {
    private final LayoutInflater inflater;

    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        TextView alies = (TextView) view.findViewById(android.R.id.text1);
        TextView data = (TextView) view.findViewById(android.R.id.text2);
        String aliesS = cursor.getString(1);
        String dataS = cursor.getString(2);
        alies.setText(aliesS);
        data.setText(dataS);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor.getInt(7) == 1) {
            view.setBackgroundColor(context.getResources().getColor(R.color.win));
        } else {
            view.setBackgroundColor(context.getResources().getColor(R.color.lose));
        }
    }
}
