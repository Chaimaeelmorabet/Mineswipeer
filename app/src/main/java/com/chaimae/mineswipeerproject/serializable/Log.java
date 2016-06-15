package com.chaimae.mineswipeerproject.serializable;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chaimae on 13/06/2016.
 */
public class Log implements Serializable {

    private static Log ourInstance;
    public List<String> log = new ArrayList<>();
    public String alies;
    public int caselles;
    public int minesperCent;
    public int temps = -1;
    private Date data;
    private String resultat;
    private int win;

    public static synchronized Log getInstance() {
        if (ourInstance == null) {
            ourInstance = new Log();
        }
        return ourInstance;
    }

    public static synchronized void reset() {
        ourInstance = new Log();
    }

    public void init(String alies, int caselles, int minesperCent) {
        ourInstance.setAlies(alies);
        ourInstance.setCaselles(caselles);
        ourInstance.setMinesperCent(minesperCent);
    }

    public int getMinesperCent() {
        return minesperCent;
    }

    public void setMinesperCent(int minesperCent) {
        this.minesperCent = minesperCent;
    }

    public void writeLog(String text) {
        log.add(text);
    }

    public String get(int i) {
        return log.get(i);
    }

    public String getLog() {
        String s = "";
        for (String s1 : log) {
            s += s1 + "\n";
        }
        return s;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }

    public String getAlies() {
        return alies;
    }

    public void setAlies(String alies) {
        this.alies = alies;
    }

    public String getData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE,dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(data);
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getCaselles() {
        return caselles;
    }

    public void setCaselles(int caselles) {
        this.caselles = caselles;
    }

    public int getPercentMines() {
        return minesperCent;
    }

    public void setPercentMines(int percentMines) {
        this.minesperCent = percentMines;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }
}
