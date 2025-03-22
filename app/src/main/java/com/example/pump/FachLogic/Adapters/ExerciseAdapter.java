package com.example.pump.FachLogic.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.FachLogic.Classes.Exercise;
import com.example.pump.FachLogic.Fragments.AddWorkoutFragment;
import com.example.pump.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exercises;
    private Context context;
    private OnItemClickListener listener;
    private Set<Integer> selectedExerciseIds = new HashSet<>();

    public ExerciseAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_item_short, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.name.setText(exercise.getName());
        holder.muscle.setText(exercise.getFirstMuscle());

        if (selectedExerciseIds.contains(exercise.getId())) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.selected_color));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorNormal));
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectedExerciseIds.contains(exercise.getId())) {
                selectedExerciseIds.remove(exercise.getId());
            } else {
                selectedExerciseIds.add(exercise.getId());
            }
            notifyItemChanged(position);
        });

        holder.moreButton.setOnClickListener(v -> {
            showPopupMenu(v, position);
            Log.d("TAG", "onBindViewHolder: clicked");
        });
    }

    private void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.exercise_options_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_delete) {
                exercises.remove(position);
                notifyItemRemoved(position);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    public ArrayList<Exercise> getSelectedExercises() {
        ArrayList<Exercise> selectedList = new ArrayList<>();
        for (Exercise exercise : exercises) {
            if (selectedExerciseIds.contains(exercise.getId())) {
                selectedList.add(exercise);
            }
        }
        return selectedList;
    }


    public void updateList(List<Exercise> newList) {
        this.exercises = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView muscle;
        ImageButton moreButton;
        public ExerciseViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exercise_name);
            muscle = itemView.findViewById(R.id.muscle);
            moreButton = itemView.findViewById(R.id.moreButton);
            itemView.setOnClickListener(view -> {
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

