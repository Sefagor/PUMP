package com.example.pump.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.pump.FachLogic.Classes.Exercise;
import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Classes.Workout;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String WEIGHTS_KEY = "weights_key";
    private static final String CALORIES_KEY = "calories_key";
    private static final String HEIGHT_KEY ="height_key";
    private static final String NECK_KEY ="neck_key";
    private static final String SHOULDER_KEY ="shoulder_key";
    private static final String CHEST_KEY ="chest_key";
    private static final String ARMS_KEY ="arms_key";
    private static final String WAIST_KEY ="waist_key";
    private static final String HIPS_KEY ="hips_key";
    private static final String LEGS_KEY ="legs_key";

    private static final String WORKOUT_KEY ="workout_key";

    private static final String EXERCISE_KEY = "exercise_key";

    private final SharedPreferences sharedPreferences;
    private static Utils instance;

    private Utils(Context context){
        sharedPreferences = context.getSharedPreferences("data_base", Context.MODE_PRIVATE);
    }

    public static Utils getInstance(Context context){
        if(instance == null){
            instance = new Utils(context);
        }
            return instance;

    }
    private String getMeasurementKey(MeasurementType type) {
        switch (type) {
            case WEIGHT:
                return WEIGHTS_KEY;
            case HEIGHT:
                return HEIGHT_KEY;
            case NECK:
                return NECK_KEY;
            case SHOULDER:
                return SHOULDER_KEY;
            case CHEST:
                return CHEST_KEY;
            case ARMS:
                return ARMS_KEY;
            case WAIST:
                return WAIST_KEY;
            case HIPS:
                return HIPS_KEY;
            case LEGS:
                return LEGS_KEY;
            case CALORIES:
                return CALORIES_KEY;
            default:
                throw new IllegalArgumentException("Unknown measurement type: " + type);
        }
    }

    public void saveMeasurements(ArrayList<Measurements> measurements, MeasurementType type) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(measurements);

        String key = getMeasurementKey(type);
        Log.d("saveMeasurements", "Saving measurements of type: " + type);
        editor.putString(key, json);
        editor.apply();
    }
    public ArrayList<Measurements> getMeasurements(MeasurementType type) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Measurements>>() {}.getType();
        String key = getMeasurementKey(type);
        ArrayList<Measurements> measurements = gson.fromJson(sharedPreferences.getString(key, null), listType);
        return measurements != null ? measurements : new ArrayList<>();
    }

    public void saveWorkouts(ArrayList<Workout> workoutList) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(workoutList);
        Log.d("saveWorkouts", "Saving: " + json);

        editor.putString(WORKOUT_KEY, json);
        editor.apply();
    }
    public ArrayList<Workout> getWorkouts() {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Workout>>() {}.getType();
        ArrayList<Workout> workoutList = gson.fromJson(sharedPreferences.getString(WORKOUT_KEY, null), listType);
        return workoutList != null ? workoutList : new ArrayList<>();
    }

    public void saveExercises(ArrayList<Exercise> exercises, int id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(exercises);
        Log.d("saveExercises", "Saving: " + json);

        editor.putString(EXERCISE_KEY + id, json);
        editor.apply();
    }
    public ArrayList<Exercise> getExercises(int id) {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Exercise>>() {}.getType();
        ArrayList<Exercise> exercises = gson.fromJson(sharedPreferences.getString(EXERCISE_KEY + id, null), listType);
        return exercises != null ? exercises : new ArrayList<>();
    }

}
