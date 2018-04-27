package com.example.drosi.vehicle_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vehiclemanager.db";
    private static final int DATABASE_VERSION = 1;

    // Table structures are as follows:
    // vehicle - From VehicleTable class, no instantiation necessary as everything is static
    // maintenance - From MaintenanceTable class,    ""                           ""

    DatabaseHelper(Context context) {
        // This is where the database is created.
        // Version can be updated when the tables are modified, this keeps the app from crashing
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the vehicle table
        String createTableSQL = "CREATE TABLE " + VehicleTable.getTableName() + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                VehicleTable.getColumnVin() + " TEXT, " +
                VehicleTable.getColumnYear() + " TEXT, " +
                VehicleTable.getColumnMake() + " TEXT, " +
                VehicleTable.getColumnModel() + " TEXT, " +
                VehicleTable.getColumnDesc() + " TEXT)";
        db.execSQL(createTableSQL);

        // Create the maintenance table
        createTableSQL = "CREATE TABLE " + MaintenanceTable.getTableName() + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MaintenanceTable.getColumnVid() + " TEXT, " +
                MaintenanceTable.getColumnDate() + " TEXT, " +
                MaintenanceTable.getColumnMileage() + " TEXT, " +
                MaintenanceTable.getColumnType() + " TEXT, " +
                MaintenanceTable.getColumnNotes() + " TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This method is only called when you upgrade the database, ie change the database_version variable
        db.execSQL("DROP TABLE IF EXISTS " + VehicleTable.getTableName());
        db.execSQL("DROP TABLE IF EXISTS " + MaintenanceTable.getTableName());
        onCreate(db);
    }

    boolean addVehicleData(String vin, String year, String make, String model, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(VehicleTable.getColumnVin(), vin);
        contentValues.put(VehicleTable.getColumnYear(), year);
        contentValues.put(VehicleTable.getColumnMake(), make);
        contentValues.put(VehicleTable.getColumnModel(), model);
        contentValues.put(VehicleTable.getColumnDesc(), desc);

        long result = db.insert(VehicleTable.getTableName(), null, contentValues);

        if (result == -1) {
            // The insert failed
            return false;
        } else {
            // The insert succeeded
            return true;
        }
    }

    Cursor getData(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
