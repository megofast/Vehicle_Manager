package com.example.drosi.vehicle_manager;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VehicleSummaryActivity extends AppCompatActivity {
    private String vName, VID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_summary);

        // Declare activity elements
        Button btnBack = findViewById(R.id.btnBack);
        ListView maintenanceList = findViewById(R.id.lstMaintenance);
        TextView vehicleTitle = findViewById(R.id.txtVehicleName);

        // Get the bundle of data sent from the MainActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // There is data to retrieve from the bundle
            vName = (String)bundle.get("vehicleName");
            VID = (String)bundle.get("vehicleID");

            // Set the title to the vehicle name
            vehicleTitle.setText(vName);

        } else {
            // There must have been an error, there is no data in the bundle
            Log.i("BUNNDLEERROR", "There was an error getting the bundle");
        }

        FloatingActionButton fab = findViewById(R.id.fabAddMaintenance);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the addVehicle activity
                //startActivity(new Intent(VehicleSummaryActivity.this, AddMaintenanceActivity.class));
                Intent maintenanceIntent = new Intent(VehicleSummaryActivity.this, AddMaintenanceActivity.class);
                maintenanceIntent.putExtra("vehicleName", vName);
                maintenanceIntent.putExtra("vehicleID", VID);
                startActivity(maintenanceIntent);
            }
        });

        // Add click listener for back button
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        final MaintenanceAdapter adapter = new MaintenanceAdapter(getMaintenanceData(), getApplicationContext());
        maintenanceList.setAdapter(adapter);
    }

    // Get the data from the database and create an arraylist with the data
    private ArrayList<MaintenanceData> getMaintenanceData() {
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
        Cursor data = mDatabaseHelper.getData("SELECT * FROM " + MaintenanceTable.getTableName()
                + " WHERE " + MaintenanceTable.getColumnVid() + " = " + VID);
        ArrayList<MaintenanceData> mData = new ArrayList<>();
        String vid, date, mileage, type, notes;
        // Loop through all the data
        while (data.moveToNext()) {
            // COLUMN 0 - ID
            // COLUMN 1 - VID
            // COLUMN 2 - date
            // COLUMN 3 - mileage
            // COLUMN 4 - type
            // COLUMN 5 - notes
            vid = data.getString(1);
            date = data.getString(2);
            mileage = data.getString(3);
            type = data.getString(4);
            notes = data.getString(5);
            mData.add(new MaintenanceData(vid, date, mileage, type, notes));

        }
        return mData;
    }
}
