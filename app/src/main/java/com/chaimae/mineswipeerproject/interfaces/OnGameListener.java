package com.chaimae.mineswipeerproject.interfaces;

/**
 * Created by Chaimae on 22/04/2016.
 */
public interface OnGameListener {
    void onUpdateUI();
    void onWin();
    void onLose(boolean timeout);
}
