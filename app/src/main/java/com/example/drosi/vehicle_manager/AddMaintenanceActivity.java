package com.example.drosi.vehicle_manager;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddMaintenanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maintenance);

        final Calendar myCalendar = Calendar.getInstance();
        final EditText txtDate = findViewById(R.id.maintenanceDate);
        EditText txtNotes = findViewById(R.id.txtNotes);
        Button submitRecord = findViewById(R.id.btnAddMaintenance);
        Button cancel = findViewById(R.id.btnCancel);

        // This sets up a multiline textbox that has a done button instead of carriage return
        txtNotes.setImeOptions(EditorInfo.IME_ACTION_DONE);
        txtNotes.setRawInputType(InputType.TYPE_CLASS_TEXT);

        // Sets the focus to the date field when the activity is loaded
        txtDate.requestFocus();

        // Makes the keyboard not popup when date field is clicked
        txtDate.setKeyListener(null);

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
