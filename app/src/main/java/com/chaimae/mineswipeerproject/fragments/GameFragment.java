package com.chaimae.mineswipeerproject.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.chaimae.mineswipeerproject.R;
import com.chaimae.mineswipeerproject.activities.ResultsActivity;
import com.chaimae.mineswipeerproject.adapters.MineswipperAdapter;
import com.chaimae.mineswipeerproject.controllers.PreferencesController;
import com.chaimae.mineswipeerproject.interfaces.OnGameListener;
import com.chaimae.mineswipeerproject.models.Settings;
import com.chaimae.mineswipeerproject.patterns.GameSingleton;
import com.chaimae.mineswipeerproject.serializable.Log;

import java.util.Date;

/**
 * Created by Chaimae on 11/05/2016.
 */
public class GameFragment extends Fragment implements OnGameListener {
    public static final String tempsRestant = "tr";
    private GameSingleton gameSingleton;
    private int temps;
    private CountDownTimer countDownTimer;
    private View mainView;
    private PreferencesController preferencesController;
    private OnCreateLogListener logListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainView = view;
    }

    @Override
    public void onAttach(Activity activity) {
        try {
            logListener = (OnCreateLogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getLocalClassName() + " ha d'implementar OnCreateLogListener");
        }
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferencesController = new PreferencesController(getActivity());
        config(savedInstanceState);
    }

    private void config(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            temps = tempsTotalPartida();
            gameSingleton = GameSingleton.getInstance(true);
            saveSettings();
            gameSingleton.initBombesRestants();
            gameSingleton.initCells(gameSingleton.getSettings().getTamany());
        } else {
            gameSingleton = GameSingleton.getInstance();
            temps = savedInstanceState.getInt(tempsRestant);
        }
        gameSingleton.setOnFinishGameListener(this);
        gameSingleton.setOnCreateLog(logListener);
        MineswipperAdapter adapter = new MineswipperAdapter(getActivity(), gameSingleton.getCells());
        GridView gameView = (GridView) mainView.findViewById(R.id.gameGrid);
        gameView.setNumColumns((int) Math.sqrt(gameSingleton.getSettings().getTamany()));
        gameView.setAdapter(adapter);
        onUpdateUI();
        startTime();
    }

    public int tempsTotalPartida() {
        return getResources().getInteger(R.integer.tempsTotal);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(tempsRestant, temps);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void saveSettings() {
        String alies = getAlies();
        int tamanyGraella = getTamanyGraella();
        boolean controlTemps = isControlTemps();
        int percentMines = getPercentMines();
        Settings settings = gameSingleton.getSettings();
        settings.setAlies(alies);
        settings.setTamany(tamanyGraella);
        settings.setControlTemps(controlTemps);
        settings.setPercent(percentMines);
        Log.getInstance().init(alies, tamanyGraella, percentMines);
        String s = String.format(getString(R.string.resultatLog)
                , alies, tamanyGraella, percentMines, tamanyGraella * percentMines / 100);
        logListener.writeLog(s);
    }

    public void startTime() {
        if (gameSingleton.getSettings().isControlTemps()) {
            final TextView tempsView = (TextView) mainView.findViewById(R.id.time);
            countDownTimer = new CountDownTimer(temps * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    tempsView.setText(String.format(getString(R.string.temps), seconds));
                    temps = seconds;
                }

                @Override
                public void onFinish() {
                    tempsView.setText(String.format(getString(R.string.temps), 0));
                    if (temps == 1) {
                        onLose(true);
                    }
                }
            }.start();
        }
    }

    private int getPercentMines() {
        return preferencesController.getPercentMines();
    }

    private boolean isControlTemps() {
        return preferencesController.isControlTemps();
    }

    private int getTamanyGraella() {
        int tamanyGraella = preferencesController.getTamanyGraella();
        return tamanyGraella * tamanyGraella;
    }

    private String getAlies() {
        if(preferencesController.getAlies()== ""){
            return "Default";
        }
        return preferencesController.getAlies();
    }

    @Override
    public void onUpdateUI() {
        TextView viewById = (TextView) mainView.findViewById(R.id.caselles);
        viewById.setText(String.format(getString(R.string.casellesRestants), gameSingleton.getCasellesRestants()));
        TextView viewById1 = (TextView) mainView.findViewById(R.id.bombes);
        viewById1.setText(String.format(getString(R.string.bombesRestants), gameSingleton.getBombesRestants()));
    }

    @Override
    public void onWin() {
        String res = getString(R.string.hasGuanyat);
        Log.getInstance().setWin(1);
        so();
        showToast(res);
        logResult(res);

    }

    private void so() {
        if (preferencesController.useSound()) {
            MediaPlayer mp = MediaPlayer.create(getActivity(), R.raw.ta_da);
            mp.start();
        }
    }

    @Override
    public void onLose(boolean timeout) {
        String res = (timeout) ? getString(R.string.hasPerdutPerTemps) : getString(R.string.hasPerdutPerBomba);
        Log.getInstance().setWin(0);
        vibrar();
        showToast(res);
        logResult(res);
    }

    private void vibrar() {
        if (preferencesController.useVibrate()) {
            Vibrator v = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            v.vibrate(500);
        }
    }

    public void logResult(String result) {
        Intent intent = new Intent(getActivity(), ResultsActivity.class);
        boolean controlTemps = gameSingleton.getSettings().isControlTemps();
        Log log = Log.getInstance();
        if (controlTemps) {
            countDownTimer.cancel();
            log.setTemps(temps);
            log.writeLog("T'han sobrat " + temps + " segons");
        }
        log.writeLog(result);
        log.setResultat(result);
        log.setData(new Date());
        intent.putExtra("log", log);
        startActivity(intent);
        getActivity().finish();
    }

    private void showToast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

    public interface OnCreateLogListener {
        void writeLog(String text);
    }
}
