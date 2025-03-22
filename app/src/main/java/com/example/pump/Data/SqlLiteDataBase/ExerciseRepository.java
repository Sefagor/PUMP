package com.example.pump.Data.SqlLiteDataBase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.pump.FachLogic.Classes.Exercise;

public class ExerciseRepository {

    private final SQLiteDatabase db;
    private boolean isLoaded = false;

    public ExerciseRepository(Context context) {
        ExerciseDbHelper dbHelper = new ExerciseDbHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertExercise(String name, String muscle, String description) {
        ContentValues values = new ContentValues();
        values.put(ExerciseDbHelper.COLUMN_NAME, name);
        values.put(ExerciseDbHelper.COLUMN_MUSCLE, muscle);
        values.put(ExerciseDbHelper.COLUMN_DESCRIPTION, description);

        return db.insert(ExerciseDbHelper.TABLE_EXERCISES, null, values);
    }

    public void insertImage(long exerciseId, byte[] image) {
        ContentValues values = new ContentValues();
        values.put(ExerciseDbHelper.COLUMN_EXERCISE_ID, exerciseId);
        values.put(ExerciseDbHelper.COLUMN_IMAGE, image);

        db.insert(ExerciseDbHelper.TABLE_IMAGES, null, values);
    }

    public byte[] loadImageFromAssets(Context context, String filePath) {
        try {
            InputStream is = context.getAssets().open(filePath);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            Log.e("ExerciseRepository", "Error loading image", e);
            return null;
        }
    }

    public void loadExercisesFromAssets(Context context) {
       if(!isLoaded) {
           isLoaded = true;
            try {
                String[] exerciseFolders = context.getAssets().list("exercises");
                for (String folder : exerciseFolders) {
                    InputStream is = context.getAssets().open("exercises/" + folder + "/exercise.json");
                    int size = is.available();
                    byte[] buffer = new byte[size];
                    is.read(buffer);
                    is.close();

                    JSONObject json = new JSONObject(new String(buffer, "UTF-8"));
                    String name = json.getString("name");
                    JSONArray musclesArray = json.getJSONArray("primaryMuscles");
                    String muscle = musclesArray.getString(0);
                    String description = json.getString("instructions");

                    insertExercise(name, muscle, description);

                }
            } catch (Exception e) {
                Log.e("ExerciseRepository", "Error loading exercises", e);
            }
        }
    }
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        Cursor cursor = db.query(ExerciseDbHelper.TABLE_EXERCISES, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex(ExerciseDbHelper.COLUMN_ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ExerciseDbHelper.COLUMN_NAME));
                @SuppressLint("Range") String muscle = cursor.getString(cursor.getColumnIndex(ExerciseDbHelper.COLUMN_MUSCLE));
                @SuppressLint("Range") String description = cursor.getString(cursor.getColumnIndex(ExerciseDbHelper.COLUMN_DESCRIPTION));

                exercises.add(new Exercise(name, muscle, description));
            }
            cursor.close();
        }

        return exercises;
    }
}

