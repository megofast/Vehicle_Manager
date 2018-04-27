package com.example.drosi.vehicle_manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    // The arraylists to store the vehicle information
    public ArrayList<String> groupNames = new ArrayList<String>();
    public String[] dbGroupIds;    // Stores the database IDs so the proper record is deleted
    public List<List<String>> childNames = new ArrayList<List<String>>();
    public ArrayList<ImageView> deleteButtons = new ArrayList<ImageView>();
    private DatabaseHelper mDatabaseHelper;

    //Context context;
    private Activity context;

    public ExpandableListViewAdapter(Activity context) {
        this.context = context;

        // In the constructor, Get the database data and store it into the group/child arrays
        mDatabaseHelper = new DatabaseHelper(context);

        Cursor data = mDatabaseHelper.getData("SELECT * FROM " + mDatabaseHelper.vehicleTable.getTableName());

        // Initialize the visibility array with values showing the Xs are invisible
        int groupCount = data.getCount();
        ImageView spaceHolder = new ImageView(context); // Create spaceholders for the Xs arraylist

        for (int i = 0; i < groupCount; i++) {
            deleteButtons.add(spaceHolder);
        }
        dbGroupIds = new String[groupCount];
        while(data.moveToNext()) {
            // The database has 4 columns labeled as such...
            // ID - column 0 - KEY
            // VIN - Column 1
            // YEAR - Column 2
            // MAKE - Column 3
            // MODEL - Column 4
            // DESC - Column 5

            // Add the name as the groupname
            String name = data.getString(2) + " " + data.getString(3) + " " + data.getString(4);
            groupNames.add(name);

            // Set all the database IDs equal to the position of the vehicles in the listview
            dbGroupIds[data.getPosition()] = data.getString(0);

            // Add the vin and description as child elements
            List<String> groupChildren = new ArrayList<String>();
            groupChildren.add(data.getString(1));
            groupChildren.add(data.getString(5));
            childNames.add(groupChildren);
        }
    }

    @Override
    public int getGroupCount() {
        return groupNames.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childNames.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupNames.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childNames.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final String groupName = groupNames.get(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);
        }
        TextView groupItem = (TextView) convertView.findViewById(R.id.groupName);
        groupItem.setTypeface(null, Typeface.BOLD);
        groupItem.setText(groupName);

        // Create the red X for deleting vehicles
        final ImageView deleteImage = (ImageView) convertView.findViewById(R.id.group_delete);
        deleteButtons.set(groupPosition, deleteImage);

        // Add the click listener for the deleting x
        deleteImage.setOnClickListener(new View.OnClickListener() {
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
                                deleteVehicle(groupPosition);

                                // Remove the delete Xs
                                for (ImageView delete : deleteButtons) {
                                    if (delete.getVisibility() == View.INVISIBLE) {
                                        delete.setVisibility(View.VISIBLE);
                                    } else if (delete.getVisibility() == View.VISIBLE) {
                                        delete.setVisibility(View.INVISIBLE);
                                    }
                                }

                                // Update the list contents and refresh the list
                                groupNames.remove(groupPosition);
                                notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                // The user canceled or said no
                                break;
                        }
                    }
                };

                // Now that the dialog listener is created, create the actual dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete " + groupName + "?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String itemName = childNames.get(groupPosition).get(childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        // Check to be sure the view hasn't been rendered yet
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.infoItem);
        ImageView image = (ImageView) convertView.findViewById(R.id.infoImage);
        item.setText(itemName);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public ArrayList<ImageView> getDeleteImages() {
        return deleteButtons;
    }

    private boolean deleteVehicle(int groupID) {
        // Load the database
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        String whereClause = "id=?";
        String[] whereArgs = new String[] {dbGroupIds[groupID]};
        int delData = db.delete(mDatabaseHelper.vehicleTable.getTableName(), whereClause, whereArgs);
        if (delData > 0) {
            // Delete operation was a success!
            return true;
        } else {
            return false;
        }
    }
}
