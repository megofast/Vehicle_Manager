package com.example.drosi.vehicle_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MaintenanceAdapter extends ArrayAdapter<MaintenanceData> {
    private ArrayList<MaintenanceData> maintData;
    Context mContext;

    // placeholder class
    private static class ViewHolder {
        TextView txtDate;
        TextView txtType;
        TextView txtMileage;
        TextView txtNotes;
    }

    // Constructor
    public MaintenanceAdapter(ArrayList<MaintenanceData> data, Context context) {
        super(context, R.layout.maint_row, data);
        this.maintData = data;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MaintenanceData data = getItem(position);

        // Check if an existing view is being reused
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {
            // There is no view yet, create one
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.maint_row, parent, false);

            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.maintenanceDate);
            viewHolder.txtMileage = (TextView) convertView.findViewById(R.id.txtMileage);
            viewHolder.txtNotes = (TextView) convertView.findViewById(R.id.notes);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtDate.setText(data.getDate());
        viewHolder.txtType.setText(data.getType());
        viewHolder.txtMileage.setText(data.getMileage());
        viewHolder.txtNotes.setText(data.getNotes());

        return convertView;
    }
}
