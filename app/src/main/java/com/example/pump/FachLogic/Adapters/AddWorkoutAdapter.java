package com.example.pump.FachLogic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.FachLogic.Classes.Exercise;
import com.example.pump.R;

import java.util.ArrayList;
import java.util.List;

public class AddWorkoutAdapter extends RecyclerView.Adapter<AddWorkoutAdapter.AddWorkoutViewHolder> {
    private List<Exercise> exercises;
    private OnItemClickListener listener;

    public AddWorkoutAdapter(List<Exercise> exercises){
        this.exercises = exercises;
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        this.exercises.clear();
        this.exercises.addAll(exercises);
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
    public AddWorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent,false);
        return new AddWorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddWorkoutViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.exerciseName.setText(exercise.getName());

    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public void removeItem(int position){
        exercises.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, exercises.size());
    }

    public class AddWorkoutViewHolder extends RecyclerView.ViewHolder{
        TextView exerciseName;
        Button addSetButton;
        public AddWorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseTitle);
            addSetButton = itemView.findViewById(R.id.addSetButton);
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
