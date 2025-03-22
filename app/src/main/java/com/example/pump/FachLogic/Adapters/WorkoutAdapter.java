package com.example.pump.FachLogic.Adapters;

import static android.app.PendingIntent.getActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.FachLogic.Activitys.AddWorkout;
import com.example.pump.FachLogic.Classes.Workout;
import com.example.pump.FachLogic.Activitys.WorkoutActivity;
import com.example.pump.FachLogic.Fragments.AddWorkoutFragment;
import com.example.pump.FachLogic.Fragments.WorkoutFragment;
import com.example.pump.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<Workout> workoutList;
    private OnItemClickListener listener;
    private static WorkoutAdapter instance;
    private WorkoutFragment fragment;

    public WorkoutAdapter(List<Workout> workoutList, WorkoutFragment fragment){
        this.workoutList = workoutList;
        this.fragment = fragment;
    }

    public static  WorkoutAdapter getInstance(List<Workout> workoutList, WorkoutFragment fragment){
        if(instance == null){
            instance = new WorkoutAdapter(workoutList, fragment);
        }
        return instance;
    }

    public void setWorkout(ArrayList<Workout> workoutList) {
        this.workoutList = workoutList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.workout_item, viewGroup,false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int i) {
        Workout workout = workoutList.get(i);
        holder.workoutName.setText(workout.getName());
        holder.moreButton.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext()) // Use view's context
                    .setTitle("Choose an action")
                    .setItems(new CharSequence[]{"Update", "Delete"}, (dialog, which) -> {
                        if (which == 0) { // Update
                            fragment.navigateToFragment(AddWorkoutFragment.newInstance(workout));
                            }
                        else {
                            fragment.showUpdateWorkoutDialog(holder.getAdapterPosition());
                        }
                    })
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return workoutList.size();
    }

    public void removeItem(int positon){
        workoutList.remove(positon);
        notifyItemRemoved(positon);
        notifyItemRangeChanged(positon, workoutList.size());
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder{
        TextView workoutName;
        ImageButton moreButton;
        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workoutTitle);
            moreButton = itemView.findViewById(R.id.moreButton);
            itemView.setOnClickListener(v -> {
                if(listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}


