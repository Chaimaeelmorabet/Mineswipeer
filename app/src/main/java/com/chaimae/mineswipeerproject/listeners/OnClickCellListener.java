package com.chaimae.mineswipeerproject.listeners;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chaimae.mineswipeerproject.models.Cell;
import com.chaimae.mineswipeerproject.patterns.GameSingleton;

import java.util.List;

/**
 * Created by Chaimae on 15/04/2016.
 */
public class OnClickCellListener implements View.OnClickListener {
    private final GameSingleton gameSingleton;
    private Integer position;
    private Cell cell;

    public OnClickCellListener(Integer position) {
        this.position = position;
        gameSingleton = GameSingleton.getInstance();
        List<Cell> cells = gameSingleton.getCells();
        this.cell = cells.get(position);
        cell.findNeighbourCells(cells);

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        if (!cell.isLongClick() && !cell.isClick()) {
            String text = "Casella oberta = " + cell.getPosition();
            cell.setIsClick(true);
            if (cell.isImABomb()) {
                text += " (Bomba)";
                cell.setState("B");
                writeLog(text);
                cell.setBombImage();
                gameSingleton.getOnFinishGameListener().onLose(false);
            } else {
                int bombs = bombsAround();
                processBombs(bombs);
                cell.setState(String.valueOf(bombs));
                button.setText(String.valueOf(bombs));
                cell.setOpenImage();
                gameSingleton.substractCell();
                checkWin();
            }
            writeLog(text);
            gameSingleton.getOnFinishGameListener().onUpdateUI();
            Log.d("Cell " + position, String.valueOf(cell.getNeighboursAsString()));
        }
    }

    private void checkWin() {
        if (gameSingleton.getCasellesRestants() == 0) {
            gameSingleton.getOnFinishGameListener().onWin();
        }
    }

    private void processBombs(int bombs) {
        if (bombs == 0) {
            for (Cell cell : this.cell.getNeighbours()) {
                cell.callOnClick();
            }
        }
    }

    private int bombsAround() {
        int bombs = 0;
        List<Cell> neighbours = cell.getNeighbours();
        for (Cell cell : neighbours) {
            if (cell.isImABomb()) bombs += 1;
        }
        return bombs;
    }

    private void writeLog(String text) {
        gameSingleton.getOnCreateLog().writeLog(text);
    }
}
