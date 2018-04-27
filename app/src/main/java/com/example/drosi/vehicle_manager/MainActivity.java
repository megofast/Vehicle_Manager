package com.example.drosi.vehicle_manager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Boolean isGroupCollapsed;
    private ExpandableListView vehicleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the expandable list of vehicles
        vehicleList = (ExpandableListView)findViewById(R.id.vehicleList);
        final ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(MainActivity.this);
        vehicleList.setAdapter(adapter);

        // Setup the fab to add a vehicle
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the addVehicle activity
                startActivity(new Intent(MainActivity.this, AddVehicleActivity.class));
            }
        });

        // Setup fab to remove vehicles from the list
        FloatingActionButton fabRemove = (FloatingActionButton) findViewById(R.id.fabRemove);
        fabRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Make the red Xs visible on the list
                ArrayList<ImageView> deleteButtons = adapter.getDeleteImages();

                // Collapse the groups if they're opened
                for (int i = 0; i < adapter.getGroupCount(); i++) {
                        vehicleList.collapseGroup(i);
                }

                for (ImageView delete : deleteButtons) {
                    if (delete.getVisibility() == View.INVISIBLE) {
                        delete.setVisibility(View.VISIBLE);
                    } else if (delete.getVisibility() == View.VISIBLE) {
                        delete.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        // Create the click listener for the children of the expandablelistview
        vehicleList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                // When the child is clicked we are only interested in what vehicle was clicked
                // Thus, we only need to worry about the group position of the click
                Intent maintenanceIntent = new Intent(MainActivity.this, VehicleSummaryActivity.class);
                maintenanceIntent.putExtra("vehicleName", adapter.groupNames.get(groupPosition).toString());
                maintenanceIntent.putExtra("vehicleID", adapter.dbGroupIds[groupPosition].toString());
                startActivity(maintenanceIntent);
                return false;
            }
        });
    }
}
