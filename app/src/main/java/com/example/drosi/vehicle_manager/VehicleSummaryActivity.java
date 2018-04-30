package com.example.drosi.vehicle_manager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class VehicleSummaryActivity extends AppCompatActivity {
    private ArrayList<MaintenanceData> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_summary);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAddMaintenance);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the addVehicle activity
                startActivity(new Intent(VehicleSummaryActivity.this, AddMaintenanceActivity.class));
            }
        });

        ListView maintenanceList = (ListView) findViewById(R.id.lstMaintenance);
        TextView vehicleTitle = (TextView) findViewById(R.id.txtVehicleName);

        // Get the bundle of data sent from the MainActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // There is data to retrieve from the bundle
            vehicleTitle.setText((String)bundle.get("vehicleName"));
        } else {
            // There must have been an error, there is no data in the bundle
            vehicleTitle.setText("ERROR");
        }

        // Populate a temporary list of the data
        mData = new ArrayList<>();

        mData.add(new MaintenanceData("10/24/2017", "48,654", "NOTES", "Oil Change"));
        mData.add(new MaintenanceData("11/29/2017", "50,654", "NOTES", "Tire Rotation"));
        mData.add(new MaintenanceData("01/08/2018", "59,893", "NOTES", "Oil Change"));
        mData.add(new MaintenanceData("04/01/2018", "64,324", "NOTES", "New Wiper Blades"));
        mData.add(new MaintenanceData("10/24/2017", "48,654", "NOTES", "Oil Change"));
        mData.add(new MaintenanceData("11/29/2017", "50,654", "NOTES", "Tire Rotation"));
        mData.add(new MaintenanceData("01/08/2018", "59,893", "NOTES", "Oil Change"));
        mData.add(new MaintenanceData("04/01/2018", "64,324", "NOTES", "New Wiper Blades"));
        mData.add(new MaintenanceData("10/24/2017", "48,654", "NOTES", "Oil Change"));
        mData.add(new MaintenanceData("11/29/2017", "50,654", "NOTES", "Tire Rotation"));
        mData.add(new MaintenanceData("01/08/2018", "59,893", "NOTES", "Oil Change"));
        mData.add(new MaintenanceData("04/01/2018", "64,324", "NOTES", "New Wiper Blades"));

        final MaintenanceAdapter adapter = new MaintenanceAdapter(mData, getApplicationContext());
        maintenanceList.setAdapter(adapter);
    }
}
