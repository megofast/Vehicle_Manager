package com.example.drosi.vehicle_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "vehicle";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_VIN = "vin";
    public static final String COLUMN_YEAR = "year";
    public static final String COLUMN_MAKE = "make";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_DESC = "description";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VIN + " TEXT, " +
                COLUMN_YEAR + " TEXT, " +
                COLUMN_MAKE + " TEXT, " +
                COLUMN_MODEL + " TEXT, " +
                COLUMN_DESC + " TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String vin, String year, String make, String model, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_VIN, vin);
        contentValues.put(COLUMN_YEAR, year);
        contentValues.put(COLUMN_MAKE, make);
        contentValues.put(COLUMN_MODEL, model);
        contentValues.put(COLUMN_DESC, desc);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            // The insert failed
            return false;
        } else {
            // The insert succeeded
            return true;
        }
    }

    public Cursor getData(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
