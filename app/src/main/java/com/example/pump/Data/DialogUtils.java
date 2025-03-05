package com.example.pump.Data;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pump.FachLogic.MeasurementAdapter;
import com.example.pump.FachLogic.Measurements;
import com.example.pump.FachLogic.Workout;
import com.example.pump.FachLogic.WorkoutAdapter;
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
                        Measurements measurementEntry = new Measurements(measurementValue);
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
    // Method for showing add workout dialog
    public static void showAddWorkoutDialogg(Context context, ArrayList<Workout> workoutList, WorkoutAdapter adapter, int layoutId, int workoutNameId, int workoutDetailsId, int buttonId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(layoutId, null);
        builder.setView(dialogView);

        final EditText workoutName = dialogView.findViewById(workoutNameId);
        final EditText workoutDetails = dialogView.findViewById(workoutDetailsId);
        Button buttonSubmit = dialogView.findViewById(buttonId);

        final AlertDialog dialog = builder.create();

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = workoutName.getText().toString();
                    String details = workoutDetails.getText().toString();
                    if (!name.isEmpty() && !details.isEmpty()) {
                        Workout workout = new Workout(name,details);
                        workoutList.add(workout);
                        Utils.getInstance(context).saveWorkouts(workoutList);
                        adapter.setWorkout(workoutList);
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Error adding measurement", e);
                }
            }
        });

        dialog.show();
    }

    // Method for showing update/delete workout dialog
    public static void showUpdateWorkoutDialog(Context context, ArrayList<Workout> workoutList, WorkoutAdapter adapter, int position, int layoutId, int deleteButtonId) {
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
                    Utils.getInstance(context).saveWorkouts(workoutList);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e(TAG, "Error removing entry", e);
                }
            }
        });

        dialog.show();
    }

    public static void showAddWorkoutDialog(Context context, ArrayList<Workout> workoutList,final Workout existingWorkout, WorkoutAdapter adapter, final int position, int layout,int workoutNameId, int workoutDetailsId, int buttonId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(layout, null);
        builder.setView(dialogView);

        EditText workoutNameInput = dialogView.findViewById(workoutNameId);
        EditText workoutDescriptionInput = dialogView.findViewById(workoutDetailsId);
        Button saveButton = dialogView.findViewById(buttonId);

        // Check if we are updating or adding a new workout
        if (existingWorkout != null) {
            workoutNameInput.setText(existingWorkout.getName());
            workoutDescriptionInput.setText(existingWorkout.getDetails());
        }

        AlertDialog dialog = builder.create();
        dialog.show();

        saveButton.setOnClickListener(v -> {
            String name = workoutNameInput.getText().toString().trim();
            String description = workoutDescriptionInput.getText().toString().trim();

            if (name.isEmpty() || description.isEmpty()) {
                Toast.makeText(context, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (existingWorkout == null) { // Adding a new workout
                Workout newWorkout = new Workout(name, description);
                workoutList.add(newWorkout);
            } else { // Updating an existing workout
                existingWorkout.setName(name);
                existingWorkout.setDetails(description);
                workoutList.set(position, existingWorkout);
            }

            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });
    }

}



