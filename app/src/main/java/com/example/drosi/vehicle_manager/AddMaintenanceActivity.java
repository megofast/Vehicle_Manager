package com.example.drosi.vehicle_manager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMaintenanceActivity extends AppCompatActivity {

    private String vName, VID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenance);

        // Create a connection to the database
        final DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);

        // Setup all the activity elements
        final Calendar myCalendar = Calendar.getInstance();
        final EditText txtDate = findViewById(R.id.maintenanceDate);
        final EditText txtNotes = findViewById(R.id.txtNotes);
        final EditText txtMileage = findViewById(R.id.txtMileage);
        final Spinner spType = findViewById(R.id.spType);
        Button submitRecord = findViewById(R.id.btnAddMaintenance);
        Button cancel = findViewById(R.id.btnCancel);

        // This sets up a multiline textbox that has a done button instead of carriage return
        txtNotes.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txtNotes.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Sets the focus to the date field when the activity is loaded
        txtDate.requestFocus();

        // Makes the keyboard not popup when date field is clicked
        txtDate.setKeyListener(null);

        // Get the data from the intent bundle
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // There is data to retrieve from the bundle
            vName = (String)bundle.get("vehicleName");
            VID = (String)bundle.get("vehicleID");
        } else {
            // There must have been an error, there is no data in the bundle
            Log.i("BUNNDLEERROR", "There was an error getting the bundle");
        }

        // When a date is selected set the textbox to contain it
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Update the textbox to show the selected date
                String format = "MM/dd/yy";
                SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);

                txtDate.setText(dateFormat.format(myCalendar.getTime()));
            }
        };

        // Click listener for the date textbox
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMaintenanceActivity.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Add record button listener
        submitRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the records to the database
                String date = txtDate.getText().toString();
                String mileage = txtMileage.getText().toString();
                String type = spType.getSelectedItem().toString();
                String notes = txtNotes.getText().toString();

                boolean insertStatus = mDatabaseHelper.addMaintenanceRecord(VID, date, mileage, type, notes);

                if (insertStatus) {
                    // Toast the user for a successful insert
                    Toast.makeText(getApplicationContext(), "Data successfully added.", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddMaintenanceActivity.this, VehicleSummaryActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "An error occurred adding vehicle.", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Add click listener for the cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the activity
                finish();
            }
        });
    }
}
