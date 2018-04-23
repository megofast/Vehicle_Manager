package com.example.drosi.vehicle_manager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

public class MainActivity extends AppCompatActivity {

    ExpandableListView vehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the fab to add a vehicle
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the addVehicle activity
                startActivity(new Intent(MainActivity.this, AddVehicleActivity.class));
            }
        });

        // Create the expandable list of vehicles
        vehicleList = (ExpandableListView)findViewById(R.id.vehicleList);
        ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(MainActivity.this);
        vehicleList.setAdapter(adapter);
    }
}
