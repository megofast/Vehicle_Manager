package com.example.drosi.vehicle_manager;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
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
    public List<List<String>> childNames = new ArrayList<List<String>>();

    //Context context;
    private Activity context;

    public ExpandableListViewAdapter(Activity context) {
        this.context = context;

        // In the constructor, Get the database data and store it into the group/child arrays
        DatabaseHelper mDatabaseHelper = new DatabaseHelper(context);

        Cursor data = mDatabaseHelper.getData("SELECT * FROM " + mDatabaseHelper.TABLE_NAME);

        while(data.moveToNext()) {
            // The database has 4 columns labeled as such...
            // ID - column 0
            // VIN - Column 1
            // YEAR - Column 2
            // MAKE - Column 3
            // MODEL - Column 4
            // DESC - Column 5


            // Add the name as the groupname
            String name = data.getString(2) + " " + data.getString(3) + " " + data.getString(4);
            groupNames.add(name);

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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupName = groupNames.get(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);
        }
        TextView groupItem = (TextView) convertView.findViewById(R.id.groupName);
        groupItem.setTypeface(null, Typeface.BOLD);
        groupItem.setText(groupName);
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
        return false;
    }
}
