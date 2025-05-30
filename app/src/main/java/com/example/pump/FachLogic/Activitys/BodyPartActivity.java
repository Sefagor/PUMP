package com.example.pump.FachLogic.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pump.Data.DialogUtils;
import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Adapters.MeasurementAdapter;
import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.example.pump.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class BodyPartActivity extends AppCompatActivity {
    private static final String EXTRA_BODY_PART = "extra_body_part";
    private RecyclerView measurementList;
    private MeasurementAdapter measurementAdapter;
    private ArrayList<Measurements> measurements;
    private static BodyPartActivity instance;

    public static Intent createIntent(Context context, MeasurementType type) {
        Intent intent = new Intent(context, BodyPartActivity.class);
        intent.putExtra(EXTRA_BODY_PART, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements); // Use a common layout

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });

        TextView pageTitle = findViewById(R.id.measurementPagetxt);

        MeasurementType type = (MeasurementType) getIntent().getSerializableExtra(EXTRA_BODY_PART);

        if(pageTitle != null){
            setPageTitle(this, type, pageTitle);
        }

        measurements = Utils.getInstance(this).getMeasurements(type);
        initViews(type);

        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(BodyPartActivity.this, MeasurementsActivity.class));
                return true;
            }
            if (item.getItemId() == R.id.nav_workout) {
                startActivity(new Intent(BodyPartActivity.this, WorkoutActivity.class));
                return true;
            }
            return false;
        });

    }

    public static BodyPartActivity getInstance(){
        if(instance == null){
            instance = new BodyPartActivity();
        }
        return instance;

    }

    public void setPageTitle(Context context,MeasurementType type, TextView pageTitle){
        if (pageTitle == null) {
            throw new NullPointerException("TextView is null");
        }
        switch (type){
            case NECK: pageTitle.setText(context.getString(R.string.neck)); break;
            case SHOULDER: pageTitle.setText(context.getString(R.string.shoulder));break;
            case CHEST: pageTitle.setText(context.getString(R.string.chest));break;
            case ARMS: pageTitle.setText(context.getString(R.string.arms));break;
            case WAIST: pageTitle.setText(context.getString(R.string.waist));break;
            case HIPS: pageTitle.setText(context.getString(R.string.hips));break;
            case LEGS: pageTitle.setText(context.getString(R.string.legs));break;
            case CALORIES: pageTitle.setText(context.getString(R.string.calorie));break;
            case WEIGHT: pageTitle.setText(context.getString(R.string.weight));break;
            case HEIGHT: pageTitle.setText(context.getString((R.string.height))); break;
            default: throw new IllegalArgumentException("Unknown MeasurementType: " + type);
        }
    }

    private void initViews(MeasurementType type) {
        measurementList = findViewById(R.id.measurementList);
        measurementAdapter = new MeasurementAdapter(type);
        measurementAdapter.setMeasurements(measurements);
        measurementList.setAdapter(measurementAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        measurementList.setLayoutManager(layoutManager);

        ImageButton addButton = findViewById(R.id.addBtn);
        addButton.setOnClickListener(v -> showAddMeasurementDialog(type));

        measurementAdapter.setOnItemClickListener(new MeasurementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showUpdateMeasurementDialog(type, position);
            }
        });
    }

    private void showAddMeasurementDialog(MeasurementType type) {
        DialogUtils.showAddMeasurementDialog(this, measurements, measurementAdapter,
                R.layout.add_entry, R.id.entry,
                R.id.buttonSubmit, type);
    }

    private void showUpdateMeasurementDialog(MeasurementType type, int position){
        DialogUtils.showUpdateMeasurementDialog(this,measurements,measurementAdapter,
                position,R.layout.update_entry,R.id.removeEntryButton,type);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MeasurementsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Call this to ensure the current activity is finished
    }
}

