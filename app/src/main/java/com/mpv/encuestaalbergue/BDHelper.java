package com.mpv.encuestaalbergue;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Encuesta (" +
                "P1 TEXT NOT NULL," +
                "P2 INTEGER NOT NULL," +
                "P3 INTEGER NOT NULL," +
                "P4 TEXT NOT NULL," +
                "P5 TEXT NOT NULL," +
                "P6 TEXT NOT NULL," +
                "P7 TEXT NOT NULL," +
                "P8 TEXT NOT NULL," +
                "P9 TEXT NOT NULL," +
                "P10 TEXT NOT NULL," +
                "P11 TEXT NOT NULL," +
                "P12 TEXT NOT NULL," +
                "P13 TEXT NOT NULL)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CAMBIE LA VERSIÓN DE LA TABLA DE LA BDD
        db.execSQL("DROP TABLE Encuesta");
        onCreate(db);
    }
}
