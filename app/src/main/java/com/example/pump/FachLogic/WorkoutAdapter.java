package com.example.pump.FachLogic;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private List<Workout> workoutList;
    private OnItemClickListener listener;

    public WorkoutAdapter(List<Workout> workoutList){
        this.workoutList = workoutList;
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
    public void onBindViewHolder(@NonNull WorkoutAdapter.WorkoutViewHolder holder, int i) {
        Workout workout = workoutList.get(i);
        holder.workoutName.setText(workout.getName());
        holder.workoutDetails.setText(workout.getDetails());
        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext()) // Use view's context
                    .setTitle("Choose an action")
                    .setItems(new CharSequence[]{"Update", "Delete"}, (dialog, which) -> {
                        if (which == 0) { // Update
                            ((WorkoutActivity) v.getContext()).showAddWorkoutDialog(workoutList.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                        } else { // Delete
                            workoutList.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
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
        TextView workoutDetails;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            workoutName = itemView.findViewById(R.id.workoutTitle);
            workoutDetails = itemView.findViewById(R.id.workoutDetails);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}


