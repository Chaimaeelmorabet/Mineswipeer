package com.chaimae.mineswipeerproject.bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chaimae on 14/06/2016.
 */
public class PartidesSQLiteHelper extends SQLiteOpenHelper {

    public final static int version = 1;
    public final static String nameTable = "Partides";

    public final static String alies_key = "alies";
    public final static String data_key = "data";
    public final static String num_caselles_key = "numeroCaselles";
    public final static String percent_mines_key = "perCentMines";
    public final static String temps_key = "temps";
    public final static String resultat_key = "resultat";
    public final static String win_key = "win";
    public final static String log_key = "log";

    //Sentencia SQL para crear la tabla de partidas
    String sqlCreate = "CREATE TABLE " + nameTable + "  " +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            alies_key + " TEXT, " +
            data_key + " TEXT, " +
            num_caselles_key + " INTEGER, " +
            percent_mines_key + " INTEGER, " +
            temps_key + " INTEGER, " +
            resultat_key + " TEXT, " +
            win_key + " INTEGER, " +
            log_key + " TEXT)";

    public PartidesSQLiteHelper(Context context) {
        super(context, nameTable, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creaci�n de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior,
                          int versionNueva) {

        //Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS " + nameTable);

        //Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
    }

}
