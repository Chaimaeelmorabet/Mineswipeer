package com.chaimae.mineswipeerproject.fragments;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.adapters.CustomCursorAdapter;
import com.chaimae.mineswipeerproject.controllers.BbddController;

/**
 * Created by Chaimae on 13/06/2016.
 */
public class QueryFrag extends Fragment {
    public ListView listView;
    ActionMode mActionMode;
    private OnQueryClickListener listener;
    private CustomCursorAdapter cursorAdapter;
    private BbddController controller;
    private Cursor all;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_query, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = new BbddController(getActivity());


        listView = (ListView) view.findViewById(android.R.id.list);
        all = controller.getAll();

        cursorAdapter = new CustomCursorAdapter(getContext(), all, 0);
        listView.setAdapter(cursorAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (all.moveToPosition(position)) {
                    String string = all.getString(8);
                    listener.onQueryClick(string);
                } else listener.onQueryClick("Error");
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // if actionmode is null "not started"
                if (mActionMode != null) {
                    return false;
                }

                // Start the CAB
                mActionMode = getActivity().startActionMode(new ActionMode.Callback() {
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        getActivity().getMenuInflater().inflate(R.menu.menu_list, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.alias_menu:
                                findByAlias(position);
                                break;
                            case R.id.borrar_menu:
                                remove(position);
                                break;
                            case R.id.mail_menu:
                                sendMail(position);
                                break;

                        }
                        mode.finish();
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        mActionMode = null;
                    }
                });
                view.setSelected(true);
                return true;
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void findByAlias(int position) {
        if (all.moveToPosition(position)) {
            String alias = all.getString(1);
            all = controller.getOne(alias);
            cursorAdapter = new CustomCursorAdapter(getActivity(), all, 0);
            listView.setAdapter(cursorAdapter);
        }
    }

    private void sendMail(int position) {
        if (all.moveToPosition(position)) {
            String alias = all.getString(1);
            String data = all.getString(2);
            String log = all.getString(8);
            Intent i = new Intent(Intent.ACTION_SEND);
            i.putExtra(Intent.EXTRA_SUBJECT, "Log " + alias + " - " + data);
            i.putExtra(Intent.EXTRA_TEXT, log);
            startActivity(Intent.createChooser(i, getString(R.string.selecciona_aplicacio)));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            listener = (OnQueryClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() + " ha d'implementar OnQueryClickListener");
        }
        super.onAttach(activity);
    }

    private void remove(int position) {
        if (all.moveToPosition(position)) {
            String alias = all.getString(1);
            String data = all.getString(2);
            controller.remove(alias, data);
            all = controller.getAll();
            cursorAdapter.swapCursor(all);
        }
    }

    private void removeAll() {
        controller.removeAll();
        showToast("S'ha eliminat TOTA la BBDD");
        getActivity().finish();

    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.delete_db, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.borrar_tot_menu:
                removeAll();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    public interface OnQueryClickListener {
        void onQueryClick(String text);
    }

}
