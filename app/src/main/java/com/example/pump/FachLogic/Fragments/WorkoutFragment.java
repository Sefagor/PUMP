package com.example.pump.FachLogic.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.DialogUtils;
import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Adapters.WorkoutAdapter;
import com.example.pump.FachLogic.Classes.Workout;
import com.example.pump.R;

import java.util.ArrayList;

public class WorkoutFragment extends Fragment {
    private RecyclerView workoutList;
    private WorkoutAdapter workoutAdapter;
    private ArrayList<Workout> workouts;
    private static final String TAG = "WorkoutFragment";

    public WorkoutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workout, container, false);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        workoutList = view.findViewById(R.id.workoutsRecyclerView);

        loadWorkouts();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        workoutList.setLayoutManager(layoutManager);

        Button addWorkoutButton = view.findViewById(R.id.workoutAddButton);

        addWorkoutButton.setOnClickListener(v -> navigateToFragment(AddWorkoutFragment.newInstance(null)));

        workoutAdapter.setOnItemClickListener(position -> showUpdateWorkoutDialog(position));
    }

    public void loadWorkouts() {
        workouts = Utils.getInstance(getContext()).getWorkouts();
        if (workoutAdapter == null) {
            workoutAdapter = new WorkoutAdapter(workouts, this);
            workoutList.setAdapter(workoutAdapter);
        } else {
            workoutAdapter.setWorkout(workouts); // Update adapter data
        }
    }

    public void showUpdateWorkoutDialog(int position) {
        DialogUtils.showUpdateWorkoutDialog(getContext(), workouts, workoutAdapter,
                position, R.layout.update_entry, R.id.removeEntryButton);
        Log.d(TAG, "onItemClick:" + workouts);
    }

    public void navigateToFragment(Fragment fragment) {
       FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
       Fragment previousFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
       if (previousFragment != null) {
            transaction.remove(previousFragment);
       }
       transaction.replace(R.id.fragment_container, fragment);
       transaction.commit();
    }
}
