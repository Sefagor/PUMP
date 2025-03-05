package com.example.pump.FachLogic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.DialogUtils;
import com.example.pump.Data.Utils;
import com.example.pump.MeasurementType;
import com.example.pump.R;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {
    private RecyclerView workoutList;
    private WorkoutAdapter workoutAdapter;
    private ArrayList<Workout> workouts;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout);

        initViews();
    }

    private void initViews(){
        workoutList = findViewById(R.id.workoutsRecyclerView);
        workouts = Utils.getInstance(this).getWorkouts();
        workoutAdapter = new WorkoutAdapter(workouts);
        workoutList.setAdapter(workoutAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        workoutList.setLayoutManager(layoutManager);

        Button addWorkoutButton = findViewById(R.id.workoutAddButton);
        addWorkoutButton.setOnClickListener(v -> showAddWorkoutDialog(null, -1));
        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showUpdateWorkoutDialog(position);
            }
        });
    }

    public void showAddWorkoutDialog(Workout workout, int position) {
        DialogUtils.showAddWorkoutDialog(this,workouts,workout,workoutAdapter,position,
                R.layout.add_workout, R.id.addWorkoutName,
                R.id.addWorkoutDetails, R.id.buttonSubmit);
    }


    public void showUpdateWorkoutDialog(int position){
        DialogUtils.showUpdateWorkoutDialog(this, workouts,
                workoutAdapter, position,
                R.layout.update_entry,R.id.removeEntryButton);
    }
}
