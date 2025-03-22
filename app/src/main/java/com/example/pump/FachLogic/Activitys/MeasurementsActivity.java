package com.example.pump.FachLogic.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pump.Data.Utils;
import com.example.pump.FachLogic.Classes.Measurements;
import com.example.pump.FachLogic.Enums.MeasurementType;
import com.example.pump.R;

import java.util.ArrayList;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MeasurementsActivity extends AppCompatActivity {
    LinearLayout weight, calorie, fat, bodyParts, height;
    TextView heightTxt, fatValue, heightValueTxt, weightValue, calorieValue, pageTitle;
    ArrayList<Measurements> heights;
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.measurements_base);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
        // Setup BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(MeasurementsActivity.this, MeasurementsActivity.class));
                return true;
            }
            if (item.getItemId() == R.id.nav_workout) {
                startActivity(new Intent(MeasurementsActivity.this, WorkoutActivity.class));
                return true;
            }
            return false;
        });

        //Initialize all variables
        initViews();


        height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent heightIntent = BodyPartActivity.createIntent(MeasurementsActivity.this, MeasurementType.HEIGHT);
                startActivity(heightIntent);
            }
        });

        weight.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent weightIntent = BodyPartActivity.createIntent(MeasurementsActivity.this, MeasurementType.WEIGHT);
                startActivity(weightIntent);
            }
        });
        calorie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent calorieIntent = BodyPartActivity.createIntent(MeasurementsActivity.this, MeasurementType.CALORIES);
                startActivity(calorieIntent);
            }
        });
        bodyParts.setOnClickListener(view -> {
            Intent intent = new Intent(MeasurementsActivity.this, BodyParts.class);
            startActivity(intent);
        });

        //Height
        if(getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.HEIGHT))!= null)
        {
            heightValueTxt.setText(String.format("%.2f cm", getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.HEIGHT)).getNumberData()));
        }
        else {
            heightValueTxt.setText("0");
        }
        //Weight
        if(getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.WEIGHT))!= null)
        {
        weightValue.setText(String.format("%.2f kg",getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.WEIGHT)).getNumberData()));
        }
        else { weightValue.setText("0");}

        //Calorie
        if(getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.CALORIES))!= null) {
            calorieValue.setText(String.format("%.2f kcal", getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.CALORIES)).getNumberData()));
        }
        else{
            calorieValue.setText("0");
        }
        //Fat Percent
        fatValue.setText(String.format("%.2f%%", bodyFatCalculator()));
    }

    private void initViews() {
        weight = findViewById(R.id.weight);
        weightValue = findViewById(R.id.weightValue);
        calorie = findViewById(R.id.calorie);
        calorieValue= findViewById(R.id.calorieValue);
        bodyParts = findViewById(R.id.bodyParts);
        fat = findViewById(R.id.fat);
        fatValue = findViewById(R.id.fatValue);
        height = findViewById(R.id.height);
        heightTxt = findViewById(R.id.heightTxt);
        heightValueTxt = findViewById(R.id.heightValue);
        pageTitle = findViewById(R.id.measurementPagetxt);
        if(heights == null){
            heights = new ArrayList<>();
        }
    }

    public static Measurements getLastElement(ArrayList<Measurements> list) {
        return list.isEmpty() ? new Measurements(0f) : list.get(list.size() - 1);
    }

    private double bodyFatCalculator(){
        float neck = getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.NECK)).getNumberData();
        float waist = getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.WAIST)).getNumberData();
        float height = getLastElement(Utils.getInstance(this).getMeasurements(MeasurementType.HEIGHT)).getNumberData();
        return (86.010 * Math.log10(waist - neck)) - (70.041 * Math.log10(height)) +30.30;
    }

}