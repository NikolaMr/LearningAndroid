package com.nikola.example.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.nikola.example.criminalintent.database.CrimeDbSchema.CrimeTable;

/**
 * Created by Nikola on 30-Aug-18.
 */

public class CrimeBaseHelper extends SQLiteOpenHelper {


    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    CrimeTable.Cols.UUID + ", " +
                    CrimeTable.Cols.TITLE + ", " +
                    CrimeTable.Cols.DATE + ", " +
                    CrimeTable.Cols.SOLVED + ", " +
                    CrimeTable.Cols.POLICE_REQUIRED +
                    ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
