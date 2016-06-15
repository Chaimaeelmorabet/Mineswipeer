package com.chaimae.mineswipeerproject.listeners;

import android.view.View;

import com.chaimae.mineswipeerproject.models.Cell;
import com.chaimae.mineswipeerproject.patterns.GameSingleton;

/**
 * Created by Chaimae on 15/04/2016.
 */
public class OnLongClickCellListener implements View.OnLongClickListener {
    private Cell cell;
    private GameSingleton gameSingleton;

    public OnLongClickCellListener(int position) {
        cell = GameSingleton.getInstance().getCells().get(position);
        gameSingleton = GameSingleton.getInstance();
    }

    @Override
    public boolean onLongClick(View v) {
        if (!cell.isClick()) {
            if (cell.isLongClick()) {
                writeLog("Treta bandera casella = " + cell.getPosition());
                cell.setState("");
                cell.removeImage();
                gameSingleton.addBomb();
                cell.setIsLongClick(false);
            } else {
                if (gameSingleton.getBombesRestants() > 0) {
                    writeLog("Bandera posada casella = " + cell.getPosition());
                    cell.setState("F");
                    cell.setFlagImage();
                    gameSingleton.substractBomb();
                    cell.setIsLongClick(true);
                }
            }
            gameSingleton.getOnFinishGameListener().onUpdateUI();
        }
        return true;
    }

    private void writeLog(String text) {
        gameSingleton.getOnCreateLog().writeLog(text);
    }
}
