package com.example.pump.FachLogic.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.DialogUtils;
import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Adapters.WorkoutAdapter;
import com.example.pump.FachLogic.Classes.Workout;
import com.example.pump.FachLogic.Fragments.WorkoutFragment;
import com.example.pump.R;

import java.util.ArrayList;

public class WorkoutActivity extends AppCompatActivity {
    private RecyclerView workoutList;
    private WorkoutAdapter workoutAdapter;
    private ArrayList<Workout> workouts;
    private static final String TAG = "WorkoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        initViews();
    }


    private void initViews(){
        workoutList = findViewById(R.id.workoutsRecyclerView);

        loadWorkouts();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        workoutList.setLayoutManager(layoutManager);

        Button addWorkoutButton = findViewById(R.id.workoutAddButton);
        addWorkoutButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddWorkout.class));

        });
        workoutAdapter.setOnItemClickListener(new WorkoutAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showUpdateWorkoutDialog(position);
            }
        });
    }

    public void loadWorkouts() {
        workouts = Utils.getInstance(this).getWorkouts();
        if (workoutAdapter == null) {
            workoutAdapter = new WorkoutAdapter(workouts, new WorkoutFragment());
            workoutList.setAdapter(workoutAdapter);
        } else {
            workoutAdapter.setWorkout(workouts); // Update adapter data
        }
    }

    public void showUpdateWorkoutDialog(int position){
        DialogUtils.showUpdateWorkoutDialog(this, workouts,
                workoutAdapter, position,
                R.layout.update_entry,R.id.removeEntryButton);
        Log.d(TAG, "onItemClick:"+workouts);
    }

}
