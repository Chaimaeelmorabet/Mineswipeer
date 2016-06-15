package com.chaimae.mineswipeerproject.patterns;

import com.chaimae.mineswipeerproject.fragments.GameFragment;
import com.chaimae.mineswipeerproject.interfaces.OnGameListener;
import com.chaimae.mineswipeerproject.models.Cell;
import com.chaimae.mineswipeerproject.models.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Chaimae on 17/04/2016.
 */
public class GameSingleton {
    private static GameSingleton ourInstance;
    private List<Cell> cells;
    private Settings settings;
    private int bombesRestants;
    private int casellesRestants;
    private OnGameListener onGameListener;
    private GameFragment.OnCreateLogListener onCreateLog;

    private GameSingleton() {
        cells = new ArrayList<>();
        settings = new Settings();
        bombesRestants = -1;
    }

    public static synchronized GameSingleton getInstance() {
        return getInstance(false);
    }

    public static synchronized GameSingleton getInstance(boolean reset) {
        if (ourInstance == null || reset) {
            ourInstance = new GameSingleton();
        }
        return ourInstance;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public Settings getSettings() {
        return settings;
    }

    public void initCells(int tamanyGraella) {

        casellesRestants = tamanyGraella - bombesRestants;
        for (int i = 0; i < tamanyGraella; i++) {
            Cell cell = new Cell(i);
            cells.add(cell);
        }
        Random randomGenerator = new Random();
        int i = 0;
        while (i < bombesRestants) {
            int randomInt = randomGenerator.nextInt(tamanyGraella);
            Cell cell = cells.get(randomInt);
            if (!cell.isImABomb()) {
                cell.setImABomb(true);
                i++;
            }
        }
    }

    public int getBombesRestants() {
        return bombesRestants;
    }

    public void initBombesRestants() {
        bombesRestants = settings.getTamany() * settings.getPercent() / 100;
    }

    public int getCasellesRestants() {
        return casellesRestants;
    }

    public void substractCell() {
        casellesRestants--;
    }

    public void substractBomb() {
        bombesRestants--;
    }

    public void addBomb() {
        bombesRestants++;
    }

    public OnGameListener getOnFinishGameListener() {
        return onGameListener;
    }

    public void setOnFinishGameListener(OnGameListener onGameListener) {
        this.onGameListener = onGameListener;
    }

    public void setOnCreateLog(GameFragment.OnCreateLogListener onCreateLog) {
        this.onCreateLog = onCreateLog;
    }

    public GameFragment.OnCreateLogListener getOnCreateLog() {
        return onCreateLog;
    }
}
