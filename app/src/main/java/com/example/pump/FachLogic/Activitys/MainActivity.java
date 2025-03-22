package com.example.pump.FachLogic.Activitys;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pump.Data.SqlLiteDataBase.ExerciseRepository;
import com.example.pump.FachLogic.Fragments.MeasurementsFragment;
import com.example.pump.FachLogic.Fragments.WorkoutFragment;
import com.example.pump.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ExerciseRepository exerciseRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        EdgeToEdge.enable(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        if (savedInstanceState == null) {
           replaceFragment(new MeasurementsFragment());
        }
        //Creating Sql Database
        exerciseRepository = new ExerciseRepository(this);
        exerciseRepository.loadExercisesFromAssets(this);
        exerciseRepository.loadExercisesFromAssets(this);

        //Bottom Nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.nav_home) {
                selectedFragment = new MeasurementsFragment();
            }

            if (item.getItemId() == R.id.nav_workout) {
                selectedFragment = new WorkoutFragment();
            }

            if (selectedFragment != null) {
                replaceFragment(selectedFragment);
            }
            return true;
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null); // for back navigation
        fragmentTransaction.commit();
    }
}

