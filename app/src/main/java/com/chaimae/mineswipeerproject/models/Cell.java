package com.chaimae.mineswipeerproject.models;

import android.widget.Button;

import com.chaimae.mineswipeerproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chaimae on 17/04/2016.
 */
public class Cell {
    boolean imABomb = false;
    List<Cell> neighbours = new ArrayList<>();
    Button button;
    int position;
    boolean isLongClick = false;
    boolean isClick = false;
    private String state = "";

    public Cell(int position) {
        this.position = position;
    }

    public void callOnClick() {
        button.callOnClick();
    }

    public boolean isLongClick() {
        return isLongClick;
    }

    public void setIsLongClick(boolean isLongClick) {
        this.isLongClick = isLongClick;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setIsClick(boolean isClick) {
        this.isClick = isClick;
    }

    public boolean isImABomb() {
        return imABomb;
    }

    public void setImABomb(boolean imABomb) {
        this.imABomb = imABomb;
    }

    public List<Cell> getNeighbours() {
        return neighbours;
    }

    public String getNeighboursAsString() {
        String string = "";
        for (Cell cell : neighbours) {
            string += " " + cell.position;
        }
        return string;
    }

    public void setBombImage(){
        button.setBackgroundResource(R.drawable.mina);
    }

    public void setFlagImage(){
        button.setBackgroundResource(R.drawable.pink_flag);
    }

    public void removeImage() {
        button.setBackgroundResource(R.color.colorCasellaTancada);
    }

    public void setOpenImage() {
        button.setBackgroundResource(R.color.colorCasellaOberta);
    }

    public void findNeighbourCells(List<Cell> allCells) {
        if (neighbours.isEmpty()) {
            List<Integer> around = aroundMe(position, (int) Math.sqrt(allCells.size()));
            for (Integer position : around) {
                neighbours.add(allCells.get(position));
            }
        }
    }

    private List<Integer> aroundMe(int position, int tamany) {
        List<Integer> around = new ArrayList<>();
        if ((position % tamany) == 0)
            lateralEsquerra(position, tamany, around);
        else if ((position % tamany) == tamany - 1)
            lateralDreta(position, tamany, around);
        else
            mig(position, tamany, around);
        return around;
    }

    private void lateralEsquerra(int position, int tamany, List<Integer> around) {
        if (position == 0) { //Cantonada superior esquerra
            around.add(getSota(position, tamany));
            around.add(getDreta(position));
            around.add(getLateralSotaDreta(position, tamany));
        } else if (position == (tamany * (tamany - 1))) { //Cantonada inferior esquerra
            around.add(getSobre(position, tamany));
            around.add(getDreta(position));
            around.add(getLateralSobreDreta(position, tamany));
        } else { //Lateral esquerra mig
            around.add(getSobre(position, tamany));
            around.add(getSota(position, tamany));
            around.add(getDreta(position));
            around.add(getLateralSobreDreta(position, tamany));
            around.add(getLateralSotaDreta(position, tamany));
        }
    }

    private void mig(int position, int tamany, List<Integer> around) {
        if (position / tamany == 0) {
            around.add(getSota(position, tamany));
            around.add(getEsquerra(position));
            around.add(getDreta(position));
            around.add(getLateralSotaDreta(position, tamany));
            around.add(getLateralSotaEsquerra(position, tamany));
        } else if (position / tamany == tamany - 1) {
            around.add(getSobre(position, tamany));
            around.add(getEsquerra(position));
            around.add(getDreta(position));
            around.add(getLateralSobreDreta(position, tamany));
            around.add(getLateralSobreEsquerra(position, tamany));
        } else {
            around.add(getSobre(position, tamany));
            around.add(getSota(position, tamany));
            around.add(getEsquerra(position));
            around.add(getDreta(position));
            around.add(getLateralSobreDreta(position, tamany));
            around.add(getLateralSobreEsquerra(position, tamany));
            around.add(getLateralSotaDreta(position, tamany));
            around.add(getLateralSotaEsquerra(position, tamany));
        }
    }

    private void lateralDreta(int position, int tamany, List<Integer> around) {
        if (position == tamany - 1) { //Cantonada superior dreta
            around.add(getSota(position, tamany));
            around.add(getEsquerra(position));
            around.add(getLateralSotaEsquerra(position, tamany));
        } else if (position == (tamany * tamany - 1)) { //Cantonada inferior dreta
            around.add(getSobre(position, tamany));
            around.add(getEsquerra(position));
            around.add(getLateralSobreEsquerra(position, tamany));
        } else { //Lateral esquerra mig
            around.add(getSobre(position, tamany));
            around.add(getSota(position, tamany));
            around.add(getEsquerra(position));
            around.add(getLateralSobreEsquerra(position, tamany));
            around.add(getLateralSotaEsquerra(position, tamany));
        }
    }

    private int getSota(int position, int tamany) {
        return position + tamany;
    }

    private int getSobre(int position, int tamany) {
        return position - tamany;
    }

    private int getEsquerra(int position) {
        return position - 1;
    }

    private int getDreta(int position) {
        return position + 1;
    }

    private int getLateralSobreEsquerra(int position, int tamany) {
        return getSobre(position, tamany) - 1;
    }

    private int getLateralSotaEsquerra(int position, int tamany) {
        return getSota(position, tamany) - 1;
    }

    private int getLateralSobreDreta(int position, int tamany) {
        return getSobre(position, tamany) + 1;
    }

    private int getLateralSotaDreta(int position, int tamany) {
        return getSota(position, tamany) + 1;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "position=" + position +
                '}';
    }
}
