package com.example.drosi.vehicle_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MaintenanceAdapter extends ArrayAdapter<MaintenanceData> {
    private ArrayList<MaintenanceData> maintData;
    private ArrayList<ImageView> deleteButtons = new ArrayList<>();
    private Activity mContext;

    // placeholder class
    private static class ViewHolder {
        TextView txtDate;
        TextView txtType;
        TextView txtMileage;
        TextView txtNotes;
        TextView txtMonth;
        ImageView imgDelete;
        ImageView imgType;
    }

    // Constructor
    public MaintenanceAdapter(ArrayList<MaintenanceData> data, Activity context) {
        super(context, R.layout.maint_row, data);
        this.maintData = data;
        this.mContext = context;
    }

    // This method initializes the imageview array of delete buttons to the right number of records
    public void setupDeleteButtons(int totalItems) {
        ImageView spaceHolder = new ImageView(mContext);
        for (int i = 0; i < totalItems; i++) {
            deleteButtons.add(spaceHolder);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MaintenanceData data = getItem(position);

        // Check if an existing view is being reused
        ViewHolder viewHolder;

        if (convertView == null) {
            // There is no view yet, create one
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.maint_row, parent, false);

            viewHolder.txtDate = convertView.findViewById(R.id.maintenanceDate);
            viewHolder.txtMileage = convertView.findViewById(R.id.txtMileage);
            viewHolder.txtNotes = convertView.findViewById(R.id.notes);
            viewHolder.txtType = convertView.findViewById(R.id.type);
            viewHolder.txtMonth = convertView.findViewById(R.id.monthLogo);
            viewHolder.imgDelete = convertView.findViewById(R.id.imgDelete);
            viewHolder.imgType = convertView.findViewById(R.id.imgType);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtDate.setText(data.getDate());
        viewHolder.txtType.setText(data.getType());
        viewHolder.txtMileage.setText(data.getMileage());
        viewHolder.txtNotes.setText(data.getNotes());

        // Set the delete images
        deleteButtons.set(position, viewHolder.imgDelete);

        // Setup the click listener for the delete buttons
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a dialog box asking the user to confirm
                // First create the dialog click listener
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                // The user confirmed yes, they're sure
                                if (deleteRecord(data.getId(), data.getVid())) {
                                    // The delete was a success!
                                    Toast.makeText(mContext, "Record successfully deleted!", Toast.LENGTH_LONG).show();
                                }

                                // Remove the delete Xs
                                for (ImageView delete : deleteButtons) {
                                    if (delete.getVisibility() == View.INVISIBLE) {
                                        delete.setVisibility(View.VISIBLE);
                                    } else if (delete.getVisibility() == View.VISIBLE) {
                                        delete.setVisibility(View.INVISIBLE);
                                    }
                                }

                                // Update the list contents and refresh the list

                                notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                // The user canceled or said no
                                break;
                        }
                    }
                };

                // Now that the dialog listener is created, create the actual dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to delete the record from " + data.getDate() + "?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


        // Set the text and background color for the month logo
        setMonthsColor(data.getDate(), viewHolder);
        return convertView;
    }

    // Return the delete image array
    ArrayList<ImageView> getDeleteButtons() {
        return deleteButtons;
    }

    private boolean deleteRecord(String id, String vid) {
        // Load the database
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(mContext);
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String whereClause = "id=? AND vid=?";
        String[] whereArgs = new String[] {id, vid};

        int delData = db.delete(MaintenanceTable.getTableName(), whereClause, whereArgs);

        if (delData > 0) {
            // Delete operation was a success!
            Log.i("SUCCESS", "SUCCESSFUL DELETE");
            return true;
        } else {
            Log.i("FAILED", "FAILED TO DELETE");
            return false;
        }
    }

    // Set the color and text of the month indicator
    private void setMonthsColor(String date, ViewHolder viewHolder) {
        String month = date.substring(0, 2);

        switch (month) {
            case "01":
                viewHolder.txtMonth.setText(R.string.str_jan);
                viewHolder.txtMonth.setBackgroundResource(R.color.jan);
                break;
            case "02":
                viewHolder.txtMonth.setText(R.string.str_feb);
                viewHolder.txtMonth.setBackgroundResource(R.color.feb);
                break;
            case "03":
                viewHolder.txtMonth.setText(R.string.str_mar);
                viewHolder.txtMonth.setBackgroundResource(R.color.mar);
                break;
            case "04":
                viewHolder.txtMonth.setText(R.string.str_apr);
                viewHolder.txtMonth.setBackgroundResource(R.color.apr);
                break;
            case "05":
                viewHolder.txtMonth.setText(R.string.str_may);
                viewHolder.txtMonth.setBackgroundResource(R.color.may);
                break;
            case "06":
                viewHolder.txtMonth.setText(R.string.str_jun);
                viewHolder.txtMonth.setBackgroundResource(R.color.jun);
                break;
            case "07":
                viewHolder.txtMonth.setText(R.string.str_jul);
                viewHolder.txtMonth.setBackgroundResource(R.color.jul);
                break;
            case "08":
                viewHolder.txtMonth.setText(R.string.str_aug);
                viewHolder.txtMonth.setBackgroundResource(R.color.aug);
                break;
            case "09":
                viewHolder.txtMonth.setText(R.string.str_sep);
                viewHolder.txtMonth.setBackgroundResource(R.color.sep);
                break;
            case "10":
                viewHolder.txtMonth.setText(R.string.str_oct);
                viewHolder.txtMonth.setBackgroundResource(R.color.oct);
                break;
            case "11":
                viewHolder.txtMonth.setText(R.string.str_nov);
                viewHolder.txtMonth.setBackgroundResource(R.color.nov);
                break;
            case "12":
                viewHolder.txtMonth.setText(R.string.str_dec);
                viewHolder.txtMonth.setBackgroundResource(R.color.dec);
                break;
        }
    }
}
