package com.chaimae.mineswipeerproject.controllers;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.chaimae.mineswipeerproject.bbdd.PartidesSQLiteHelper;
import com.chaimae.mineswipeerproject.serializable.Log;

/**
 * Created by Chaimae on 14/06/2016.
 */
public class BbddController {

    private final PartidesSQLiteHelper db;


    public BbddController(Activity activity) {
        db = new PartidesSQLiteHelper(activity);
    }

    public Boolean saveLog(Log log) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(PartidesSQLiteHelper.alies_key, log.getAlies());
            contentValues.put(PartidesSQLiteHelper.data_key, log.getData());
            contentValues.put(PartidesSQLiteHelper.num_caselles_key, log.getCaselles());
            contentValues.put(PartidesSQLiteHelper.percent_mines_key, log.getPercentMines());
            contentValues.put(PartidesSQLiteHelper.temps_key, log.getTemps());
            contentValues.put(PartidesSQLiteHelper.resultat_key, log.getResultat());
            contentValues.put(PartidesSQLiteHelper.win_key, log.getWin());
            contentValues.put(PartidesSQLiteHelper.log_key, log.getLog());
            db.getWritableDatabase().insert(PartidesSQLiteHelper.nameTable, null, contentValues);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Cursor getAll() {
        return db.getReadableDatabase().rawQuery("SELECT * FROM " + PartidesSQLiteHelper.nameTable +
                " ORDER BY " + PartidesSQLiteHelper.data_key + " DESC", null);
    }

    public Cursor getOne(String alies) {
        return db.getReadableDatabase().rawQuery("SELECT * FROM " + PartidesSQLiteHelper.nameTable +
                " WHERE " + PartidesSQLiteHelper.alies_key + "=? ORDER BY " +
                PartidesSQLiteHelper.data_key + " DESC", new String[]{alies});
    }

    public void removeAll() {
        db.getWritableDatabase().delete(PartidesSQLiteHelper.nameTable, null, null);
    }

    public void remove(String alias, String data) {
        String[] args = new String[]{alias, data};
        db.getWritableDatabase().delete(PartidesSQLiteHelper.nameTable,
                PartidesSQLiteHelper.alies_key + "=? AND " + PartidesSQLiteHelper.data_key + "=?", args);
    }
}
