package com.chaimae.mineswipeerproject.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.listeners.OnClickCellListener;
import com.chaimae.mineswipeerproject.listeners.OnLongClickCellListener;
import com.chaimae.mineswipeerproject.models.Cell;

import java.util.List;

public class MineswipperAdapter extends BaseAdapter {

    private Context context;
    private List<Cell> cells;

    public MineswipperAdapter(Context context, List<Cell> cells) {

        this.context = context;
        this.cells = cells;
    }

    @Override
    public int getCount() {
        return cells.size();
    }

    @Override
    public Cell getItem(int position) {
        return cells.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridcell_game, null);
            holder = new ViewHolder();
            holder.button = (Button) convertView.findViewById(R.id.buttonCell);
            getItem(position).setButton(holder.button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.button.setOnClickListener(new OnClickCellListener(position));
        holder.button.setOnLongClickListener(new OnLongClickCellListener(position));
        holder.button.setText(getItem(position).getState());

        return convertView;
    }

    private class ViewHolder {
        public Button button;

    }
}
