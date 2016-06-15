package com.chaimae.mineswipeerproject.models;

/**
 * Created by Chaimae on 20/04/2016.
 */
public class Settings {
    private String alies;
    private int tamany;
    private boolean controlTemps;
    private int percent;

    public Settings() {
        this("Default", 5, false, 25);
    }

    public Settings(String alies, int tamany, boolean controlTemps, int percent) {
        this.alies = alies;
        this.tamany = tamany;
        this.controlTemps = controlTemps;
        this.percent = percent;
    }

    public String getAlies() {
        return alies;
    }

    public void setAlies(String alies) {
        this.alies = alies;
    }

    public int getTamany() {
        return tamany;
    }

    public void setTamany(int tamany) {
        this.tamany = tamany;
    }

    public boolean isControlTemps() {
        return controlTemps;
    }

    public void setControlTemps(boolean controlTemps) {
        this.controlTemps = controlTemps;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
