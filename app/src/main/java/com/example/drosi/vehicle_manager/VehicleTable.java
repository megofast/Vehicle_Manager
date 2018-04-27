package com.example.drosi.vehicle_manager;

public class VehicleTable {
    private static final String TABLE_NAME = "vehicle";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VIN = "vin";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_MAKE = "make";
    private static final String COLUMN_MODEL = "model";
    private static final String COLUMN_DESC = "description";

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnVin() {
        return COLUMN_VIN;
    }

    public static String getColumnYear() {
        return COLUMN_YEAR;
    }

    public static String getColumnMake() {
        return COLUMN_MAKE;
    }

    public static String getColumnModel() {
        return COLUMN_MODEL;
    }

    public static String getColumnDesc() {
        return COLUMN_DESC;
    }
}
