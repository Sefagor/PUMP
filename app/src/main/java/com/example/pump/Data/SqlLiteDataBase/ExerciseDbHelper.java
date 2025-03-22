package com.example.pump.Data.SqlLiteDataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ExerciseDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "exercises.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EXERCISES = "exercises";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MUSCLE = "muscle";
    public static final String COLUMN_DESCRIPTION = "description";

    public static final String TABLE_IMAGES = "exercise_images";
    public static final String COLUMN_EXERCISE_ID = "exercise_id";
    public static final String COLUMN_IMAGE = "image";

    private static final String SQL_CREATE_EXERCISES =
            "CREATE TABLE " + TABLE_EXERCISES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_MUSCLE + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT)";

    private static final String SQL_CREATE_IMAGES =
            "CREATE TABLE " + TABLE_IMAGES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_EXERCISE_ID + " INTEGER, " +
                    COLUMN_IMAGE + " BLOB, " +
                    "FOREIGN KEY(" + COLUMN_EXERCISE_ID + ") REFERENCES " +
                    TABLE_EXERCISES + "(" + COLUMN_ID + "))";

    public ExerciseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EXERCISES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        onCreate(db);
    }
}
