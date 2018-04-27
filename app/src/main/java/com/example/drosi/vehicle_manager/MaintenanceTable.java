package com.example.drosi.vehicle_manager;

public class MaintenanceTable {
    private static final String TABLE_NAME = "maintenance";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_VID = "vid";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_MILEAGE = "mileage";
    private static final String COLUMN_NOTES = "notes";

    public static String getTableName() {
        return TABLE_NAME;
    }

    public static String getColumnId() {
        return COLUMN_ID;
    }

    public static String getColumnVid() {
        return COLUMN_VID;
    }

    public static String getColumnDate() {
        return COLUMN_DATE;
    }

    public static String getColumnType() {
        return COLUMN_TYPE;
    }

    public static String getColumnMileage() {
        return COLUMN_MILEAGE;
    }

    public static String getColumnNotes() {
        return COLUMN_NOTES;
    }
}
