package com.example.drosi.vehicle_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "vehiclemanager.db";
    public static final int DATABASE_VERSION = 1;

    // Create the table classes
    public static final VehicleTable vehicleTable = new VehicleTable();
    public static final MaintenanceTable maintenanceTable = new MaintenanceTable();

    public DatabaseHelper(Context context) {
        // This is where the database is created.
        // Version can be updated when the tables are modified, this keeps the app from crashing
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the vehicle table
        String createTableSQL = "CREATE TABLE " + vehicleTable.getTableName() + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                vehicleTable.getColumnVin() + " TEXT, " +
                vehicleTable.getColumnYear() + " TEXT, " +
                vehicleTable.getColumnMake() + " TEXT, " +
                vehicleTable.getColumnModel() + " TEXT, " +
                vehicleTable.getColumnDesc() + " TEXT)";
        db.execSQL(createTableSQL);

        // Create the maintenance table
        createTableSQL = "CREATE TABLE " + maintenanceTable.getTableName() + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                maintenanceTable.getColumnVid() + " TEXT, " +
                maintenanceTable.getColumnDate() + " TEXT, " +
                maintenanceTable.getColumnMileage() + " TEXT, " +
                maintenanceTable.getColumnType() + " TEXT, " +
                maintenanceTable.getColumnNotes() + " TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is only called when you upgrade the database, ie change the database_version variable
        db.execSQL("DROP TABLE IF EXISTS " + vehicleTable.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + maintenanceTable.getTableName());
        onCreate(db);
    }

    public boolean addVehicleData(String vin, String year, String make, String model, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(vehicleTable.getColumnVin(), vin);
        contentValues.put(vehicleTable.getColumnYear(), year);
        contentValues.put(vehicleTable.getColumnMake(), make);
        contentValues.put(vehicleTable.getColumnModel(), model);
        contentValues.put(vehicleTable.getColumnDesc(), desc);

        long result = db.insert(vehicleTable.getTableName(), null, contentValues);

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
