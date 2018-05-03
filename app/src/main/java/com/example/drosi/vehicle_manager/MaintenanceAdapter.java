package com.example.drosi.vehicle_manager;

import android.content.Context;
import android.util.Log;
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
        TextView txtMonth;
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

            viewHolder.txtDate = convertView.findViewById(R.id.maintenanceDate);
            viewHolder.txtMileage = convertView.findViewById(R.id.txtMileage);
            viewHolder.txtNotes = convertView.findViewById(R.id.notes);
            viewHolder.txtType = convertView.findViewById(R.id.type);
            viewHolder.txtMonth = convertView.findViewById(R.id.monthLogo);

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

        // Set the text and background color for the month logo
        setMonthsColor(data.getDate(), viewHolder);
        return convertView;
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
