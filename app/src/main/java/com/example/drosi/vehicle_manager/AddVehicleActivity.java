package com.example.drosi.vehicle_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddVehicleActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        // Create the database object so that we can add data to it
        mDatabaseHelper = new DatabaseHelper(this);

        // Add the add and cancel buttons
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnCancel = (Button) findViewById(R.id.btnCancel);

        // Declare the textboxes to get the users data
        final EditText txtVIN = (EditText) findViewById(R.id.txtVIN);
        final EditText txtYEAR = (EditText) findViewById(R.id.txtYEAR);
        final EditText txtMAKE = (EditText) findViewById(R.id.txtMAKE);
        final EditText txtMODEL = (EditText) findViewById(R.id.txtMODEL);
        final EditText txtDESC = (EditText) findViewById(R.id.txtDESC);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vin = txtVIN.getText().toString();
                String year = txtYEAR.getText().toString();
                String make = txtMAKE.getText().toString();
                String model = txtMODEL.getText().toString();
                String desc = txtDESC.getText().toString();

                // Call addData method to send the data to the database
                addData(vin, year, make, model, desc);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AddVehicleActivity.this, MainActivity.class));
                finish();
            }
        });

    }

    public void addData(String vin, String year, String make, String model, String desc) {
        boolean insertData = mDatabaseHelper.addVehicleData(vin, year, make, model, desc);

        if (insertData) {
            // Toast the user for a successful insert
            Toast.makeText(this, "Data successfully added.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddVehicleActivity.this, MainActivity.class));
        } else {
            Toast.makeText(this, "An error occurred adding vehicle.", Toast.LENGTH_LONG).show();
        }
    }
}
