package com.example.drosi.vehicle_manager;

public class MaintenanceData {
    private String id;
    private String vid;
    private String date;
    private String mileage;
    private String notes;
    private String type;

    public MaintenanceData(String cId, String cVid, String cDate, String cMileage, String cType, String cNotes) {
        this.id = cId;
        this.vid = cVid;
        this.date = cDate;
        this.mileage = cMileage;
        this.notes = cNotes;
        this.type = cType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
