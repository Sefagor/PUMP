package com.example.pump.FachLogic.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Adapters.AddWorkoutAdapter;
import com.example.pump.FachLogic.Adapters.WorkoutAdapter;
import com.example.pump.FachLogic.Classes.Exercise;
import com.example.pump.FachLogic.Classes.Workout;
import com.example.pump.FachLogic.Fragments.WorkoutFragment;
import com.example.pump.R;

import java.util.ArrayList;

public class AddWorkout extends AppCompatActivity {
    private Button cancelButton;
    private Button submitButton;
    private Button addExerciseButton;
    private EditText workoutName;
    private RecyclerView exerciseList;
    private ArrayList<Exercise> exercises;
    private AddWorkoutAdapter adapter;
    private Workout workout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.add_workout);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        // Checking if we're updating an existing workout
        workout = (Workout) getIntent().getSerializableExtra("workout");

        init();

        if (workout != null) {
            workoutName.setText(workout.getName());
            loadExercises();
        }
    }

    private void init() {
        cancelButton = findViewById(R.id.buttonCancel);
        submitButton = findViewById(R.id.buttonSubmit);
        addExerciseButton = findViewById(R.id.addExerciseButton);
        exerciseList = findViewById(R.id.exercisesRecyclerView);
        workoutName = findViewById(R.id.addWorkoutName);

        cancelButton.setOnClickListener(v -> finish());

        submitButton.setOnClickListener(v -> handleSubmit());
    }

    private void handleSubmit() {
        String workoutNameText = workoutName.getText().toString().trim();
        if (workoutNameText.isEmpty()) {
            Toast.makeText(this, "Please enter a workout name", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Workout> workouts = Utils.getInstance(this).getWorkouts();

        if (workout == null) {
            workout = new Workout(workoutNameText);
            workouts.add(workout);
        }
        else { // Updating existing workout
            for (int i = 0; i < workouts.size(); i++) {
                if (workouts.get(i).getID() == workout.getID()) {
                    workouts.get(i).setName(workoutNameText);
                    break;
                }
            }
        }

        Utils.getInstance(this).saveWorkouts(workouts);

        WorkoutAdapter adapter = WorkoutAdapter.getInstance(workouts, new WorkoutFragment());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        startActivity(new Intent(this, WorkoutActivity.class));
        finish();
    }



    public void loadExercises() {
        if (workout != null) {
            exercises = Utils.getInstance(this).getExercises(workout.getID());
            if (adapter == null) {
                adapter = new AddWorkoutAdapter(exercises);
                exerciseList.setAdapter(adapter);
            } else {
                adapter.setExercises(exercises);
                adapter.notifyDataSetChanged();
            }
        } else {
            Log.w("loadExercises", "Workout is null, cannot load exercises.");
        }
    }
}
