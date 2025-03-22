package com.example.pump.FachLogic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Adapters.AddWorkoutAdapter;
import com.example.pump.FachLogic.Adapters.WorkoutAdapter;
import com.example.pump.FachLogic.Classes.Exercise;
import com.example.pump.FachLogic.Classes.Workout;
import com.example.pump.R;

import java.util.ArrayList;
import java.util.Objects;

public class AddWorkoutFragment extends Fragment {
    private Button cancelButton;
    private Button submitButton;
    private Button addExerciseButton;
    private EditText workoutName;
    private RecyclerView exerciseList;
    private ArrayList<Exercise> exercises;
    private ArrayList<Exercise> selectedExercises;
    private AddWorkoutAdapter adapter;
    private Workout workout;

    public AddWorkoutFragment() {
        this.selectedExercises = new ArrayList<>();
    }

    public static AddWorkoutFragment newInstance(Workout workout) {
        AddWorkoutFragment fragment = new AddWorkoutFragment();
        Bundle args = new Bundle();
        args.putSerializable("workout", workout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_workout, container, false);

       if(adapter == null){
           Log.d("TAG", "onCreateView: adapter null");
       }
       else{
           Log.d("TAG", "onCreateView: adapter not null");
       }

        init(view);

        return view;
    }

    private void init(View rootView) {
        cancelButton = rootView.findViewById(R.id.buttonCancel);
        submitButton = rootView.findViewById(R.id.buttonSubmit);
        addExerciseButton = rootView.findViewById(R.id.addExerciseButton);

        if (getArguments() != null) {
            workout = (Workout) getArguments().getSerializable("workout");
        }

        workoutName = rootView.findViewById(R.id.addWorkoutName);
        if (workout != null) {
            workoutName.setText(workout.getName());
        }

        exerciseList = rootView.findViewById(R.id.exercisesRecyclerView);
        exerciseList.setLayoutManager(new LinearLayoutManager(getContext()));

        if(workout != null){
            exercises = Utils.getInstance(getContext()).getExercises(workout.getID());
            adapter = new AddWorkoutAdapter(exercises);
            exerciseList.setAdapter(adapter);
        }
        loadExercises();

        cancelButton.setOnClickListener(v -> requireActivity().onBackPressed());

        submitButton.setOnClickListener(v -> handleSubmit());

        addExerciseButton.setOnClickListener(v -> navigateToFragment(new ExerciseListFragment()));
    }

    public void loadExercises() {
        //Getting selected Exercises from ExerciseListFragment
        getParentFragmentManager().setFragmentResultListener("exercise_selection", this, (requestKey, bundle) -> {
            ArrayList<Exercise> newExercises =  (ArrayList<Exercise>) bundle.getSerializable("selected_exercises");
            if(newExercises != null){
                selectedExercises.addAll(newExercises);
                Log.d("TAG", "loadExercises: "+selectedExercises.size());
            }
            if (workout != null) {
                exercises = Utils.getInstance(getContext()).getExercises(workout.getID());

                Log.d("TAG", "Test1: "+exercises.size());

                if(selectedExercises != null){
                    exercises.addAll(selectedExercises);
                }

                Log.d("TAG", "Test2: "+selectedExercises.size());

            }
            else {
                exercises = new ArrayList<>();
                if(selectedExercises != null){
                    exercises.addAll(selectedExercises);
                    Log.d("loadExercises", "loadExercises: "+selectedExercises.size());
                    Log.d("loadExercises", "loadExercises: "+exercises.size());
                }
                Log.w("loadExercises", "Workout is null, cannot load exercises.");
            }


            adapter = new AddWorkoutAdapter(exercises);
            exerciseList.setAdapter(adapter);
        });


    }

    private void handleSubmit() {
        String workoutNameText = workoutName.getText().toString().trim();
        if (workoutNameText.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a workout name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (exercises == null) {
            Toast.makeText(getContext(), "Please select at least 1 exercise", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Workout> workouts = Utils.getInstance(getContext()).getWorkouts();
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

        Utils.getInstance(getContext()).saveWorkouts(workouts);

        Utils.getInstance(getContext()).saveExercises(exercises, workout.getID());

        WorkoutAdapter adapter = WorkoutAdapter.getInstance(workouts, new WorkoutFragment());
        if (adapter != null) {
            Log.d("AddWorkout", "handleSubmit: "+ exercises.size());
            adapter.notifyDataSetChanged();
        }

        navigateToFragment(new WorkoutFragment());
    }

    public void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}


