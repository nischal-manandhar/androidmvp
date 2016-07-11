package com.antonioleiva.mvpexample.app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ebpearls on 7/7/2016.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="test.db";
    private static final int DATABASE_VERSION = 1;
    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
