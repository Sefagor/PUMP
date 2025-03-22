package com.example.pump.FachLogic.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.SqlLiteDataBase.ExerciseRepository;
import com.example.pump.FachLogic.Classes.Exercise;
import com.example.pump.FachLogic.Adapters.ExerciseAdapter;
import com.example.pump.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerciseListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ExerciseAdapter adapter;
    private List<Exercise> exercises;
    private SearchView searchView;
    private ImageButton submitButton;
    private ArrayList<Exercise> selectedExercises;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_exercises, container, false);

        setHasOptionsMenu(true);

        init(view);
        Log.d("ExerciseList", "Exercises: " + exercises);

        return view;
    }

    private void init(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        searchView = view.findViewById(R.id.searchView);
        submitButton = view.findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(v -> handleSubmitButton());

        recyclerView = view.findViewById(R.id.exercise_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        loadExercises();
        adapter = new ExerciseAdapter(exercises, getContext());
        recyclerView.setAdapter(adapter);
    }

    private void loadExercises() {
        ExerciseRepository repository = new ExerciseRepository(getContext());

        // Fetch exercises from SQLite database
        exercises = repository.getAllExercises();
    }

    private void filterExercises(String query) {
        List<Exercise> filteredList = new ArrayList<>();
        for (Exercise exercise : exercises) {
            if (exercise.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(exercise);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }
        else{
            adapter.updateList(filteredList);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        searchView.setOnSearchClickListener(v -> {
            animateSearchView(true); // Expand animation
        });

        searchView.setOnCloseListener(() -> {
            animateSearchView(false); // Collapse animation
            return false;
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TAG", "onQueryTextChange: " + newText);
                filterExercises(newText);
                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void animateSearchView(boolean expand) {
        if (expand) {
            searchView.setAlpha(0f);
            searchView.setTranslationX(100f);
            searchView.animate()
                    .alpha(1f)
                    .translationX(0f)
                    .setDuration(300)
                    .start();
        } else {
            searchView.animate()
                    .alpha(0f)
                    .translationX(100f)
                    .setDuration(300)
                    .withEndAction(() -> searchView.setAlpha(1f))
                    .start();
        }
    }

    public void handleSubmitButton() {
        selectedExercises = adapter.getSelectedExercises();
        for(int i=0; i<selectedExercises.size(); i++){
            Log.d("Exerciselistfragment", "handleSubmitButton: " + selectedExercises.get(i).getName());
        }

        Bundle result = new Bundle();
        result.putSerializable("selected_exercises", new ArrayList<>(selectedExercises));

        getParentFragmentManager().setFragmentResult("exercise_selection", result);

        requireActivity().getSupportFragmentManager().popBackStack();

    }

}


