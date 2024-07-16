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
                "P1 text NOT NULL," +
                "P2 text NOT NULL," +
                "P3 text NOT NULL," +
                "P4 text NOT NULL," +
                "P5 text NOT NULL," +
                "P6 text NOT NULL," +
                "P7 text NOT NULL," +
                "P8 text NOT NULL," +
                "P9 text NOT NULL," +
                "P10 text NOT NULL," +
                "P11 text NOT NULL," +
                "P12 text NOT NULL," +
                "P13 text NOT NULL)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //CAMBIE LA VERSIÓN DE LA TABLA DE LA BDD
        db.execSQL("DROP TABLE Encuesta");
        onCreate(db);
    }

    public SQLiteDatabase onRead() {
        return this.getReadableDatabase();
    }
}
