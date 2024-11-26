package com.example.pump.Data;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pump.FachLogic.MeasurementAdapter;
import com.example.pump.FachLogic.Measurements;
import com.example.pump.MeasurementType;

import java.util.ArrayList;

public class  DialogUtils {

    private static final String TAG = "DialogUtils";

    // Method for showing add measurement dialog
    public static void showAddMeasurementDialog(Context context, ArrayList<Measurements> measurements, MeasurementAdapter adapter, int layoutId, int editTextId, int buttonId, MeasurementType type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(layoutId, null);
        builder.setView(dialogView);

        final EditText measurementTxt = dialogView.findViewById(editTextId);
        Button buttonSubmit = dialogView.findViewById(buttonId);

        final AlertDialog dialog = builder.create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String measurementText = measurementTxt.getText().toString();
                    if (!measurementText.isEmpty()) {
                        float measurementValue = Float.parseFloat(measurementText);
                        String currentDate = Measurements.getCurrentDate();
                        Measurements measurementEntry = new Measurements(measurementValue, currentDate);
                        measurements.add(measurementEntry);
                        Utils.getInstance(context).saveMeasurements(measurements, type);
                        adapter.setMeasurements(measurements);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error adding measurement", e);
                }
            }
        });

        dialog.show();
    }

    // Method for showing update/delete measurement dialog
    public static void showUpdateMeasurementDialog(Context context, ArrayList<Measurements> measurements, MeasurementAdapter adapter, int position, int layoutId, int deleteButtonId, MeasurementType type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(layoutId, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        Button deleteButton = dialogView.findViewById(deleteButtonId);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    adapter.removeItem(position);
                    Utils.getInstance(context).saveMeasurements(measurements, type);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e(TAG, "Error removing entry", e);
                }
            }
        });

        dialog.show();
    }
}


